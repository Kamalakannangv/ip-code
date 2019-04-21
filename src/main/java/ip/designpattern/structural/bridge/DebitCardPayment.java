package ip.designpattern.structural.bridge;

public class DebitCardPayment extends Payment {

	public DebitCardPayment(PaymentGateway paymentGateway) {
		super(paymentGateway);
	}

	@Override
	public boolean makePayment() {
		System.out.print("Debit Card payment done through ");
		return paymentGateway.processPayment();
	}

}
