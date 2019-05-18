package ip.designpattern.structural.proxy;

import java.util.List;
import java.util.Map;

public class ProxyAddressDirectory implements AddressDirectory {
	
	private AddressCollection addressCollection;
	
	public ProxyAddressDirectory() {
		System.out.println("Creating Address Directory...");
		System.out.println("Address Directory created");
	}
	
	@Override
	public List<String> getAllState() {
		return getAddressCollection().getAllState();
	}

	@Override
	public List<String> getAllDistrict() {
		return getAddressCollection().getAllDistrict();
	}

	@Override
	public Map<String, List<String>> getAllStateAndDistrict() {
		return getAddressCollection().getAllStateAndDistrict();
	}
	
	private AddressCollection getAddressCollection(){
		if(addressCollection == null){
			addressCollection = new AddressCollection();
		}
		return addressCollection;
	}

}
