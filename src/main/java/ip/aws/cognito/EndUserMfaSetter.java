package ip.aws.cognito;

import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenResult;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenResult;

public class EndUserMfaSetter {

	public static void main(String[] args) {
		EndUserMfaSetter userMfaSetter = new EndUserMfaSetter();
		userMfaSetter.setMFA();
	}

	public void setMFA(String... sessionIdArray){
		System.out.println("\nAs End User: Setting MFA...");
		String sessionId = null;
		if(sessionIdArray.length == 1){
			sessionId = sessionIdArray[0];
		}

		if(null == sessionId){
			InitiateAuthRequest initiateAuthRequest = CommonUtility.getInstance().getInitiateAuthRequest();
			CommonUtility.getInstance().display(initiateAuthRequest.toString(), "End User: Request for Authentication");
			InitiateAuthResult initiateAuthResult = CommonUtility.getInstance().getCognitoAPIClient().initiateAuth(initiateAuthRequest);
			CommonUtility.getInstance().display(initiateAuthResult.toString(), "End User: Resonse for Authentication");
			sessionId = initiateAuthResult.getSession();
		}

		AssociateSoftwareTokenRequest associateSoftwareTokenRequest = getAssociateSoftwareTokenRequest(sessionId);
		CommonUtility.getInstance().display(associateSoftwareTokenRequest.toString(), "End User: Request for MFA SF token security code");
		AssociateSoftwareTokenResult associateSoftwareTokenResult = CommonUtility.getInstance().getCognitoAPIClient().associateSoftwareToken(associateSoftwareTokenRequest);
		CommonUtility.getInstance().display(associateSoftwareTokenResult.toString(), "End User: Response for MFA SF token security code");
		VerifySoftwareTokenRequest verifySoftwareTokenRequest = getVerifySfTokenRequest(associateSoftwareTokenResult);
		CommonUtility.getInstance().display(verifySoftwareTokenRequest.toString(), "End User: Request for Verify SF token");
		VerifySoftwareTokenResult verifySoftwareTokenResult = CommonUtility.getInstance().getCognitoAPIClient().verifySoftwareToken(verifySoftwareTokenRequest);
		CommonUtility.getInstance().display(verifySoftwareTokenResult.toString(), "End User: Response for Verify SF token");
		System.out.println("As End User: MFA Setup completed");
	}

	private AssociateSoftwareTokenRequest getAssociateSoftwareTokenRequest(String sessionId){
		AssociateSoftwareTokenRequest associateSoftwareTokenRequest = new AssociateSoftwareTokenRequest();
		associateSoftwareTokenRequest.setSession(sessionId);
		return associateSoftwareTokenRequest;
	}


	private VerifySoftwareTokenRequest getVerifySfTokenRequest(AssociateSoftwareTokenResult associateSoftwareTokenResult){
		VerifySoftwareTokenRequest verifySoftwareTokenRequest = new VerifySoftwareTokenRequest();
		verifySoftwareTokenRequest.setFriendlyDeviceName(Inputs.User.TOKEN_DEVICE_NAME);
		verifySoftwareTokenRequest.setSession(associateSoftwareTokenResult.getSession());
		String tokenCode = CommonUtility.getInstance().getInput("Authenticator user code");
		verifySoftwareTokenRequest.setUserCode(tokenCode);
		return verifySoftwareTokenRequest;
	}

}
