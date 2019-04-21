package ip.designpattern.structural.decorator;

public class MailNotifier implements Notifier {

	@Override
	public void sendNotification(String message, Customer customer) {
		System.out.println("\n*****************************************************************************************");
		System.out.println("***************** Below message send to customer through " + getNotificationMedium() + " ********************");
		System.out.println("*****************************************************************************************");
		System.out.println(formatMessage(message, customer));
	}

	@Override
	public String formatMessage(String message, Customer customer) {
		StringBuilder messageContent = new StringBuilder();
		messageContent.append("Address on the Envelope:");
		messageContent.append("\n-----------------------");
		messageContent.append("\nTo Address:");
		messageContent.append("\n\t" + customer.getAddress().replace("\n", "\n\t"));
		messageContent.append("\n\nFrom Address:");
		messageContent.append("\n\tDXC Insurance,\n\tChennai");
		messageContent.append("\n\n\nLetter Content");
		messageContent.append("\n----------------");
		messageContent.append("\nDear " + customer.getName() +",");
		messageContent.append("\n" + message);
		messageContent.append("\n\nThanks\nDXC Insurance.");
		return messageContent.toString();
	}

	@Override
	public NotificationMedium getNotificationMedium() {
		return NotificationMedium.MAIL;
	}

}
