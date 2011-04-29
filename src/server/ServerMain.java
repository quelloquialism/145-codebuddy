package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String[] args) throws IOException {
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
	}
	
	
}