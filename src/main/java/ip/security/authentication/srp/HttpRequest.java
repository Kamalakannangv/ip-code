package ip.security.authentication.srp;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpObject {

	private String endpoint;
	private String httpMethod;
	private Map<String, String> params = new HashMap<>();

	public HttpRequest(String httpMethod, String endpoint){
		this.httpMethod = httpMethod;
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public Map<String, String> getParams() {
		return params;
	}
}
