package ip.designpattern.creational.builder;

public class InsuredPerson implements Insured{
	
	private long sumInsured;
	private int term;
	private Prospect prospect;
	
	public InsuredPerson(Prospect prospect) {
		this.prospect = prospect;
	}

	@Override
	public void setSumInsured(long sumInsuredAmount) {
		sumInsured = sumInsuredAmount;
	}

	@Override
	public long calculatePremium() {
		if(sumInsured > 0 && term > 0){
			return sumInsured/term;
		}
		return 0;
	}

	@Override
	public void setTerm(int noOfYears) {
		term = noOfYears;
	}

	public Prospect getProspect() {
		return prospect;
	}

	@Override
	public String toString() {
		return "InsuredPerson [sumInsured=" + sumInsured + ", term=" + term + ", prospect=" + prospect + "]";
	}

}
