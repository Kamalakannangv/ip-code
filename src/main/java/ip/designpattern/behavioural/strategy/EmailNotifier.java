package ip.designpattern.behavioural.strategy;

public class EmailNotifier implements Notifier {

	@Override
	public void notify(Prospect prospect, String detail) {
		System.out.println("Email sent to " + prospect.getEmailId() + " with " + detail);
	}

}
