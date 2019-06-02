package ip.designpattern.behavioural.chainofresponsibility;

public enum NotificationMedium {
	
	MAIL("MAIL"), EMAIL("EMAIL"), SMS("SMS"), FACEBOOK("FACEBOOK"), PHONE("PHONE");
	
	private final String medium;
	
	private NotificationMedium(String medium){
		this.medium = medium;
	}
}
