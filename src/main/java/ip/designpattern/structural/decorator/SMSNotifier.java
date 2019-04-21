package ip.designpattern.structural.decorator;

public class SMSNotifier extends NotificationDecorator {

	public SMSNotifier(Notifier notifier) {
		super(notifier);
	}

	@Override
	public String formatMessage(String message, Customer customer) {
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
