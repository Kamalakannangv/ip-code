package ip.security.authentication.srp;

import com.nimbusds.srp6.SRP6ServerSession;

public class User {

	private String username;
	private String salt;
	private String verifier;
	private String sessionId;
	private SRP6ServerSession srpServerSession;

	public User(String username){
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public SRP6ServerSession getSrpServerSession() {
		return srpServerSession;
	}

	public void setSrpServerSession(SRP6ServerSession srpServerSession) {
		this.srpServerSession = srpServerSession;
	}

}
