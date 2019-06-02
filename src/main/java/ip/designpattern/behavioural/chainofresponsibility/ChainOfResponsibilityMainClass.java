package ip.designpattern.behavioural.chainofresponsibility;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ChainOfResponsibilityMainClass {
	
	public static void main(String[] args) {
		ApplicationContext config = new ClassPathXmlApplicationContext("META-INF/config/ip/designpattern/behavioural/chainofresponsibility/config.xml");
		Notifier notifier = (Notifier)config.getBean("smsNotifier");
		
		Customer customer = new Customer("Kamalakannan", "100");
		customer.setAddress("No.31/1, 1st street,\nGovindarajapuram, Nandhivaram,\nGuduvanchery,\nKanchipuram district.\nPincode - 603202");
		customer.setEmailId("kamal@gmail.com");
		customer.setFacebookId("kamal");
		customer.setMobileNo("9876543210");
		
		//customer.setNotificationMedium(NotificationMedium.SMS);
		//customer.setNotificationMedium(NotificationMedium.EMAIL);
		//customer.setNotificationMedium(NotificationMedium.MAIL);
		
		String message = "Premium for your policy number 123456 is due by May 12, 2019";
		
		notifier.sendNotification(message, customer);
	}

}
