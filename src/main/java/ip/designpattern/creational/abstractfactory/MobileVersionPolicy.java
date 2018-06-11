package ip.designpattern.creational.abstractfactory;

import java.util.Date;

public class MobileVersionPolicy extends AbstractPolicy {

	private String policyCustShortName;
	private int premiumAmount;
	private Date nextDueDate; 
	
	public MobileVersionPolicy(long customerId, long policyId) {
		super(customerId, policyId);
	}

	public String getPolicyCustShortName() {
		return policyCustShortName;
	}

	public void setPolicyCustShortName(String policyCustShortName) {
		this.policyCustShortName = policyCustShortName;
	}

	public int getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(int premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	@Override
	public void printPolicy() {
		// TODO Auto-generated method stub

	}

}
