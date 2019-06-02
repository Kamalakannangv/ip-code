package ip.designpattern.behavioural.chainofresponsibility;

public class SMSNotifier extends Notifier {

	public SMSNotifier() {
	}
	
	public SMSNotifier(Notifier notifier) {
		super(notifier);
	}
	
	@Override
	String formatMessage(String message, Customer customer) {
		StringBuilder messageContent = new StringBuilder();
		messageContent.append("Mobile Number:" + customer.getMobileNo());
		messageContent.append("\n\nSMS Content");
		messageContent.append("\n------------");
		messageContent.append("\nHi " + customer.getName() +", " + message);
		messageContent.append("\n~DXC Insurance.");
		return messageContent.toString();
	}

	@Override
	public NotificationMedium getNotificationMedium() {
		return NotificationMedium.SMS;
	}

}
