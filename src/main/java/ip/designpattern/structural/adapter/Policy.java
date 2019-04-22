package ip.designpattern.structural.adapter;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;

public class Policy {
	
	@XmlAttribute
	private long policyNumber;
	private long premiumAmount;
	private Date nextDueDate;
	private long sumInsured;
	
	public Policy(long policyNumber) {
		this.policyNumber = policyNumber;
	}

	@XmlAttribute
	public long getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	@XmlAttribute
	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	@XmlAttribute
	public long getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(long sumInsured) {
		this.sumInsured = sumInsured;
	}
	

}
