package ip.designpattern.behavioural.chainofresponsibility;

public class DefaultNotifier extends Notifier {
	
	public DefaultNotifier() {
	}

	public void sendNotification(String message, Customer customer){
		System.err.println(formatMessage(message, customer));
	}
	
	@Override
	String formatMessage(String message, Customer customer) {
		StringBuilder messageContent = new StringBuilder();
		messageContent.append("\nHello " + customer.getName() +",");
		messageContent.append("\n" + message);
		messageContent.append("\n\nRegards\nDXC Insurance.");
		return "No notification medicum is set for Customer[Customer Id: " +customer.getCustomerId() + ", Customer Name: " + customer.getName() + "]. So, please contact Admin";
	}

	@Override
	public NotificationMedium getNotificationMedium() {
		return null;
	}

}
