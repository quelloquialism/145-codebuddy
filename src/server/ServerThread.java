package server;

import java.io.*;
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
				if (outputLine != null) {
					if (outputLine.contains("\n")) {
						String[] tmp = outputLine.split("\n");
						out.println(tmp[0]);
						out.println("" + (tmp.length-1));
						for (int i = 1; i < tmp.length; i++)
							out.println(tmp[i]);
					} else {
						out.println(outputLine);
					}
				}
			}
			out.close();
			in.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}