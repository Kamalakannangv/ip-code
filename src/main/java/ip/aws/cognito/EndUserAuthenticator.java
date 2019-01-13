package ip.aws.cognito;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;

public class EndUserAuthenticator {

	public static void main(String[] args) {
		EndUserAuthenticator userAuthenticator = new EndUserAuthenticator();
		userAuthenticator.authenticate();
	}

	public void authenticate(){
		System.out.println("\nAs End User: Authentication inprogress...");
		InitiateAuthRequest initiateAuthRequest = CommonUtility.getInstance().getInitiateAuthRequest();
		CommonUtility.getInstance().display(initiateAuthRequest.toString(), "End User: Request for Authentication");
		InitiateAuthResult initiateAuthResult = CommonUtility.getInstance().getCognitoAPIClient().initiateAuth(initiateAuthRequest);
		CommonUtility.getInstance().display(initiateAuthResult.toString(), "End User: Response for Authentication");
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = getRespondToAuthChallengeRequest(initiateAuthResult);
		CommonUtility.getInstance().display(respondToAuthChallengeRequest.toString(), "End User: Request for 2nd factor Authentication");
		RespondToAuthChallengeResult respondToAuthChallengeResult = CommonUtility.getInstance().getCognitoAPIClient().respondToAuthChallenge(respondToAuthChallengeRequest);
		CommonUtility.getInstance().display(respondToAuthChallengeResult.toString(), "End User: Response for 2nd factor Authentication");
		System.out.println("As End User: Authentication completed");
	}

	private RespondToAuthChallengeRequest getRespondToAuthChallengeRequest(InitiateAuthResult initiateAuthResult){
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(initiateAuthResult.getChallengeName());
		respondToAuthChallengeRequest.setClientId(Inputs.APP_CLIENT_ID);
		respondToAuthChallengeRequest.setSession(initiateAuthResult.getSession());
		Map<String, String> challengeResponseMap = new HashMap<>();
		challengeResponseMap.put(CommonUtility.USERNAME, Inputs.User.USERNAME);
		String softwareTokenCode = CommonUtility.getInstance().getInput("Software token MFA code");
		challengeResponseMap.put(CommonUtility.CHALLENGE_SOFTWARE_TOKEN_MFA_CODE, softwareTokenCode);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			challengeResponseMap.put(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, Inputs.User.USERNAME));
		}
		respondToAuthChallengeRequest.setChallengeResponses(challengeResponseMap);
		return respondToAuthChallengeRequest;
	}
}
