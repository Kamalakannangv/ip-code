package ip.aws.cognito;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;

public class EndUserPwdResetter {

	public static void main(String[] args) {
		EndUserPwdResetter userPwdResetMfaSetter = new EndUserPwdResetter();
		userPwdResetMfaSetter.resetPassword();
	}

	public String resetPassword(){
		System.out.println("\nAs End User: Resetting Password...");
		String sessionId = null;
		InitiateAuthRequest initiateAuthRequest = CommonUtility.getInstance().getInitiateAuthRequest();
		CommonUtility.getInstance().display(initiateAuthRequest.toString(), "End User: Request for Authentication");
		InitiateAuthResult initiateAuthResult = CommonUtility.getInstance().getCognitoAPIClient().initiateAuth(initiateAuthRequest);
		CommonUtility.getInstance().display(initiateAuthResult.toString(), "End User: Resonse for Authentication");

		if(initiateAuthResult.getChallengeName().equalsIgnoreCase(CommonUtility.CHALLENGE_NEW_PASSWORD_REQUIRED)){
			RespondToAuthChallengeRequest respondToAuthChallengeRequest = getRespondToAuthChallengeRequestForNewPWD(initiateAuthResult);
			CommonUtility.getInstance().display(respondToAuthChallengeRequest.toString(), "End User: Request for Password Reset");
			RespondToAuthChallengeResult respondToAuthChallengeResult = CommonUtility.getInstance().getCognitoAPIClient().respondToAuthChallenge(respondToAuthChallengeRequest);
			CommonUtility.getInstance().display(respondToAuthChallengeResult.toString(), "End User: Response for Password Reset");
			sessionId = respondToAuthChallengeResult.getSession();
		}
		System.out.println("As End User: Password reset completed");
		return sessionId;
	}

	private RespondToAuthChallengeRequest getRespondToAuthChallengeRequestForNewPWD(InitiateAuthResult initiateAuthResult){
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(initiateAuthResult.getChallengeName());
		Map<String, String> challengeResponse = new HashMap<>();
		challengeResponse.put(CommonUtility.USERNAME, Inputs.User.USERNAME);
		String endUserNewPassword = CommonUtility.getInstance().getInput("End User's new password");
		challengeResponse.put(CommonUtility.NEW_PASSWORD, endUserNewPassword);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			challengeResponse.put(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, Inputs.User.USERNAME));
		}
		respondToAuthChallengeRequest.setChallengeResponses(challengeResponse);
		respondToAuthChallengeRequest.setClientId(Inputs.APP_CLIENT_ID);
		respondToAuthChallengeRequest.setSession(initiateAuthResult.getSession());
		return respondToAuthChallengeRequest;
	}

}
