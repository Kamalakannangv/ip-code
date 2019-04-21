package ip.designpattern.behavioural.strategy;

public class NewBuiness {

	private Prospect prospect;
	private Notifier notifier;

	public NewBuiness(Prospect prospect, Notifier notifier) {
		this.prospect = prospect;
		this.notifier = notifier;
	}

	public Prospect getProspect() {
		return prospect;
	}

	public void issuePolicy(){
		// policy generation stuffs.. 
		String policyNo = "123456";

		// notify customer
		notifier.notify(prospect, policyNo);
	}



}
