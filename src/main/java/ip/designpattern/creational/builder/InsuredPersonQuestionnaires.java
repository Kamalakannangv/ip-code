package ip.designpattern.creational.builder;

public class InsuredPersonQuestionnaires implements Questionnaires{

	private boolean isInsuranceRejected;
	private int claimCounts;
	private int insuredWeight;
	private int insuredHeight;
	
	@Override
	public void isInsuranceRejected(boolean insuranceRejected) {
		this.isInsuranceRejected = insuranceRejected;
	}

	@Override
	public void setNoOfClaimsInLastThreeYear(int claimCounts) {
		this.claimCounts = claimCounts;
	}

	public int getInsuredWeight() {
		return insuredWeight;
	}

	public void setInsuredWeight(int insuredWeight) {
		this.insuredWeight = insuredWeight;
	}

	public int getInsuredHeight() {
		return insuredHeight;
	}

	public void setInsuredHeight(int insuredHeight) {
		this.insuredHeight = insuredHeight;
	}

	public boolean isInsuranceRejected() {
		return isInsuranceRejected;
	}

	public int getClaimCounts() {
		return claimCounts;
	}

	@Override
	public String toString() {
		return "InsuredPersonQuestionnaires [isInsuranceRejected=" + isInsuranceRejected + ", claimCounts="
				+ claimCounts + ", insuredWeight=" + insuredWeight + ", insuredHeight=" + insuredHeight + "]";
	}
	
}
