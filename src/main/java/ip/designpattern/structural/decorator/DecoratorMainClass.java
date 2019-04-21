package ip.designpattern.structural.decorator;

public class DecoratorMainClass {
	
	public static void main(String[] args) {
		Customer customer = new Customer("Kamalakannan", "100");
		customer.setAddress("No.31/1, 1st street,\nGovindarajapuram, Nandhivaram,\nGuduvanchery,\nKanchipuram district.\nPincode - 603202");
		customer.setEmailId("kamal@gmail.com");
		customer.setFacebookId("kamal");
		customer.setMobileNo("9876543210");
		String message = "Premium for your policy number 123456 is due by May 12, 2019";
		
		Notifier mailNotifier = new MailNotifier();
		
		// Notification through Mail and Email
		/* 
		Notifier emailNotifier = new EmailNotifier(mailNotifier);
		emailNotifier.sendNotification(message, customer);
		*/
		
		// Notification through Mail and SMS
		/*
		Notifier smsNotifier = new SMSNotifier(mailNotifier);
		smsNotifier.sendNotification(message, customer);
		*/
		
		// Notification through Mail, Email and SMS
		/*
		Notifier emailNotifier = new EmailNotifier(mailNotifier);
		Notifier smsNotifier = new SMSNotifier(emailNotifier);
		smsNotifier.sendNotification(message, customer);
		*/
		
		// Notification through Email with disclaimer
		/*
		EmailNotifier emailNotifier = new EmailNotifier(mailNotifier);
		Notifier emailDisclaimerNotifier = new EmailDisclaimerNotifier(emailNotifier);
		emailDisclaimerNotifier.sendNotification(message, customer);
		*/
		
		EmailNotifier emailNotifier = new EmailNotifier(mailNotifier);
		Notifier emailDisclaimerNotifier = new EmailDisclaimerNotifier(emailNotifier);
		Notifier smsNotifier = new SMSNotifier(emailDisclaimerNotifier);
		smsNotifier.sendNotification(message, customer);
		
	}

}
