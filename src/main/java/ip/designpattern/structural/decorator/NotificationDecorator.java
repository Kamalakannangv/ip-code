package ip.designpattern.structural.decorator;

public abstract class NotificationDecorator implements Notifier {
	
	protected Notifier notifier;
	
	public NotificationDecorator(Notifier notifier){
		this.notifier = notifier;
	}

	public void sendNotification(String message, Customer customer){
		notifier.sendNotification(message, customer);
		System.out.println("\n=====================================================================================================================================");
		System.out.println("\n*****************************************************************************************");
		System.out.println("******************** Below message send to customer through " + getNotificationMedium() + " ***********************");
		System.out.println("*****************************************************************************************");
		System.out.println(formatMessage(message, customer));
	}

}
