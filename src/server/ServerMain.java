package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
	private static int USER_PORT = 4444;
    private static int DATA_PORT = 4445;

	public static void main(String[] args) throws IOException {
		DBManager.init();
		System.err.println("Database system startup complete.");
		ServerSocket ssUser = null;
        ServerSocket ssData = null;

		System.err.println("Opening user port " + USER_PORT + " for listening...");
		try {
			ssUser = new ServerSocket(USER_PORT);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

        System.err.println("Opening data port " + DATA_PORT + " for listening...");
        try {
            ssData = new ServerSocket(DATA_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        new DataListenThread(ssData).start();
        //new UserListenThread(ssUser).start();

		System.err.println("Listening on user port.");
		try {
			while (true) {
				new ServerThread(ssUser.accept()).start();
				System.err.println("Connection established.");
			}
		} catch (Exception e) {
			e.printStackTrace();
            System.err.println("Error in user listener.  New connections may fail.");
		}

		ssUser.close();
        ssData.close();
		DBManager.shutdown();
	}
}

class DataListenThread extends Thread {
    private ServerSocket ssData = null;

    public DataListenThread(ServerSocket ss) {
        super("DataListenThread");
        ssData = ss;
    }

    public void run() {
        System.err.println("Listening on data port.");
        try {
            while (true) {
                new TransferThread(ssData.accept()).start();
                System.err.println("File transfer started.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in data listener.  File transfers may fail.");
        }
    }
}
