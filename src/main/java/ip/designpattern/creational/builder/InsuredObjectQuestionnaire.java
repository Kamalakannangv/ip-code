package ip.designpattern.creational.builder;

public class InsuredObjectQuestionnaire implements Questionnaires {

	private boolean isInsuranceRejected;
	private int claimCounts;
	private int kiloMeters;
	
	@Override
	public void isInsuranceRejected(boolean insuranceRejected) {
		this.isInsuranceRejected = insuranceRejected;
	}

	@Override
	public void setNoOfClaimsInLastThreeYear(int claimCounts) {
		this.claimCounts = claimCounts;
	}

	public boolean isInsuranceRejected() {
		return isInsuranceRejected;
	}

	public int getClaimCounts() {
		return claimCounts;
	}

	public int getKiloMeters() {
		return kiloMeters;
	}

	public void setKiloMeters(int kiloMeters) {
		this.kiloMeters = kiloMeters;
	}

	@Override
	public String toString() {
		return "InsuredObjectQuestionnaire [isInsuranceRejected=" + isInsuranceRejected + ", claimCounts=" + claimCounts
				+ ", kiloMeters=" + kiloMeters + "]";
	}
	
}
