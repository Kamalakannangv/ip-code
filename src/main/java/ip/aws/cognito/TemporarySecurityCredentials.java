package ip.aws.cognito;

import java.util.Date;

import com.amazonaws.services.securitytoken.model.Credentials;


public class TemporarySecurityCredentials {

	private String awsAccessKeyId;
	private String awsSecretAccessKey;
	private String awsSessionToken;
	private Date awsSessionTokenExpDate;

	public TemporarySecurityCredentials(Credentials credentials){
		awsAccessKeyId = credentials.getAccessKeyId();
		awsSecretAccessKey = credentials.getSecretAccessKey();
		awsSessionToken = credentials.getSessionToken();
		awsSessionTokenExpDate = credentials.getExpiration();
	}

	public String getAwsAccessKeyId() {
		return awsAccessKeyId;
	}

	public String getAwsSecretAccessKey() {
		return awsSecretAccessKey;
	}

	public String getAwsSessionToken() {
		return awsSessionToken;
	}

	public boolean isExpired(){
		if(awsSessionTokenExpDate.getTime() > (System.currentTimeMillis() + 1000)){
			return false;
		}else{
			return true;
		}
	}

}
