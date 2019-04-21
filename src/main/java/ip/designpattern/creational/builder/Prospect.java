package ip.designpattern.creational.builder;

public class Prospect {

	private String name;
	private String prospectId;

	private String emailId;
	private String mobileNo;
	private String address;
	private String notificationPreference;

	public Prospect(String name, String prospectId) {
		this.name = name;
		this.prospectId = prospectId;
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

	public String getNotificationPreference() {
		return notificationPreference;
	}

	public void setNotificationPreference(String notificationPreference) {
		this.notificationPreference = notificationPreference;
	}

	@Override
	public String toString() {
		return "Prospect [name=" + name + ", prospectId=" + prospectId + ", emailId=" + emailId + ", mobileNo="
				+ mobileNo + ", address=" + address + ", notificationPreference=" + notificationPreference + "]";
	}

	
}
