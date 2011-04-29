package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;

	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
						new InputStreamReader(
						socket.getInputStream()));

			String userInput, outputLine;
			ServerProtocol msp = new ServerProtocol();

			while ((userInput = in.readLine()) != null) {
				outputLine = msp.processInput(userInput);
				out.println(outputLine);
				if (outputLine.equals("Bye"))
					break;
			}
			out.close();
			in.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}