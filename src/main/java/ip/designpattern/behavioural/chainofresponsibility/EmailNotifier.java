package ip.designpattern.behavioural.chainofresponsibility;

public class EmailNotifier extends Notifier {
	
	public EmailNotifier() {
	}
	
	public EmailNotifier(Notifier notifier) {
		super(notifier);
	}
	
	@Override
	String formatMessage(String message, Customer customer) {
		StringBuilder messageContent = new StringBuilder();
		messageContent.append("Email address:");
		messageContent.append("\n-------------");
		messageContent.append("\nTo Address   : " + customer.getEmailId());
		messageContent.append("\nFrom Address : " + "admin@dxc.com");
		messageContent.append("\n\n\nEmail Content");
		messageContent.append("\n----------------");
		messageContent.append("\nHello " + customer.getName() +",");
		messageContent.append("\n" + message);
		messageContent.append("\n\nRegards\nDXC Insurance.");
		return messageContent.toString();
	}

	@Override
	public NotificationMedium getNotificationMedium() {
		return NotificationMedium.EMAIL;
	}

}
