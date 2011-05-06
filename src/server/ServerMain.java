package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
	private static int PORT_NUMBER = 4444;

	public static void main(String[] args) throws IOException {
		DBManager.init();
		System.err.println("Database system startup complete.");
		ServerSocket serverSocket = null;

		System.err.println("Opening port " + PORT_NUMBER + " for listening...");
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.err.println("Listening on port " + PORT_NUMBER + ".");
		try {
			while (true) {
				new ServerThread(serverSocket.accept()).start();
				System.err.println("Connection established.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		serverSocket.close();
		DBManager.shutdown();
	}
}