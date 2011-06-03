package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionManager {
	private static final String SERVER = "minthe.ugcs.caltech.edu";
	private static final int USER_PORT = 4444;
    private static final int DATA_PORT = 4445;
    private static Socket srvrSoc = null;
	private static PrintWriter srvrOut = null;
	private static BufferedReader srvrIn = null;
	
	private static long lastUpdate = 0;
	
	private static boolean active = false;
	
	static void openConnection() throws Exception {
		if (!active) {
			// Acquire a read/write socket connection to the server
			InetAddress srvr = InetAddress.getByName(SERVER);
			srvrSoc = new Socket(srvr, USER_PORT);
			srvrOut = new PrintWriter(srvrSoc.getOutputStream(), true);
			srvrIn = new BufferedReader(new InputStreamReader(
					srvrSoc.getInputStream()));
			
			
			active = true;
		}
	}
	
	static synchronized void syncClock() {
		// Get the server's clock time
		try {
			srvrOut.println("CLK");
			lastUpdate = Long.parseLong(srvrIn.readLine());
		} catch (IOException e) {
			
		}
	}
	
	static void closeConnection() throws IOException {
		if (active) {
			// Close the IO streams and socket with server
			srvrOut.close();
			srvrIn.close();
			srvrSoc.close();
			active = false;
		}
	}
	
	static boolean login(String user, String pass) throws IOException {
		if (active) {
			srvrOut.println("LGN" + user + ":" + pass);
			String response = srvrIn.readLine();
			if (response.equals("accept"))
				return true;
			else
				return false;
		}
		return false;
	}
	
	static void sendChat(String user, String msg) {
		if (active)
			srvrOut.println("MSG" + user + ":" + msg);
	}

    static boolean storeFile(String path, String f) {
        try {
			InetAddress srvr = InetAddress.getByName(SERVER);
            Socket dataSock = new Socket(srvr, DATA_PORT);
            PrintWriter op = new PrintWriter(dataSock.getOutputStream(), true);

            op.println("STOR");
            op.println(path);
            op.println(f);
            op.flush();
            op.close();
            dataSock.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String retrieveFile(String path) {
        try {
			InetAddress srvr = InetAddress.getByName(SERVER);
            Socket dataSock = new Socket(srvr, DATA_PORT);
            PrintWriter op = new PrintWriter(dataSock.getOutputStream(), true);
            BufferedReader ip = new BufferedReader(new InputStreamReader(
                        dataSock.getInputStream()));

            op.println("RETR");
            op.println(path);
            String fileStr = "";
            String tmp = "";
            while ((tmp = ip.readLine()) != null)
                fileStr += tmp + "\n";
            ip.close();
            op.close();
            dataSock.close();
            // last char is a spare newline
            if (fileStr.length() > 0)
                fileStr = fileStr.substring(0, fileStr.length() - 1);
            return fileStr;
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized String[] getBuddyList() throws IOException {
        if (active) {
            srvrOut.println("BUD");

            String response = srvrIn.readLine();
            return response.split(":");
        } else
            return new String[0];
    }

    static synchronized void logout(String user) throws IOException {
        if (active)
            srvrOut.println("LOG"+user);
    }
	
	static synchronized String[] updateChat() throws IOException {
		if (active) {
			srvrOut.println("UPD" + lastUpdate);
			
			String response = srvrIn.readLine();
			lastUpdate = Long.parseLong(response);
			
			response = srvrIn.readLine();
			String[] ret = null;
			if (response == null || response.equals("") || response.equals("0"))
				ret = new String[0];
			else {
				int sz = Integer.parseInt(response);
				
				ret = new String[sz];
				for (int i = 0; i < sz; i++)
					ret[i] = srvrIn.readLine();
			}
			return ret;
		}
		return null;
	}
}
