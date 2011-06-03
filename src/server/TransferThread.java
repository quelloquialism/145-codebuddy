package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TransferThread extends Thread {
	private Socket socket = null;

	public TransferThread(Socket socket) {
		super("TransferThread");
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

            String type = in.readLine();
            if (type.equals("STOR")) {
                String path = in.readLine();

                String fileStr = "";
                String tmp = "";
                while ((tmp = in.readLine()) != null)
                    fileStr += tmp + "\n";
                // last char is stray newline
                if (fileStr.length() > 0)
                    fileStr = fileStr.substring(0, fileStr.length() - 1);
                DBManager.putFile(path, fileStr);

            } else if (type.equals("RETR")) {
                String path = in.readLine();

                String fileStr = DBManager.getFile(path);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(fileStr);
                out.flush();
                out.close();
            }

            in.close();
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
