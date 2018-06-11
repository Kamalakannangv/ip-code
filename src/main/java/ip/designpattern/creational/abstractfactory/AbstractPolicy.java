package ip.designpattern.creational.abstractfactory;

public abstract class AbstractPolicy implements Policy {
	
	private long customerId;
	private long policyId;
	
	public AbstractPolicy(long customerId, long policyId){
		this.customerId = customerId;
		this.policyId = policyId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getPolicyId() {
		return policyId;
	}

	public abstract void printPolicy();

}
