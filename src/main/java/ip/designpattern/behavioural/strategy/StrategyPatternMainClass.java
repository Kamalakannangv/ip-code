package ip.designpattern.behavioural.strategy;

public class StrategyPatternMainClass {

	public static void main(String[] args) {
		Prospect prospect = new Prospect("Kamal", "GK");
		prospect.setMobileNo("9876543210");
		prospect.setAddress("xyz, abcd, efghi, jklmnopq");
		prospect.setEmailId("emailId@domain.com");
		prospect.setNotificationPreference("mobile");

		Notifier notifier = null;
		switch (prospect.getNotificationPreference()) {
		case "mobile":
			notifier = new SmsNotifier();
			break;
		case "email":
			notifier = new EmailNotifier();
			break;
		case "mail":
			notifier = new MailNotifier();
			break;
		default:
			break;
		}
		NewBuiness newBuiness = new NewBuiness(prospect, notifier);
		
		newBuiness.issuePolicy();
	}

}
