package ip.designpattern.structural.bridge;

public class BridgeMainClass {
	
	public static void main(String[] args) {
		
		PaymentGateway IciciPaysealGateway = new ICICIPaysealPaymentGateway();
		PaymentGateway paypalGateway = new PaypalPaymentGateway();
		
		Payment creditCardPayment = new CreditCardPayment(IciciPaysealGateway);
		System.out.println("Credit card + ICICI Payseal");
		creditCardPayment.makePayment();
		
		creditCardPayment = new CreditCardPayment(paypalGateway);
		System.out.println("\nCredit card + PayPal");
		creditCardPayment.makePayment();
		
		Payment debitCardPayment = new DebitCardPayment(IciciPaysealGateway);
		System.out.println("\nDebit card + ICICI Payseal");
		debitCardPayment.makePayment();
		
		debitCardPayment = new DebitCardPayment(paypalGateway);
		System.out.println("\nDebit card + Paypal");
		debitCardPayment.makePayment();
		
	}

}
