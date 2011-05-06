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
	
	private static boolean active = false;
	
	static void openConnection() throws Exception {
		if (!active) {
			InetAddress srvr = InetAddress.getByName(SERVER);
			srvrSoc = new Socket(srvr, PORT_NUMBER);
			srvrOut = new PrintWriter(srvrSoc.getOutputStream(), true);
			srvrIn = new BufferedReader(new InputStreamReader(
					srvrSoc.getInputStream()));
			active = true;
		}
	}
	
	static void closeConnection() throws IOException {
		if (active) {
			srvrOut.close();
			srvrIn.close();
			srvrSoc.close();
			active = false;
		}
	}
	
	static void sendString(String s) {
		if (active)
			srvrOut.println(s);
	}
	
	static String readLine() throws IOException {
		if (active)
			return srvrIn.readLine();
		else
			return null;
	}
}
