package ip.designpattern.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

public class AddressComponentFactory {
	
	private List<IAddressComponent> componentAddressCollection = new ArrayList<>();
	
	public IAddressComponent createAddress(String address){
		IAddressComponent addressComponent = null;
		String addressTemp = address;
		IAddressComponent previousAddressComponent = null;
		while(addressTemp.length() > 0){
			int addressCompSeperatorIndex = addressTemp.indexOf(",");
			String[] firstAddressCompStrVal;
			if(addressCompSeperatorIndex > 0){
				firstAddressCompStrVal = addressTemp.substring(0, addressCompSeperatorIndex).split(":");
			}else{
				firstAddressCompStrVal = addressTemp.split(":");
			}
			AddressComponentEnum addressComponentEnum = AddressComponentEnum.getAddressComponentEnum(firstAddressCompStrVal[0]);
			String addressCompValue = firstAddressCompStrVal[1];
			IAddressComponent currentAddressComponent = getMatchingComponentAddress(addressComponentEnum, addressCompValue, addressTemp);
			if(currentAddressComponent == null){
				currentAddressComponent = new AddressComponent(addressComponentEnum, addressCompValue);
				componentAddressCollection.add(currentAddressComponent);
			}
			if(addressComponent == null){
				addressComponent = currentAddressComponent;
			}else{
				((AddressComponent)currentAddressComponent).setChildAddressComponent(previousAddressComponent);
			}
			previousAddressComponent = currentAddressComponent;
			if(addressCompSeperatorIndex > 0){
				addressTemp = addressTemp.substring(addressCompSeperatorIndex + 1);
			}else{
				addressTemp = "";
			}
		}
		return addressComponent;
	}
	
	private IAddressComponent getMatchingComponentAddress(AddressComponentEnum addressComponent, String addressComponentName, String addressPath){
		IAddressComponent componentAddress = null;
		for(IAddressComponent address : componentAddressCollection){
			if(address.getAddressComponentEnum() == addressComponent 
					&& address.getAddressComponentValue().equalsIgnoreCase(addressComponentName)
					&& address.getAddress().equalsIgnoreCase(addressPath)){
				componentAddress = address;
				break;
			}
		}
		return componentAddress;
	}

	public List<IAddressComponent> getComponentAddressCollection() {
		return componentAddressCollection;
	}

}
