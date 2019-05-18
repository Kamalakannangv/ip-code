package ip.designpattern.structural.proxy;

import java.util.List;

public class ProxyMainClass {
	
	public static void main(String[] args) {
		AddressDirectory addressDirectory = new ProxyAddressDirectory();
		System.out.println("\nClient fetching all state names");
		List<String> states = addressDirectory.getAllState();
		System.out.println(states);
		System.out.println("Client fetched all state names");
	}

}
