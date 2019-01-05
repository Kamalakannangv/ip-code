package ip.aws.cognito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.AssociateSoftwareTokenResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenRequest;
import com.amazonaws.services.cognitoidp.model.VerifySoftwareTokenResult;

public class CognitoMainClass_old {
	
	public static void main(String[] args) {
		CognitoMainClass_old mainClassObj = new CognitoMainClass_old();
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder
													.standard()
													.withRegion(Regions.US_EAST_1)
													.build();
		
		
		// User creation by Admin
		
		AdminCreateUserRequest request = mainClassObj.getAdminCreateUserRequest();
		System.out.println("Request:\n"+request.toString());
		AdminCreateUserResult adminCreateUserResult = cognitoClient.adminCreateUser(request);
		System.out.println("\nResponse:\n"+adminCreateUserResult);
		
		
		
		/*
		 * Authentication call by End user
		AdminInitiateAuthRequest authRequest = mainClassObj.getAdminAuthRequest();
		System.out.println("Auth Request:\n"+authRequest);
		AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);
		System.out.println("\nAuth Response:\n"+authResult);
		
		// Initial challenge question to reset password 
		AdminRespondToAuthChallengeRequest challengeResponseRequest = mainClassObj.getAdminRespondToAuthChallengeRequest(authResult);
		System.out.println("\nAuth Challenge Request:\n"+challengeResponseRequest);
		AdminRespondToAuthChallengeResult challengeResult = cognitoClient.adminRespondToAuthChallenge(challengeResponseRequest);
		System.out.println("\nAuth Challenge Response:\n"+challengeResult);
		*/
		
		/*
		 * Authentication call by End user
		AdminInitiateAuthRequest authRequest = mainClassObj.getAdminAuthRequest();
		System.out.println("Auth Request:\n"+authRequest);
		AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);
		System.out.println("\nAuth Response:\n"+authResult);
		
		Call get secret code for AWS to register with Software token authenticator
		AssociateSoftwareTokenRequest associateSoftwareTokenRequest = mainClassObj.getAssociateSoftwareTokenRequest(authResult);
		System.out.println("\nSoftwareTokenRequest:\n"+associateSoftwareTokenRequest);
		AssociateSoftwareTokenResult associateSoftwareTokenResult = cognitoClient.associateSoftwareToken(associateSoftwareTokenRequest);
		System.out.println("\nSoftwareTokenResult:\n"+associateSoftwareTokenResult);
		
		call to confirm registration by suplying user code from software token authenticator to AWS
		VerifySoftwareTokenRequest verifySoftwareTokenRequest = mainClassObj.getVerifySfTokenRequest(associateSoftwareTokenResult);
		System.out.println("\nVerifySoftwareTokenResult:\n"+verifySoftwareTokenRequest);
		VerifySoftwareTokenResult verifySoftwareTokenResult = cognitoClient.verifySoftwareToken(verifySoftwareTokenRequest);
		System.out.println("\nVerifySoftwareTokenResult:\n"+verifySoftwareTokenResult);
		*/
		
		
		// End User authentication
		/*InitiateAuthRequest authRequest = mainClassObj.getInitiateAuthRequest();
		System.out.println("Auth Request:\n"+authRequest);
		InitiateAuthResult initiateAuthResult = cognitoClient.initiateAuth(authRequest);
		System.out.println("\nAuth Response:\n"+initiateAuthResult);
		
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = mainClassObj.getRespondToAuthChallengeRequest(initiateAuthResult);
		System.out.println("\nAuth Challenge Request:\n"+respondToAuthChallengeRequest);
		RespondToAuthChallengeResult respondToAuthChallengeResult = cognitoClient.respondToAuthChallenge(respondToAuthChallengeRequest);
		System.out.println("\nAuth challegeResponse:\n"+respondToAuthChallengeResult);
		*/
		
		
		System.out.println("");
		
	}
	
	
	private RespondToAuthChallengeRequest getRespondToAuthChallengeRequest(InitiateAuthResult initiateAuthResult){
		RespondToAuthChallengeRequest respondToAuthChallengeRequest = new RespondToAuthChallengeRequest();
		respondToAuthChallengeRequest.setChallengeName(initiateAuthResult.getChallengeName());
		respondToAuthChallengeRequest.setClientId("5hqn4r7jge3p1c3cf4m778fr80");
		respondToAuthChallengeRequest.setSession(initiateAuthResult.getSession());
		Map<String, String> challengeResponseMap = new HashMap<>();
		challengeResponseMap.put("USERNAME", "gkamalakanna@csc.com");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Software Token Code: ");
		String softwareTokenCode = scan.nextLine();
		challengeResponseMap.put("SOFTWARE_TOKEN_MFA_CODE", softwareTokenCode);
		respondToAuthChallengeRequest.setChallengeResponses(challengeResponseMap);
		return respondToAuthChallengeRequest;
	}
	
	
	private InitiateAuthRequest getInitiateAuthRequest(){
		InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
		initiateAuthRequest.setAuthFlow(AuthFlowType.USER_PASSWORD_AUTH);
		Map<String, String> authParams = new HashMap<>();
		authParams.put("USERNAME", "gkamalakanna@csc.com");
		authParams.put("PASSWORD", "ABcd12#$");
		initiateAuthRequest.setAuthParameters(authParams);
		initiateAuthRequest.setClientId("5hqn4r7jge3p1c3cf4m778fr80");
		return initiateAuthRequest;
	}
	
	
	
	
	private VerifySoftwareTokenRequest getVerifySfTokenRequest(AssociateSoftwareTokenResult associateSoftwareTokenResult){
		VerifySoftwareTokenRequest verifySoftwareTokenRequest = new VerifySoftwareTokenRequest();
		verifySoftwareTokenRequest.setFriendlyDeviceName("Google Win Authenticator");
		verifySoftwareTokenRequest.setSession(associateSoftwareTokenResult.getSession());
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter User Code: ");
		String userCode = scan.nextLine();
		verifySoftwareTokenRequest.setUserCode(userCode);
		return verifySoftwareTokenRequest;
	}
	
	
	private AssociateSoftwareTokenRequest getAssociateSoftwareTokenRequest(AdminInitiateAuthResult authResult){
		AssociateSoftwareTokenRequest associateSoftwareTokenRequest = new AssociateSoftwareTokenRequest();
		associateSoftwareTokenRequest.setSession(authResult.getSession());
		return associateSoftwareTokenRequest;
	}
	
