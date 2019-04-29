package ip.designpattern.structural.composite;

public class CompositeMainClass {

	public static void main(String[] args) {
		
		AddressCollection addressCollection = new AddressCollection();
		addressCollection.printAllState(addressCollection.getRootComponentAddress());
		addressCollection.printAllDistrict(addressCollection.getRootComponentAddress());
		addressCollection.printAllStateAndDistrict(addressCollection.getRootComponentAddress());
		
	}

}
