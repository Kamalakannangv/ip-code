package ip.designpattern.structural.bridge;

public class ICICIPaysealPaymentGateway implements PaymentGateway {

	@Override
	public boolean processPayment() {
		System.out.println("ICICI Payseal Payment Gateway");
		return true;
	}

}
