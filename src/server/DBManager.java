package server;

import java.sql.*;

public class DBManager {

	private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String dbName = "codebuddy";
	private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	private static final String createString = "CREATE TABLE USERS (" +
			"USERNAME VARCHAR(20) NOT NULL, " +
			"PASSHASH VARCHAR(32) NOT NULL, " +
			"ENTRY_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

	private static Connection conn = null;
	private static Statement s = null;

	public static void init() {
		// Load the JDBC JavaDB/Derby driver
		try {
			Class.forName(driver);
			System.err.println(driver + " loaded. ");
		} catch(ClassNotFoundException e) {
			System.err.println(e.getMessage());
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
				System.err.println("USERS table not found.	Creating...");
				createUsersTable();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void shutdown() {
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
				System.err.println("Database did not shut down normally");
			else
				System.err.println("Database shut down normally");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void createUsersTable() throws SQLException {
		s.execute(createString);
				
		PreparedStatement ps = conn.prepareStatement("INSERT INTO USERS(USERNAME, PASSHASH) VALUES (?, ?)");
				
		// Make the "nathan" account
		ps.setString(1, "nathan");
		ps.setString(2, "nahtan");
		ps.executeUpdate();
				
		// Make the "mike" account
		ps.setString(1, "mike");
		ps.setString(2, "ekim");
		ps.executeUpdate();
				
		// Make the "scott" account
		ps.setString(1, "scott");
		ps.setString(2, "ttocs");
		ps.executeUpdate();
				
		ps.close();
	}
	
	public static void printUsers() throws SQLException {
		ResultSet userlist =
				s.executeQuery("SELECT ENTRY_TIME, USERNAME, PASSHASH " +
				"FROM USERS ORDER BY ENTRY_TIME");
		while (userlist.next()) {
			System.out.println("User account " + userlist.getString(2));
			System.out.println("Creation time: " + userlist.getTimestamp(1));
			System.out.println("Password: " + userlist.getString(3));
			System.out.println();
		}
		userlist.close();
	}
	
	public static boolean isValidLogin(String user, String pass) {
		
		try {
			// TODO this is completely vulnerable to SQL injection
			// probable solution: restrict characters allowed in username, and
			// verify that the cryptographic hashes we eventually use for
			// passwords will not have potentially harmful characters
			ResultSet findUser =
				s.executeQuery("SELECT USERNAME FROM USERS WHERE USERNAME = '" +
				user + "' AND PASSHASH = '" + pass + "'");
			return findUser.next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
