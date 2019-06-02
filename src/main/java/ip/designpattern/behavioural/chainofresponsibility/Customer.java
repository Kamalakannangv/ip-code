package ip.designpattern.behavioural.chainofresponsibility;

public class Customer {

	private String name;
	private String customerId;

	private String emailId;
	private String mobileNo;
	private String address;
	private String facebookId;
	private NotificationMedium notificationMedium;
	
	public Customer(String name, String customerId) {
		this.name = name;
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public NotificationMedium getNotificationMedium() {
		return notificationMedium;
	}

	public void setNotificationMedium(NotificationMedium notificationMedium) {
		this.notificationMedium = notificationMedium;
	}

	public String getCustomerId() {
		return customerId;
	}
	
}
