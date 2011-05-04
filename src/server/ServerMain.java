package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		DBManager.init();
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(4444);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			while (true)
				new ServerThread(serverSocket.accept()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		serverSocket.close();
		DBManager.shutdown();
	}
}