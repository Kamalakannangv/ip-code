package ip.designpattern.structural.bridge;

public class PaypalPaymentGateway implements PaymentGateway {

	@Override
	public boolean processPayment() {
		System.out.println("PayPal Payment Gateway");
		return true;
	}

}
