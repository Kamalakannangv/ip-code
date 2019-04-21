package ip.designpattern.structural.bridge;

public abstract class Payment {
	
	protected PaymentGateway paymentGateway;
	
	public Payment(PaymentGateway paymentGateway){
		this.paymentGateway = paymentGateway;
	}
	
	public abstract boolean makePayment();

}
