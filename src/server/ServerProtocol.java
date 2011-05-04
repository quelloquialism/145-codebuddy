package server;

public class ServerProtocol {
	private ServerState ss = ServerState.PRELOGIN;
		
	public String processInput(String inText) {
	
		if (ss == ServerState.PRELOGIN) {
			// login[0] is username, login[1] is password
			// TODO: passwords should actually be salted hashes at this point
			String[] login = inText.split(":");
			
			boolean valid = DBManager.isValidLogin(login[0], login[1]);
			
			if (valid) {
				ss = ServerState.POSTLOGIN;
				return "accept";
			} else {
				return "reject";
			}
		} else if (ss == ServerState.POSTLOGIN) {
			// TODO: process chat messages?
		}
		return null;
	}
	
	// using an enum is kind of silly for two-state, but we may very well
	// need more states later
	private enum ServerState { PRELOGIN, POSTLOGIN }
}
