package ip.designpattern.structural.decorator;

public class EmailDisclaimerNotifier extends NotificationDecorator {

	private EmailNotifier emailNotifier;
	
	public EmailDisclaimerNotifier(EmailNotifier emailNotifier) {
		super(emailNotifier);
		this.emailNotifier = emailNotifier;
	}

	@Override
	public String formatMessage(String message, Customer customer) {
		return emailNotifier.formatMessage(message, customer);
	}

	@Override
	public NotificationMedium getNotificationMedium() {
		return emailNotifier.getNotificationMedium();
	}
	
	@Override
	public void sendNotification(String message, Customer customer) {
		System.out.println("\n=====================================================================================================================================");
		System.out.println("\n*****************************************************************************************");
		System.out.println("******************** Below message send to customer through " + getNotificationMedium() + " ***********************");
		System.out.println("*****************************************************************************************");
		System.out.println(formatMessage(message, customer));
		System.out.println("\nDISCLAIMER:");
		System.out.println("This is email is send from DXC insurance and is intended only for addressed customer");
	}

}
