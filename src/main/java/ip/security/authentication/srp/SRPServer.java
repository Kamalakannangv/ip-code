package ip.security.authentication.srp;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;

public class SRPServer {

	static Set<User> userSet = new HashSet<>();

	public HttpResponse registerUser(HttpObject request){
		HttpResponse response = null;
		try{
			JSONObject requestBody = request.getJsonBody();
			String username = requestBody.getString(SRPConfig.USERNAME);
			User newUser = new User(username);
			newUser.setSalt(requestBody.getString(SRPConfig.SALT));
			newUser.setVerifier(requestBody.getString(SRPConfig.VERIFIER));
			userSet.add(newUser);

			response = new HttpResponse(200, "Ok");
			response.getHeaders().put("Server", "Java HTTP Server");
			response.getHeaders().put("Date", new Date().toString());
			response.getHeaders().put("Content-type", "Application/JSON");

		}catch(JSONException e){
			e.printStackTrace();
		}
		return response;
	}

	public HttpResponse authenticate(HttpObject request){
		HttpResponse response = null;
		try{
			JSONObject requestBody = request.getJsonBody();
			if(requestBody != null){
				String srpAuthStepStr = request.getHeaders().get(SRPConfig.HEADER_AUTH_STEP);
				AuthenticationStep authStep = AuthenticationStep.valueOf(srpAuthStepStr);
				Map<String, String> responseBodyAtts = new HashMap<>();
				switch (authStep) {
				case STEP1:
					if(requestBody.has(SRPConfig.USERNAME)){
						User user = getUserByUsername(requestBody.getString(SRPConfig.USERNAME));
						if(null != user){
							BigInteger salt = new BigInteger(user.getSalt());
							BigInteger verifier = new BigInteger(user.getVerifier());
							SRP6ServerSession srpServerSession = new SRP6ServerSession(SRPConfig.config);
							BigInteger b = srpServerSession.step1(user.getUsername(), salt, verifier);
							user.setSrpServerSession(srpServerSession);
							responseBodyAtts.put(SRPConfig.SALT, salt.toString());
							responseBodyAtts.put(SRPConfig.B, b.toString());
							String sessionId = getRandomString();
							user.setSessionId(sessionId);
							responseBodyAtts.put(SRPConfig.SESSIONID, sessionId);
						}
					}
					break;
				case STEP2:
					if(requestBody.has(SRPConfig.A) && requestBody.has(SRPConfig.M1)){
						User user = getUserBySessionId(requestBody.getString(SRPConfig.SESSIONID));
						BigInteger a = new BigInteger(requestBody.getString(SRPConfig.A));
						BigInteger m1 = new BigInteger(requestBody.getString(SRPConfig.M1));
						BigInteger m2 = user.getSrpServerSession().step2(a, m1);
						responseBodyAtts.put(SRPConfig.M2, m2.toString());
						System.out.println("Encryption key on Server:" + user.getSrpServerSession().getSessionKey().toString());
					}
					break;
				default:
					break;
				}
				Map<String, String> headers = new HashMap<>();
				headers.put("Server", "Java HTTP Server");
				headers.put("Date", new Date().toString());
				headers.put("Content-type", "Application/JSON");
				response = buildResponse(200, "Ok", headers, responseBodyAtts);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}catch (SRP6Exception e) {
			Map<String, String> responseBodyAtts = new HashMap<>();
			responseBodyAtts.put("Error Message", e.getMessage());
			Map<String, String> headers = new HashMap<>();
			headers.put("Server", "Java HTTP Server");
			headers.put("Date", new Date().toString());
			headers.put("Content-type", "Application/JSON");
			response = buildResponse(401, "Unauthorized", headers, responseBodyAtts);
		}
		return response;
	}

	private HttpResponse buildResponse(int statusCode, String statusMsg, Map<String, String> headers, Map<String, String> responseAttributes){
		HttpResponse response = new HttpResponse(statusCode, statusMsg);
		try{
			Iterator<String> headersIter = headers.keySet().iterator();
			while(headersIter.hasNext()){
				String headerName = headersIter.next();
				String headerValue = headers.get(headerName);
				response.getHeaders().put(headerName, headerValue);
			}

			Iterator<String> responseAttIter = responseAttributes.keySet().iterator();
			JSONObject responseBody = new JSONObject();
			while(responseAttIter.hasNext()){
				String attName = responseAttIter.next();
				String attValue = responseAttributes.get(attName);
				responseBody.put(attName, attValue);
			}
			response.setJsonBody(responseBody);
		}catch(JSONException e){
			e.printStackTrace();
		}
		return response;
	}

	private String getRandomString(){
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 30;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}


	public void registerUser(User newUser){

	}

	private User getUserByUsername(String username){
		for(User user : userSet){
			if(user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}

	private User getUserBySessionId(String sessionId){
		for(User user : userSet){
			if(user.getSessionId().equals(sessionId)){
				return user;
			}
		}
		return null;
	}

}
