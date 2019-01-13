package ip.aws.cognito;

public class CognitoMainClass {

	public static void main(String[] args) {
		SystemAdmin systemAdmin = new SystemAdmin();
		EndUserPwdResetter endUserPwdResetter = new EndUserPwdResetter();
		EndUserMfaSetter endUserMfaSetter = new EndUserMfaSetter();
		EndUserAuthenticator endUserAuthenticator = new EndUserAuthenticator();

		systemAdmin.createNewUser();

		String sessionId = endUserPwdResetter.resetPassword();
		endUserMfaSetter.setMFA(sessionId);

		endUserAuthenticator.authenticate();
	}

}
