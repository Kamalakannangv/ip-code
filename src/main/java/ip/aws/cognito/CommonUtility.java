package ip.aws.cognito;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;


public class CommonUtility {

	public static final String AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID";
	public static final String AWS_SECRET_ACCESS_KEY = "AWS_SECRET_ACCESS_KEY";
	public static final String AWS_SESSION_TOKEN = "AWS_SESSION_TOKEN";
	public static final String AWS_SESSION_TOKEN_EXP_DATE = "AWS_SESSION_TOKEN_EXP_DATE";

	public static final String CHALLENGE_NEW_PASSWORD_REQUIRED = "NEW_PASSWORD_REQUIRED";
	public static final String CHALLENGE_MFA_SETUP = "MFA_SETUP";
	public static final String CHALLENGE_SOFTWARE_TOKEN_MFA_CODE = "SOFTWARE_TOKEN_MFA_CODE";

	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String NEW_PASSWORD = "NEW_PASSWORD";
	public static final String SECRET_HASH = "SECRET_HASH";
	public static final String TIMESTAMP = "TIMESTAMP";

	public static final String USER_ATTRIBUTE_NAME = "name";
	public static final String USER_ATTRIBUTE_EMAIL = "email";
	public static final String USER_ATTRIBUTE_GENDER = "gender";
	public static final String USER_ATTRIBUTE_PROFILE = "profile";
	public static final String USER_ATTRIBUTE_PHONE = "phone_number";

	public static final String SRP_USER_ID_FOR_SRP = "USER_ID_FOR_SRP";
	public static final String SRP_A = "SRP_A";
	public static final String SRP_B = "SRP_B";
	public static final String SRP_SALT = "SALT";
	public static final String SRP_SECRET_BLOCK = "SECRET_BLOCK";
	public static final String SRP_PASSWORD_CLAIM_SECRET_BLOCK = "PASSWORD_CLAIM_SECRET_BLOCK";
	public static final String SRP_PASSWORD_CLAIM_SIGNATURE = "PASSWORD_CLAIM_SIGNATURE";

	private static CommonUtility util = null;
	private static TemporarySecurityCredentials tempSecCredentials = null;
	private AWSCognitoIdentityProvider cognitoAPIClient = null;

	private CommonUtility(){
	}

	public AWSCognitoIdentityProvider getCognitoAPIClient() {
		return cognitoAPIClient;
	}

	public static CommonUtility getInstance(){
		if(null == util){
			util = new CommonUtility();
		}
		if(Inputs.IS_PRG_AWS_ACC_ENABLED_WITH_MFA){
			AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(util.getTemporaryCredentials());
			util.cognitoAPIClient = AWSCognitoIdentityProviderClientBuilder
					.standard()
					.withRegion(Regions.US_EAST_1)
					.withCredentials(awsCredentialsProvider)
					.build();
		}else{
			util.cognitoAPIClient = AWSCognitoIdentityProviderClientBuilder
					.standard()
					.withRegion(Regions.US_EAST_1)
					.build();
		}
		return util;
	}

	private AWSCredentials getTemporaryCredentials(){
		AWSCredentials tempAWSCredentials = null;
		if(null == tempSecCredentials || tempSecCredentials.isExpired()){
			GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
			getSessionTokenRequest.setDurationSeconds(900);
			getSessionTokenRequest.setSerialNumber(Inputs.MFA_SERIAL_NUMBER);
			String prgMfaTokenCode = getInput("MFA token code to generate temporary session token");
			getSessionTokenRequest.setTokenCode(prgMfaTokenCode);
			display(getSessionTokenRequest.toString(), "Request JSON to get temporary session token");
			GetSessionTokenResult getSessionTokenResult = AWSSecurityTokenServiceClientBuilder
					.standard()
					.withRegion(Regions.US_EAST_1)
					.build()
					.getSessionToken(getSessionTokenRequest);
			display(getSessionTokenResult.toString(), "Response JSON for getting temporary session token");
			Credentials credentials = getSessionTokenResult.getCredentials();
			tempAWSCredentials = new BasicSessionCredentials(credentials.getAccessKeyId(), 
					credentials.getSecretAccessKey(), 
					credentials.getSessionToken());
			tempSecCredentials = new TemporarySecurityCredentials(credentials);
		}else{
			tempAWSCredentials = new BasicSessionCredentials(tempSecCredentials.getAwsAccessKeyId(),
					tempSecCredentials.getAwsSecretAccessKey(),
					tempSecCredentials.getAwsSessionToken());
		}
		return tempAWSCredentials;
	}

