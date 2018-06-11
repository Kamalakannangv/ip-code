package ip.designpattern.creational.abstractfactory;


public abstract class AbstractCustomer implements Customer {

	private long customerId;
	
	public long getCustomerId() {
		return customerId;
	}

	public AbstractCustomer(long customerId) {
		this.customerId = customerId;
	}
	
	protected void getData(){
		
	}

	public abstract void printCustomer();
	

}
