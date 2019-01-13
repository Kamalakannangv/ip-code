package ip.aws.cognito;

public class Inputs {

	public static final boolean IS_PRG_AWS_ACC_ENABLED_WITH_MFA = true;

	public static final String USER_POOL_ID = "xxxxxxxxxxxxxxxxxxx"; 

	public static final boolean IS_APP_CLIENT_HAS_SECRET = true;

	public static final String APP_CLIENT_ID = "xxxxxxxxxxxxxxxxxxxxxxxxxx";  

	public static final String APP_CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"; 

	public static final String MFA_SERIAL_NUMBER = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"; 

	public static final String TEMPORARY_PASSWORD = "xxxxxxx";

	public static final String HMAC_ALGORITHM = "HmacSHA256";

	static class User{
		public static final String USERNAME = "xxxxxxxxxxxxxxxxxxxxx";
		public static final String NAME = "xxxxxxxx";
		public static final String EMAIL = "xxxxxxxxxxxxxxxxxxxx";
		public static final String GENDER = "Male";
		public static final String PROFILE = "xxxxxx";
		public static final String PHONE_NUMBER = "0000000000000";
		public static final String DESIRED_DELIVERY_MEDIUM = "EMAIL";
		public static final String TOKEN_DEVICE_NAME = "Google Authenticator";
	}

}