	public InitiateAuthRequest getInitiateAuthRequest(){

		InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
		initiateAuthRequest.setAuthFlow(AuthFlowType.USER_PASSWORD_AUTH);
		Map<String, String> authParams = new HashMap<>();
		authParams.put(USERNAME, Inputs.User.USERNAME);
		String endUserTempPassword = getInput("End User's current password");
		authParams.put(PASSWORD, endUserTempPassword);
		initiateAuthRequest.setAuthParameters(authParams);
		initiateAuthRequest.setClientId(Inputs.APP_CLIENT_ID);
		if(Inputs.IS_APP_CLIENT_HAS_SECRET){
			initiateAuthRequest.addAuthParametersEntry(CommonUtility.SECRET_HASH, CommonUtility.getInstance().getSecretHash(Inputs.APP_CLIENT_ID,Inputs.APP_CLIENT_SECRET, Inputs.User.USERNAME));
		}
		return initiateAuthRequest;
	}

	public String getInput(String input){
		String userInput = null;
		Scanner scan = new Scanner(System.in);
		System.out.print("\nEnter "+ input + " :");
		userInput = scan.nextLine();
		return userInput;
	}

	public String getTemporaryPassword(){
		return Inputs.TEMPORARY_PASSWORD;
	}

	public String getSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
		try {
			Mac mac = Mac.getInstance(Inputs.HMAC_ALGORITHM);
			SecretKeySpec signingKey = new SecretKeySpec(userPoolClientSecret.getBytes(StandardCharsets.UTF_8), Inputs.HMAC_ALGORITHM);
			mac.init(signingKey);
			mac.update(userName.getBytes(StandardCharsets.UTF_8));
			byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
			return java.util.Base64.getEncoder().encodeToString(rawHmac);
		} catch (Exception e) {
			throw new RuntimeException("Error while hashing secret ");
		}
	}
	
	
	public String createSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
		String hmacAlgorithm = "HmacSHA256";
		try {
			Mac mac = Mac.getInstance(hmacAlgorithm);
			SecretKeySpec signingKey = new SecretKeySpec(userPoolClientSecret.getBytes(StandardCharsets.UTF_8), hmacAlgorithm);
			mac.init(signingKey);
			mac.update(userName.getBytes(StandardCharsets.UTF_8));
			byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
			return java.util.Base64.getEncoder().encodeToString(rawHmac);
		} catch (Exception e) {
			throw new RuntimeException("Error while hashing secret ");
		}
	}
	

	public void display(String str, String header){

		System.out.println("\n"+header +":");
		for(int i = 0; i < header.length()+2; i++){
			System.out.print("*");
		}
		JSONObject jsonObject = new JSONObject();
		JSONObject newJsonObject = convertToJson(str, jsonObject);
		if(jsonObject.length() == 0){
			jsonObject = newJsonObject;
		}
		try {
			System.out.println("\n"+jsonObject.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println();

	}

	private JSONObject convertToJson(String str, JSONObject currentJson){
		JSONObject newJson = null;
		Stage stage = Stage.KEY;
		StringBuilder currentKey = new StringBuilder();
		StringBuilder currentValue = new StringBuilder();
		JSONArray jsonArray = null;
		ValueType valueType = ValueType.STRING;
		try{
			for(int charCounter = 0; charCounter < str.length(); charCounter++){
				char character = str.charAt(charCounter);
				switch (character) {
				case '{':
					String jsonStr = getNextCompleteJsonString(str.substring(charCounter));
					newJson = isValidJsonStr(jsonStr);
					if(null == newJson){
						String subStr = jsonStr.substring(1, jsonStr.length()-1);
						JSONObject tempJson = new JSONObject();
						if(currentKey.toString().trim().length() > 0){
							currentJson.put(currentKey.toString().trim(), tempJson);
							currentKey = new StringBuilder();
							newJson = convertToJson(subStr, tempJson);
						}else{
							newJson = convertToJson(subStr, currentJson);
						}
						charCounter = charCounter + jsonStr.length()-1;
					}else{
						if(currentKey.toString().trim().length() > 0){
							currentJson.put(currentKey.toString().trim(), newJson);
							currentKey = new StringBuilder();
						}
						charCounter = charCounter + jsonStr.length() - 1;
					}
					break;

				case ':':
					if(stage == Stage.VALUE && valueType == ValueType.STRING){
						currentValue.append(character);
					}else{
						stage = Stage.VALUE;
					}
					valueType = ValueType.STRING;
					break;

				case ',':
					if(valueType == ValueType.STRING  && currentKey.toString().trim().length() > 0){
						currentJson.put(currentKey.toString().trim(), currentValue.toString().trim());
					}
					currentKey = new StringBuilder();
					currentValue = new StringBuilder();
					stage = Stage.KEY;
					break;

				case '[':
					valueType = ValueType.JSONARRAY;
					jsonArray = new JSONArray();
					int arrayEndOffSet = str.indexOf(']');
					String arrayStr = str.substring(charCounter+1, arrayEndOffSet);
					List<String> jsonStrList = getJsonArray(arrayStr);
					for(String jsonStrItem : jsonStrList){
						JSONObject jsonObject = convertToJson(jsonStrItem, currentJson);
						jsonArray.put(jsonObject);
					}
					charCounter = arrayEndOffSet;
					currentJson.put(currentKey.toString().trim(), jsonArray);
					currentKey = new StringBuilder();
					stage = Stage.KEY;
					break;

				default:
					if(stage == Stage.KEY){
						currentKey.append(character);
					}
					if(stage == Stage.VALUE && valueType == ValueType.STRING){
						currentValue.append(character);
					}
					break;
				}

			}
			if(currentKey.length() > 0){
				if(valueType == ValueType.STRING){
					currentJson.put(currentKey.toString().trim(), currentValue.toString().trim());
				}else if(valueType == ValueType.JSONOBJECT && newJson != null){
					currentJson.put(currentKey.toString().trim(), newJson);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return newJson;
	}

	private JSONObject isValidJsonStr(String str){
		JSONObject jsonObject = null;
		try{
			jsonObject = new JSONObject(str);
		}catch(JSONException e){
			//e.printStackTrace();
		}
		return jsonObject;
	}

	private String getNextCompleteJsonString(String str){
		int count = 0;
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == '{'){
				count++;
			}else if(str.charAt(i) == '}'){
				count--;
				if(count == 0){
					return str.substring(0, i+1);
				}
			}
		}
		return null;
	}

	private List<String> getJsonArray(String str){
		List<String> jsonStrList = new ArrayList<>(); 
		String jsonString = str;
		while(jsonString.length() > 0){
			if(jsonString.startsWith("{")){
				int jsonEndOffSet = jsonString.indexOf('}');
				if(jsonEndOffSet > 0){
					String jsonStrItem = jsonString.substring(0, jsonEndOffSet+1);
					if(jsonStrItem.length() > 0){
						jsonStrList.add(jsonStrItem);
						jsonString = jsonString.substring(jsonEndOffSet+1);
					}
				}else{
					System.out.println("No End flower bracket");
					break;
				}
			}else{
				int jsonStartOffSet = jsonString.indexOf('{');
				jsonString = jsonString.substring(jsonStartOffSet);
			}
		}
		return jsonStrList;
	}

	enum ValueType{
		STRING,
		JSONOBJECT,
		JSONARRAY
	}

	enum Stage{
		KEY,
		VALUE
	}

}
