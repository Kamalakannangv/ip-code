package ip.designpattern.creational.simplefactory;

public class SimpleFactoryMainClass {
	
	public static void main(String[] args) {
		CustomerSimpleFactory customerSimpleFactory = CustomerSimpleFactory.getInstance();
		System.out.println("******************");
		System.out.println("Mobile Customer:");
		System.out.println("******************");
		customerSimpleFactory.createCustomer("Mobile").printCustomer();
		System.out.println();
		System.out.println("******************");
		System.out.println("Desktop Customer:");
		System.out.println("******************");
		customerSimpleFactory.createCustomer("Desktop").printCustomer();
	}

}
