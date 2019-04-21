package ip.aws.cognito;

import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;

public class SystemAdminGetUserDetail {
	
	public static void main(String[] args) {
		SystemAdminGetUserDetail systemAdminGetUserDetail = new SystemAdminGetUserDetail();
		systemAdminGetUserDetail.getUserDetails();
	}
	
	private void getUserDetails(){
		String accessToken = CommonUtility.getInstance().getInput("Access Token");
		GetUserRequest getUserRequestBeforeSignOut = new GetUserRequest();
		getUserRequestBeforeSignOut.setAccessToken(accessToken);
		GetUserResult getUserResultBeforeSignOut = CommonUtility.getInstance().getCognitoAPIClient().getUser(getUserRequestBeforeSignOut);
		CommonUtility.getInstance().display(getUserResultBeforeSignOut.toString(), "System Admin: GetUser response");
	}

}
