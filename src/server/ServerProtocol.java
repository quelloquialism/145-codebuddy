package server;

public class ServerProtocol {
	private ServerState ss = ServerState.PRELOGIN;
		
	public String processInput(String inText) {
		String type = inText.substring(0, 3);
		inText = inText.substring(3);
		
		if (type.equals("LGN") && ss == ServerState.PRELOGIN) {
			// Process login/authentication request
			int splitPoint = inText.indexOf(":");
			String user = inText.substring(0, splitPoint);
			String pass = inText.substring(splitPoint + 1);
			
			boolean valid = DBManager.isValidLogin(user, pass);
			if (valid) {
				ss = ServerState.POSTLOGIN;
				return "accept";
			} else {
				return "reject";
			}
		} else if (type.equals("MSG") && ss == ServerState.POSTLOGIN) {
			// Process submitted chat message
			int splitPoint = inText.indexOf(":");
			String user = inText.substring(0, splitPoint);
			String msg = inText.substring(splitPoint + 1);
			
			DBManager.writeChatMessage(user, msg);
			return null;
		} else if (type.equals("CLK") && ss == ServerState.POSTLOGIN) {
			// Process request for server clock time
			return "" + System.currentTimeMillis();
		} else if (type.equals("UPD") && ss == ServerState.POSTLOGIN) {
			// Process request for chat messages from a given start time
			long lastUpdate = Long.parseLong(inText);
			
			String messages = DBManager.getChatSince(lastUpdate);
			return System.currentTimeMillis() + "\n" + messages;
		}
		return null;
	}
	
	// using an enum is kind of silly for two-state, but we may very well
	// need more states later
	private enum ServerState { PRELOGIN, POSTLOGIN }
}
