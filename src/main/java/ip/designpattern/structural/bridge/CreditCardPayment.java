package ip.designpattern.structural.bridge;

public class CreditCardPayment extends Payment {

	public CreditCardPayment(PaymentGateway paymentGateway) {
		super(paymentGateway);
	}

	@Override
	public boolean makePayment() {
		System.out.print("Credit Card payment done through ");
		return paymentGateway.processPayment();
	}

}
