package ip.aws.cognito;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenResult;
import com.amazonaws.util.Base64;

public class EndUserSRPAuthenticator {

	private static final int radix = 16;
	private static final String HMAC_ALGORITHM = "HmacSHA256";
	private static final Charset UTF8_CHARSET = Charset.forName("UTF8");

	private SRPClient srpClientUtil;

	public EndUserSRPAuthenticator(){
		srpClientUtil = new SRPClient(HMAC_ALGORITHM);
	}

	public static void main(String[] args) {
		EndUserSRPAuthenticator authenticator = new EndUserSRPAuthenticator();
		authenticator.authenticate();
	}

	private void authenticate(){
		InitiateAuthRequest initiateAuthRequest = initiateUserSRPAuthRequest(Inputs.User.USERNAME);
		CommonUtility.getInstance().display(initiateAuthRequest.toString(), "End User: Request for Authentication");
		InitiateAuthResult initiateAuthResult = CommonUtility.getInstance().getCognitoAPIClient().initiateAuth(initiateAuthRequest);
		CommonUtility.getInstance().display(initiateAuthResult.toString(), "End User: Response for Authentication");
		String challengeNameType = initiateAuthResult.getChallengeName();
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = null;
		RespondToAuthChallengeResult respondToAuthChallengeResult = null;
		String sessionId = initiateAuthResult.getSession();
		while(null != challengeNameType){
			ChallengeNameType challengeEnum = ChallengeNameType.fromValue(challengeNameType);
			switch (challengeEnum) {
			case PASSWORD_VERIFIER:
				String endUserPassword = CommonUtility.getInstance().getInput("End User's current password");
				respondToAuthChallengeRequest = getUserSRPAuthChallengeRequest(initiateAuthResult, endUserPassword);
				CommonUtility.getInstance().display(respondToAuthChallengeRequest.toString(), "End User: Request for SRP 2nd Authentication request");
				try{
					respondToAuthChallengeResult = CommonUtility.getInstance().getCognitoAPIClient().respondToAuthChallenge(respondToAuthChallengeRequest);
				}catch(NotAuthorizedException ex){
					if(ex.getMessage().contains("Incorrect username or password.")){
						System.err.println("Incorrect password");
						return;
					}else{
						throw ex;
					}
				}
				CommonUtility.getInstance().display(respondToAuthChallengeResult.toString(), "End User: Request for SRP 2nd Authentication request");
				System.out.println("As End User: Password verified");
				sessionId = respondToAuthChallengeResult.getSession();
				challengeNameType = respondToAuthChallengeResult.getChallengeName();
				break;

			case NEW_PASSWORD_REQUIRED:
				respondToAuthChallengeRequest = getRespondToAuthChallengeRequestForNewPWD(challengeNameType, sessionId);
				CommonUtility.getInstance().display(respondToAuthChallengeRequest.toString(), "End User: Request for Password Reset");
				respondToAuthChallengeResult = CommonUtility.getInstance().getCognitoAPIClient().respondToAuthChallenge(respondToAuthChallengeRequest);
				CommonUtility.getInstance().display(respondToAuthChallengeResult.toString(), "End User: Response for Password Reset");
				System.out.println("As End User: New Password set");
				sessionId = respondToAuthChallengeResult.getSession();
				challengeNameType = respondToAuthChallengeResult.getChallengeName();
				break;	

			case MFA_SETUP:
				AssociateSoftwareTokenRequest associateSoftwareTokenRequest = getAssociateSoftwareTokenRequest(sessionId);
				CommonUtility.getInstance().display(associateSoftwareTokenRequest.toString(), "End User: Request for MFA SF token security code");
				AssociateSoftwareTokenResult associateSoftwareTokenResult = CommonUtility.getInstance().getCognitoAPIClient().associateSoftwareToken(associateSoftwareTokenRequest);
				CommonUtility.getInstance().display(associateSoftwareTokenResult.toString(), "End User: Response for MFA SF token security code");
				VerifySoftwareTokenRequest verifySoftwareTokenRequest = getVerifySfTokenRequest(associateSoftwareTokenResult);
				CommonUtility.getInstance().display(verifySoftwareTokenRequest.toString(), "End User: Request for Verify SF token");
				VerifySoftwareTokenResult verifySoftwareTokenResult = CommonUtility.getInstance().getCognitoAPIClient().verifySoftwareToken(verifySoftwareTokenRequest);
				CommonUtility.getInstance().display(verifySoftwareTokenResult.toString(), "End User: Response for Verify SF token");
				System.out.println("As End User: MFA Setup completed");
				initiateAuthRequest = initiateUserSRPAuthRequest(Inputs.User.USERNAME);
				CommonUtility.getInstance().display(initiateAuthRequest.toString(), "End User: Request for Authentication");
				initiateAuthResult = CommonUtility.getInstance().getCognitoAPIClient().initiateAuth(initiateAuthRequest);
				CommonUtility.getInstance().display(initiateAuthResult.toString(), "End User: Response for Authentication");
				sessionId = initiateAuthResult.getSession();
				challengeNameType = initiateAuthResult.getChallengeName();
				break;

			case SOFTWARE_TOKEN_MFA:
				respondToAuthChallengeRequest = getTokenAuthChallengeRequest(challengeNameType, sessionId);
				CommonUtility.getInstance().display(respondToAuthChallengeRequest.toString(), "End User: Request for 2nd factor Authentication");
				respondToAuthChallengeResult = CommonUtility.getInstance().getCognitoAPIClient().respondToAuthChallenge(respondToAuthChallengeRequest);
				CommonUtility.getInstance().display(respondToAuthChallengeResult.toString(), "End User: Response for 2nd factor Authentication");
				System.out.println("As End User: Authentication completed");
				sessionId = respondToAuthChallengeResult.getSession();
				challengeNameType = respondToAuthChallengeResult.getChallengeName();
				break;
			default:
				System.out.println("Challenge Name:" + challengeNameType);
				break;
			}
		}
	}

