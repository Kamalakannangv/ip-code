package ip.designpattern.behavioural.strategy;

public class SmsNotifier implements Notifier {

	@Override
	public void notify(Prospect prospect, String detail) {
		System.out.println("SMS sent to " + prospect.getMobileNo() + " with " + detail);	
	}

}
