package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionManager {
	private static final String SERVER = "minthe.ugcs.caltech.edu";
	private static final int PORT_NUMBER = 4444;
    private static Socket srvrSoc = null;
	private static PrintWriter srvrOut = null;
	private static BufferedReader srvrIn = null;
	
	private static long lastUpdate = 0;
	
	private static boolean active = false;
	
	static void openConnection() throws Exception {
		if (!active) {
			// Acquire a read/write socket connection to the server
			InetAddress srvr = InetAddress.getByName(SERVER);
			srvrSoc = new Socket(srvr, PORT_NUMBER);
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
