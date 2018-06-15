package ip.designpattern.creational.factorymethod;

public class FactoryMethodMainClass {
	
	public static void main(String[] args) {
		DisplayApplication device = null;
		//device = new MobileApplication();
		//device = new TabletApplication();
		device = new DesktopApplication();
		device.displayCustomerDetail(123);
	}

}