	private InitiateAuthRequest initiateUserSRPAuthRequest(String username) {
		InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
		initiateAuthRequest.setAuthFlow(AuthFlowType.USER_SRP_AUTH);
		initiateAuthRequest.setClientId(Inputs.APP_CLIENT_ID);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			initiateAuthRequest.addAuthParametersEntry(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, username));
		}
		initiateAuthRequest.addAuthParametersEntry(CommonUtility.USERNAME, username);
		initiateAuthRequest.addAuthParametersEntry(CommonUtility.SRP_A, srpClientUtil.getA().toString(radix));
		return initiateAuthRequest;
	}

	private RespondToAuthChallengeRequest getUserSRPAuthChallengeRequest(InitiateAuthResult challenge, String password){
		String userIdForSRP = challenge.getChallengeParameters().get(CommonUtility.SRP_USER_ID_FOR_SRP);
		String username = challenge.getChallengeParameters().get(CommonUtility.USERNAME);
		BigInteger B = new BigInteger(challenge.getChallengeParameters().get(CommonUtility.SRP_B), radix);
		BigInteger salt = new BigInteger(challenge.getChallengeParameters().get(CommonUtility.SRP_SALT), radix);
		byte[] srpAuthKey = srpClientUtil.getSRPAuthenticationKey(userIdForSRP, password, B, salt);
		Date timestamp = new Date();
		byte[] hmac = null;
		try {
			Mac mac = Mac.getInstance(HMAC_ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(srpAuthKey, HMAC_ALGORITHM);
			mac.init(keySpec);
			mac.update(Inputs.USER_POOL_ID.split("_", 2)[1].getBytes(UTF8_CHARSET));
			mac.update(userIdForSRP.getBytes(UTF8_CHARSET));
			byte[] secretBlock = Base64.decode(challenge.getChallengeParameters().get(CommonUtility.SRP_SECRET_BLOCK));
			mac.update(secretBlock);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
			simpleDateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
			String dateString = simpleDateFormat.format(timestamp);
			byte[] dateBytes = dateString.getBytes(UTF8_CHARSET);
			hmac = mac.doFinal(dateBytes);
		} catch (Exception e) {
			System.out.println(e);
		}
		SimpleDateFormat formatTimestamp = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
		formatTimestamp.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
		Map<String, String> srpAuthResponses = new HashMap<>();
		srpAuthResponses.put(CommonUtility.SRP_PASSWORD_CLAIM_SECRET_BLOCK, challenge.getChallengeParameters().get("SECRET_BLOCK"));
		srpAuthResponses.put(CommonUtility.SRP_PASSWORD_CLAIM_SIGNATURE, new String(Base64.encode(hmac), UTF8_CHARSET));
		srpAuthResponses.put(CommonUtility.TIMESTAMP, formatTimestamp.format(timestamp));
		srpAuthResponses.put(CommonUtility.USERNAME, username);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			srpAuthResponses.put(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, username));
		}
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(challenge.getChallengeName());
		respondToAuthChallengeRequest.setClientId(Inputs.APP_CLIENT_ID);
		respondToAuthChallengeRequest.setSession(challenge.getSession());
		respondToAuthChallengeRequest.setChallengeResponses(srpAuthResponses);
		return respondToAuthChallengeRequest;
	}

	private RespondToAuthChallengeRequest getRespondToAuthChallengeRequestForNewPWD(String challengeNameType, String sessionId){
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(challengeNameType);
		Map<String, String> challengeResponse = new HashMap<>();
		challengeResponse.put(CommonUtility.USERNAME, Inputs.User.USERNAME);
		String endUserNewPassword = CommonUtility.getInstance().getInput("End User's new password");
		challengeResponse.put(CommonUtility.NEW_PASSWORD, endUserNewPassword);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			challengeResponse.put(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, Inputs.User.USERNAME));
		}
		respondToAuthChallengeRequest.setChallengeResponses(challengeResponse);
		respondToAuthChallengeRequest.setClientId(Inputs.APP_CLIENT_ID);
		respondToAuthChallengeRequest.setSession(sessionId);
		return respondToAuthChallengeRequest;
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

	private RespondToAuthChallengeRequest getTokenAuthChallengeRequest(String challengeNameType, String sessionId){
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(challengeNameType);
		respondToAuthChallengeRequest.setClientId(Inputs.APP_CLIENT_ID);
		respondToAuthChallengeRequest.setSession(sessionId);
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
