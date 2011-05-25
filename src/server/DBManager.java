package server;

import java.sql.*;
import bcrypt.BCrypt;

public class DBManager {

	private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String dbName = "codebuddy";
	private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	
	private static final String createUSERS = "CREATE TABLE USERS (" +
			"USERNAME VARCHAR(20) NOT NULL, " +
			"PASSHASH VARCHAR(255) NOT NULL, " +
			"ENTRY_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
	
	private static final String createCHAT = "CREATE TABLE CHAT (" +
			"USERNAME VARCHAR(20) NOT NULL, " +
			"MESSAGE VARCHAR(255) NOT NULL, " +
			"ENTRY_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        private static final String createONLINE = "CREATE TABLE ONLINE (" +
			"USERNAME VARCHAR(20) NOT NULL)";

	private static Connection conn = null;
	private static Statement s = null;
	private static PreparedStatement psChat = null;
	private static PreparedStatement psHist = null;
        private static PreparedStatement psOnline = null;

	static void init() {
		// Load the JDBC JavaDB/Derby driver
		try {
			Class.forName(driver);
			System.err.println(driver + " loaded. ");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// Acquire a connection to the codebuddy DB
			conn = DriverManager.getConnection(connectionURL);
			System.err.println("Connected to database " + dbName);

			// Create an SQL statement to use for queries, etc.
			s = conn.createStatement();

			// Create users table, if it does not exist.
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, "USERS", null);
			if (!rs.next()) {
				System.err.println("USERS table not found. Creating...");
				createUsersTable();
				System.err.println("USERS table created.");
			}
			
			// Create chat table, if it does not exist.
			rs = dbmd.getTables(null, null, "CHAT", null);
			if (!rs.next()) {
				System.err.println("CHAT table not found. Creating...");
				createChatTable();
				System.err.println("CHAT table created.");
			}

                        // Create online table, if it does not exist.
                        s.execute("DROP TABLE ONLINE");
			rs = dbmd.getTables(null, null, "ONLINE", null);
			if (!rs.next()) {
				System.err.println("ONLINE table not found. Creating...");
				createOnlineTable();
				System.err.println("ONLINE table created.");
			}
			
			psChat = conn.prepareStatement("INSERT INTO CHAT(USERNAME, MESSAGE) VALUES (?, ?)");
			psHist = conn.prepareStatement("SELECT USERNAME, MESSAGE FROM CHAT WHERE ENTRY_TIME > ?  ORDER BY ENTRY_TIME");
                        psOnline = conn.prepareStatement("INSERT INTO ONLINE(USERNAME) VALUES (?)");
                        
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	static void shutdown() {
		try {
			// Release the resources (clean up)
			s.close();
			conn.close();
			System.err.println("Closed connection");

			/* In embedded mode, an application should shut down Derby.
			 * Shutdown throws the XJ015 exception to confirm success. */
			boolean properShutdown = false;
			try {
				DriverManager.getConnection("jdbc:derby:;shutdown=true");
			} catch (SQLException se) {
				if (se.getSQLState().equals("XJ015"))
					properShutdown = true;
			}
			if (!properShutdown)
				System.err.println("Database failed to shutdown normally.");
			else
				System.err.println("Database shutdown complete.");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

        private static void createOnlineTable() throws SQLException {
            s.execute(createONLINE);
        }
	
	private static void createUsersTable() throws SQLException {
		s.execute(createUSERS);
				
		PreparedStatement ps = conn.prepareStatement("INSERT INTO USERS(USERNAME, PASSHASH) VALUES (?, ?)");
				
		// Make the "nathan" account
		ps.setString(1, "nathan");
		ps.setString(2, BCrypt.hashpw("nahtan", BCrypt.gensalt()));
		ps.executeUpdate();
				
		// Make the "mike" account
		ps.setString(1, "mike");
		ps.setString(2, BCrypt.hashpw("ekim", BCrypt.gensalt()));
		ps.executeUpdate();
				
		// Make the "scott" account
		ps.setString(1, "scott");
		ps.setString(2, BCrypt.hashpw("ttocs", BCrypt.gensalt()));
		ps.executeUpdate();
				
		ps.close();
	}
	
	private static void createChatTable() throws SQLException {
		s.execute(createCHAT);
	}
	
	static boolean isValidLogin(String user, String pass) {
		try {
            if (!user.matches("[A-Za-z0-9_.]+") ||
                    !pass.matches("[A-Za-z0-9_.]+")) {
                return false;
            }
			ResultSet findUser =
				s.executeQuery("SELECT PASSHASH FROM USERS WHERE USERNAME = '" +
						user + "'");
			return findUser.next() && BCrypt.checkpw(pass, findUser.getString(1));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

        static void writeOnline(String user)
        {
            try {
                    psOnline.setString(1, user);
                    psOnline.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        static void logoutUser(String user)
        {
            try {
                    s.execute("DELETE FROM ONLINE WHERE USERNAME='"+user+"'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	
	static void writeChatMessage(String user, String msg) {
		try {
			psChat.setString(1, user);
			psChat.setString(2, msg);
			psChat.executeUpdate();
			printChat();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static String getChatSince(long time) {
		try {
			Timestamp begin = new Timestamp(time);
			psHist.setTimestamp(1, begin);
			ResultSet msgs = psHist.executeQuery();
			String ret = "";
			while (msgs.next()) {
				ret += msgs.getString(1) + "> " + msgs.getString(2) + "\n";
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

        static synchronized String[] getOnlineList()
        {
            try
            {
                ResultSet onlineUsers =
                        s.executeQuery("SELECT USERNAME FROM ONLINE");
                String userStr = "";

                while (onlineUsers.next())
                {
                    userStr += onlineUsers.getString(1) + ":";
                }

                return userStr.split(":");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        static synchronized String[] getOfflineList()
        {
            try
            {
                ResultSet onlineUsers =
                        s.executeQuery("SELECT USERNAME FROM ONLINE");
                
                String query = "SELECT USERNAME FROM USERS";

                if (onlineUsers.next())
                {
                    query = "SELECT USERNAME FROM USERS WHERE (USERNAME <> "+
                        "'"+onlineUsers.getString(1)+"'";
                    while (onlineUsers.next())
                    {
                        query += " AND USERNAME <> '"+onlineUsers.getString(1)+"'";
                    }
                    query += ")";
                }

                ResultSet offUsers = s.executeQuery(query);
                String userStr = "";

                while (offUsers.next())
                {
                    userStr += offUsers.getString(1) + ":";
                }

                return userStr.split(":");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return null;
            }
        }
	
	static void printChat() throws SQLException {
		ResultSet userlist =
			s.executeQuery("SELECT ENTRY_TIME, USERNAME, MESSAGE " +
			"FROM CHAT ORDER BY ENTRY_TIME");
		while (userlist.next()) {
			System.out.println("User account " + userlist.getString(2));
			System.out.println("Creation time: " + userlist.getTimestamp(1));
			System.out.println("Message: " + userlist.getString(3));
			System.out.println();
		}
		userlist.close();
	}
}
