package ip.designpattern.behavioural.chainofresponsibility;

public abstract class Notifier {
	
	private Notifier nextNotifier;
	
	public Notifier() {
	}
	
	public Notifier(Notifier nextNotifier) {
		this.nextNotifier = nextNotifier;
	}
	
	public void sendNotification(String message, Customer customer){
		if(getNotificationMedium() == customer.getNotificationMedium()){
			System.out.println("\n*****************************************************************************************");
			System.out.println("***************** Below message send to customer through " + getNotificationMedium() + " ********************");
			System.out.println("*****************************************************************************************");
			System.out.println(formatMessage(message, customer));
		}else{
			if(nextNotifier != null){
				nextNotifier.sendNotification(message, customer);
			}
		}
	}
	
	abstract String formatMessage(String message, Customer customer);
	
	public abstract NotificationMedium getNotificationMedium();

}
