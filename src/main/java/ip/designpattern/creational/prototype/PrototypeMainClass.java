package ip.designpattern.creational.prototype;

public class PrototypeMainClass {
	
	public void printAddress() throws CloneNotSupportedException{
		
		AddressCollection originalAddressCollection = new AddressCollection();
		System.out.println("Original Address Collection");
		originalAddressCollection.printAllStateAndDistrict();
		
		AddressCollection clonedAddressCollection = (AddressCollection)originalAddressCollection.clone();
		System.out.println("Cloned Address Collection");
		clonedAddressCollection.printAllStateAndDistrict();
		
		// Modifying the cloned Address collection
		ComponentAddress rootComponentAddress = clonedAddressCollection.getRootComponentAddress();
		CompositeAddress newStateAddress = new CompositeAddress(AddressComponentEnum.STATE, "Maharastra");
		newStateAddress.addComponentAddress(new CompositeAddress(AddressComponentEnum.DISTRICT, "Pune"));
		((CompositeAddress)rootComponentAddress).addComponentAddress(newStateAddress);
		
		System.out.println("Original Address Collection after modification");
		originalAddressCollection.printAllStateAndDistrict();
		
		System.out.println("Cloned Address Collection after modification");
		clonedAddressCollection.printAllStateAndDistrict();
		
		AddressList originalAddressList = new AddressList();
		System.out.println("\nOriginal Address List");
		originalAddressList.printAllAddress();
		
		AddressList clonedAddressList = (AddressList)originalAddressList.clone();
		System.out.println("\nCloned Address List");
		clonedAddressList.printAllAddress();
		
		// Modifying the cloned Address list
		Address newAddress = new Address("Kamal", "13/3", "Only street", "Vayalure", 
				"Velur", "Cheyyur", "Kanchipuram", "Tamil Nadu", "India");
		clonedAddressList.getAddresses().add(newAddress);
		
		System.out.println("\nOriginal Address List after modification");
		originalAddressList.printAllAddress();
		
		System.out.println("\nCloned Address List after modification");
		clonedAddressList.printAllAddress();
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		PrototypeMainClass client = new PrototypeMainClass();
		client.printAddress();
	}

}
