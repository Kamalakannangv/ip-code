package ip.aws.cognito;

import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.GlobalSignOutRequest;
import com.amazonaws.services.cognitoidp.model.GlobalSignOutResult;

public class EndUserSignOut {

	public static void main(String[] args) {

		EndUserSignOut endUserSignOut = new EndUserSignOut();
		endUserSignOut.signOut();

	}

	private void signOut(){
		GlobalSignOutRequest globalSignOutRequest = new GlobalSignOutRequest();
		String accessToken = CommonUtility.getInstance().getInput("Access Token");
		globalSignOutRequest.setAccessToken(accessToken);
		GlobalSignOutResult globalSignOutResult = CommonUtility.getInstance().getCognitoAPIClient().globalSignOut(globalSignOutRequest);
		CommonUtility.getInstance().display(globalSignOutResult.toString(), "End User: Sign out response");

		// Try getting user information from Access token ater sign out
		GetUserRequest getUserRequest = new GetUserRequest();
		getUserRequest.setAccessToken(accessToken);

		// Below line will throw exception
		GetUserResult getUserResult = CommonUtility.getInstance().getCognitoAPIClient().getUser(getUserRequest);
		CommonUtility.getInstance().display(getUserResult.toString(), "End User: Sign out response");
	}

}
