package ip.designpattern.behavioural.strategy;

public class MailNotifier implements Notifier {

	@Override
	public void notify(Prospect prospect, String detail) {
		System.out.println("Main sent to " + prospect.getAddress() + " with " + detail);
	}

}
