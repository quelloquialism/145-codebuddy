package server;

public class ServerProtocol {
	private ServerState ss = ServerState.PRELOGIN;
	
	// TODO: this is going to die when i roll out the JavaDBs
	private String[] users = {"mike", "scott", "nathan"};
	private String[] passwords = {"ekim", "ttocs", "nahtan"};
		
	public String processInput(String inText) {
	
		// TODO: back this with a members JavaDB
		if (ss == ServerState.PRELOGIN) {
			// login[0] is username, login[1] is password
			// TODO: passwords should actually be salted hashes at this point
			String[] login = inText.split(":");
			
			boolean valid = false;
			for (int i = 0; i < users.length; i++)
				if (users[i].equals(login[0]) && passwords[i].equals(login[1]))
					valid = true;
			
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
