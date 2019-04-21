package ip.designpattern.structural.decorator;

public interface Notifier {

	public void sendNotification(String message, Customer customer);
	
	String formatMessage(String message, Customer customer);
	
	public NotificationMedium getNotificationMedium();
	
}
