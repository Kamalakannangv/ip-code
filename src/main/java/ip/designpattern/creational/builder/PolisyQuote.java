package ip.designpattern.creational.builder;

import java.util.List;

public class PolisyQuote extends Quote {
	
	private long maintenanceCharge;

	public PolisyQuote(Prospect prospect, Insured insured, List<Insured> additionalInsureds,
			Questionnaires questionnaires, long maintenanceCharge) {
		super(prospect, insured, additionalInsureds, questionnaires);
		this.maintenanceCharge = maintenanceCharge;
	}

	public long getMaintenanceCharge() {
		return maintenanceCharge;
	}

	@Override
	public String toString() {
		return "PolisyQuote [maintenanceCharge=" + maintenanceCharge + ", toString()=" + super.toString() + "]";
	}

}
