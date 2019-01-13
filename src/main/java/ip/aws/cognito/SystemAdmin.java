package ip.aws.cognito;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;

public class SystemAdmin {

	public static void main(String[] args) {
		SystemAdmin admin = new SystemAdmin();
		admin.createNewUser();
	}

	public void createNewUser(){
		System.out.println("\nAs App System: Creating new user...");
		AdminCreateUserRequest adminCreateUserRequest = getAdminCreateUserRequest();
		CommonUtility.getInstance().display(adminCreateUserRequest.toString(), "Request JSON for creating new user");
		AdminCreateUserResult adminCreateUserResult = CommonUtility.getInstance().getCognitoAPIClient().adminCreateUser(adminCreateUserRequest);
		CommonUtility.getInstance().display(adminCreateUserResult.toString(), "Response JSON after creating new user");
		System.out.println("As App System: New user created");
	}

	private AdminCreateUserRequest getAdminCreateUserRequest(){
		AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest();
		List<String> desiredDeliveryMediums = new ArrayList<>();
		desiredDeliveryMediums.add(Inputs.User.DESIRED_DELIVERY_MEDIUM);
		adminCreateUserRequest.setDesiredDeliveryMediums(desiredDeliveryMediums);
		AttributeType nameAttribute = new AttributeType();
		nameAttribute.setName(CommonUtility.USER_ATTRIBUTE_NAME);
		nameAttribute.setValue(Inputs.User.NAME);
		AttributeType emailAttribute = new AttributeType();
		emailAttribute.setName(CommonUtility.USER_ATTRIBUTE_EMAIL);
		emailAttribute.setValue(Inputs.User.EMAIL);
		AttributeType genderAttribute = new AttributeType();
		genderAttribute.setName(CommonUtility.USER_ATTRIBUTE_GENDER);
		genderAttribute.setValue(Inputs.User.GENDER);
		AttributeType profileAttribute = new AttributeType();
		profileAttribute.setName(CommonUtility.USER_ATTRIBUTE_PROFILE);
		profileAttribute.setValue(Inputs.User.PROFILE);
		AttributeType phoneAttribute = new AttributeType();
		phoneAttribute.setName(CommonUtility.USER_ATTRIBUTE_PHONE);
		phoneAttribute.setValue(Inputs.User.PHONE_NUMBER);
		List<AttributeType> userAttributes = new ArrayList<>();
		userAttributes.add(nameAttribute);
		userAttributes.add(emailAttribute);
		userAttributes.add(genderAttribute);
		userAttributes.add(profileAttribute);
		userAttributes.add(phoneAttribute);
		adminCreateUserRequest.setUserAttributes(userAttributes);
		adminCreateUserRequest.setUsername(Inputs.User.USERNAME);
		adminCreateUserRequest.setUserPoolId(Inputs.USER_POOL_ID);
		adminCreateUserRequest.setTemporaryPassword(CommonUtility.getInstance().getTemporaryPassword());
		return adminCreateUserRequest;
	}

}