	/**
	 * Object for Initial password reset challenge
	 */
	private AdminRespondToAuthChallengeRequest getAdminRespondToAuthChallengeRequest(AdminInitiateAuthResult authResult){
		
		AdminRespondToAuthChallengeRequest challengeResponseRequest = new AdminRespondToAuthChallengeRequest();
		challengeResponseRequest.setChallengeName(authResult.getChallengeName());
		Map<String, String> challengeResponse = new HashMap<>();
		challengeResponse.put("NEW_PASSWORD", "qwER09*&");
		challengeResponse.put("USERNAME", "gkamalakanna@csc.com");
		challengeResponse.put("userAttributes.gender", "Male");
		challengeResponse.put("userAttributes.profile", "12345");
		challengeResponse.put("userAttributes.phone_number", "+19876543210");
		challengeResponse.put("userAttributes.name", "Kamal");
		challengeResponseRequest.setChallengeResponses(challengeResponse);
		//challengeResponseRequest.
		System.out.println("\ntest :"+authResult.getChallengeParameters());
		challengeResponseRequest.setClientId("5hqn4r7jge3p1c3cf4m778fr80");
		challengeResponseRequest.setSession(authResult.getSession());
		challengeResponseRequest.setUserPoolId("us-east-1_OppejjDfw");
		return challengeResponseRequest;
	}
	
	
	private AdminInitiateAuthRequest getAdminAuthRequest(){
		AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
		/*authRequest.setAuthFlow(AuthFlowType.USER_PASSWORD_AUTH);
		authRequest.addAuthParametersEntry("USERNAME", "gkamalakanna@csc.com");
		authRequest.addAuthParametersEntry("PASSWORD", "h8MB*riH");
		authRequest.setClientId("7sikjcqrm7errij1nvrjo0s1dl");
		authRequest.setUserPoolId("us-east-1_OppejjDfw");*/
		Map<String, String> authParams = new HashMap<>();
		authParams.put("USERNAME", "gkamalakanna@csc.com");
		authParams.put("PASSWORD", "ABcd12#$");
		authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
					.withAuthParameters(authParams)
					.withClientId("5hqn4r7jge3p1c3cf4m778fr80")
					.withUserPoolId("us-east-1_OppejjDfw");
		return authRequest;
	}
	
	private AdminCreateUserRequest getAdminCreateUserRequest(){
		AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest();
		List<String> desiredDeliveryMediums = new ArrayList<>();
		desiredDeliveryMediums.add("EMAIL");
		adminCreateUserRequest.setDesiredDeliveryMediums(desiredDeliveryMediums);
		//adminCreateUserRequest.setMessageAction(MessageActionType.SUPPRESS);
		AttributeType emailAttribute = new AttributeType();
		emailAttribute.setName("email");
		emailAttribute.setValue("kamal.gk@gmail.com");
		List<AttributeType> userAttributes = new ArrayList<>();
		userAttributes.add(emailAttribute);
		adminCreateUserRequest.setUserAttributes(userAttributes);
		adminCreateUserRequest.setUsername("kamal.gk@gmail.com");
		adminCreateUserRequest.setUserPoolId("us-east-1_OppejjDfw");
		return adminCreateUserRequest;
	}

}
