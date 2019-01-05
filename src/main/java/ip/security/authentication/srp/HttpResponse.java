package ip.security.authentication.srp;

public class HttpResponse extends HttpObject {
	
	private Integer httpStatusCode;
	private String httpStatusDesc;

	public HttpResponse(Integer statusCode, String statusDesc) {
		httpStatusCode = statusCode;
		httpStatusDesc = statusDesc;
	}

	public Integer getHttpStatus() {
		return httpStatusCode;
	}

	public String getHttpStatusDesc() {
		return httpStatusDesc;
	}
	

}
