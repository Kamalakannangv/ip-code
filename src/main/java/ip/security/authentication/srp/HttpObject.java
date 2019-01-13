package ip.security.authentication.srp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class HttpObject {

	private Map<String, String> headers = new HashMap<>();
	private StringBuilder requestBody = new StringBuilder();

	private JSONObject jsonBody;

	public StringBuilder getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(StringBuilder requestBody) {
		this.requestBody = requestBody;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public JSONObject getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(JSONObject jsonBody) {
		this.jsonBody = jsonBody;
	}

}
