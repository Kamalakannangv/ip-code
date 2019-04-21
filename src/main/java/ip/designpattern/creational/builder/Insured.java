package ip.designpattern.creational.builder;

public interface Insured {
	
	public void setSumInsured(long sumInsuredAmount);
	
	public void setTerm(int noOfYears);
	
	public long calculatePremium();

}
