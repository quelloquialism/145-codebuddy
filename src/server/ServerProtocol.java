package server;

public class ServerProtocol {
	private ServerState ss = ServerState.PRELOGIN;
		
	public String processInput(String inText) {
		int splitPoint = inText.indexOf(":");
		String user = inText.substring(0, splitPoint);
		String info = inText.substring(splitPoint + 1);
	
		if (ss == ServerState.PRELOGIN) {
			// here, "info" is password
			// TODO: passwords should actually be salted hashes at this point
			String[] login = inText.split(":");
			
			boolean valid = DBManager.isValidLogin(user, info);
			
			if (valid) {
				ss = ServerState.POSTLOGIN;
				return "accept";
			} else {
				return "reject";
			}
		} else if (ss == ServerState.POSTLOGIN) {
			// here, "info" is a chat message
			DBManager.writeChatMessage(user, info);
		}
		return null;
	}
	
	// using an enum is kind of silly for two-state, but we may very well
	// need more states later
	private enum ServerState { PRELOGIN, POSTLOGIN }
}
