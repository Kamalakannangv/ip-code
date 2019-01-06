package ip.security.authentication.srp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6VerifierGenerator;

public class SRPClient {
	
	private static final String SERVER_URL = "http://localhost:9876";
	
	public void registerUser(String username, String password){

		// Create verifier generator
		SRP6VerifierGenerator srpVerifierGenerator = new SRP6VerifierGenerator(SRPConfig.config);

		// Random salt
		BigInteger salt = new BigInteger(srpVerifierGenerator.generateRandomSalt(SRPConfig.SRP_SALT_BYTE_SIZE));

		// Compute verifier 'v'
		BigInteger verifier = srpVerifierGenerator.generateVerifier(salt, username, password);

		Map<String, String> headers = new HashMap<>();
		headers.put(SRPConfig.HEADER_CONTENT_TYPE, "application/json");
		headers.put(SRPConfig.HEADER_USER_AGENT, "Java Client");

		JSONObject userRegReqBody = new JSONObject();
		try {
			userRegReqBody.put(SRPConfig.USERNAME, username);
			userRegReqBody.put(SRPConfig.SALT, salt.toString());
			userRegReqBody.put(SRPConfig.VERIFIER, verifier.toString());
			System.out.println("\nUser Registration request\n-----------------------------------------");
			sendHttpRequest(SERVER_URL + SRPConfig.REGISTER_ENDPOINT, "POST", headers, userRegReqBody);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void authenticate(String username, String password){
		try {
			Map<String, String> headers = null;
			JSONObject userAuthReqBody = null;
			JSONObject responseJson = null;
			//Step 1
			SRP6ClientSession srp6ClientSession = new SRP6ClientSession();
			srp6ClientSession.step1(username, password);
			String endPoint = SERVER_URL + SRPConfig.AUTHENTICATE_ENDPOINT;
			headers = new HashMap<>();
			headers.put(SRPConfig.HEADER_CONTENT_TYPE, "application/json");
			headers.put(SRPConfig.HEADER_USER_AGENT, "Java Client");
			headers.put(SRPConfig.HEADER_AUTH_STEP, AuthenticationStep.STEP1.toString());
			
			userAuthReqBody = new JSONObject();
			userAuthReqBody.put(SRPConfig.USERNAME, username);
			System.out.println("\nUser Authentication STEP1 request\n---------------------------------------------");
			responseJson = sendHttpRequest(endPoint, "POST", headers, userAuthReqBody);
			if(null != responseJson){
				//Step 2
				BigInteger salt = new BigInteger(responseJson.getString(SRPConfig.SALT));
				BigInteger b = new BigInteger(responseJson.getString(SRPConfig.B));
				String sessionId = responseJson.getString(SRPConfig.SESSIONID);
				SRP6ClientCredentials clientCredentials = srp6ClientSession.step2(SRPConfig.config, salt, b);
				headers = new HashMap<>();
				headers.put(SRPConfig.HEADER_CONTENT_TYPE, "application/json");
				headers.put(SRPConfig.HEADER_USER_AGENT, "Java Client");
				headers.put(SRPConfig.HEADER_AUTH_STEP, AuthenticationStep.STEP2.toString());
				userAuthReqBody = new JSONObject();
				userAuthReqBody.put(SRPConfig.A, clientCredentials.A.toString());
				userAuthReqBody.put(SRPConfig.M1, clientCredentials.M1.toString());
				userAuthReqBody.put(SRPConfig.SESSIONID, sessionId);
				System.out.println("\nUser Authentication STEP2 request\n---------------------------------------------");
				responseJson = sendHttpRequest(endPoint, "POST", headers, userAuthReqBody);
				if(null != responseJson){
					//Step 3
					if(responseJson.has(SRPConfig.M2)){
						BigInteger m2 = new BigInteger(responseJson.getString(SRPConfig.M2));
						System.out.println("\nUser Authentication STEP3 request\n---------------------------------------------");
						srp6ClientSession.step3(m2);
						System.out.println("Authentication completed, encryption key on Client:" + srp6ClientSession.getSessionKey().toString());
					}
				}
			}
		} catch (JSONException | SRP6Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject sendHttpRequest(String endPoint, String method, Map<String, String> headers, JSONObject requestBody){
		JSONObject responseJson = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			if(method.equalsIgnoreCase("POST")){
				HttpPost request = new HttpPost(endPoint);
				System.out.println("***********\nRequest:\n***********");
				System.out.println(method + " " + endPoint + " HTTP/1.1");
				Iterator<String> headersIter = headers.keySet().iterator();
				while(headersIter.hasNext()){
					String headerName = headersIter.next();
					String headerValue = headers.get(headerName);
					request.addHeader(headerName, headerValue);
					System.out.println(headerName + ": " + headerValue);
				}
				StringEntity jsonEntity = new StringEntity(requestBody.toString(4), ContentType.APPLICATION_FORM_URLENCODED);
				request.setEntity(jsonEntity);
				System.out.println("\n" + requestBody.toString(4));
				
				HttpResponse response = httpClient.execute(request);
				System.out.println("\n***********\nResponse:\n***********");
				System.out.println(response.getStatusLine().getProtocolVersion() + " " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
				Header[] responseHeaders = response.getAllHeaders();
				for(Header header : responseHeaders){
					System.out.println(header.getName() + ": " + header.getValue());
				}
				System.out.println("");
				responseJson = getJsonFromInputStream(response.getEntity().getContent());
				if(null != responseJson){
					System.out.println(responseJson.toString(4).trim());
				}
			}
		} catch (UnsupportedCharsetException | JSONException | IOException e) {
			e.printStackTrace();
		}
		return responseJson;

	}
	
	
	private JSONObject getJsonFromInputStream(InputStream inputStream){
		JSONObject json = null;
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = inputStream.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
			String string = result.toString("UTF-8");
			json = new JSONObject(string);
		} catch (IOException | JSONException e) {
			//e.printStackTrace();
		}
		return json;
	}
	
}
