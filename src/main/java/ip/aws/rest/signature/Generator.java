package ip.aws.rest.signature;


import java.net.URI;
import java.net.URISyntaxException;

import uk.co.lucasweb.aws.v4.signer.HttpRequest;
import uk.co.lucasweb.aws.v4.signer.Signer;
import uk.co.lucasweb.aws.v4.signer.credentials.AwsCredentials;

public class Generator {
	
	public static final String ACCESS_KEY = "";
	public static final String SECRET_KEY = "";

	public static String generate(){
		String contentSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
		HttpRequest request = null;;
		try {
			request = new HttpRequest("GET", new URI("http://ip.aws.s3.staticwebsite.s3.us-east-2.amazonaws.com"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String signature = Signer.builder()
		        .awsCredentials(new AwsCredentials(ACCESS_KEY, SECRET_KEY))
		        //.header("Host", "ip.aws.s3.rest.testing.s3.amazonaws.com")
		        .header("Host", "ip.aws.s3.staticwebsite.s3.us-east-2.amazonaws.com")
		        .header("x-amz-date", "20180919T171941Z")
		        .header("x-amz-content-sha256", contentSha256)
		        .region("us-east-2")
		        .buildS3(request, contentSha256)
		        .getSignature();
		return signature;
	}
	
	public static void main(String[] args) {
		System.out.println(generate());
	}
}
