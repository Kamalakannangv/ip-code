package ip.designpattern.structural.flyweight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AddressComponent implements IAddressComponent {

	private AddressComponentEnum addressComponentName;
	private String addressComponentValue;
	private IAddressComponent parentAddressComponent;
	private Set<IAddressComponent> childAddressComponent = new HashSet<>();
	
	public AddressComponent(AddressComponentEnum addressComponentName, String addressComponentValue) {
		this.addressComponentName = addressComponentName;
		this.addressComponentValue = addressComponentValue;
	}
	
	@Override
	public String getAddress() {
		StringBuffer address = new StringBuffer(addressComponentName.getValue()+":" + addressComponentValue);
		if(parentAddressComponent != null){
			address.append("," + parentAddressComponent.getAddress());
		}
		return address.toString() ;
	}
	
	public void setChildAddressComponent(IAddressComponent childAddressComponent){
		this.childAddressComponent.add(childAddressComponent);
		childAddressComponent.setParentAddressComponent(this);
	}

	@Override
	public IAddressComponent getParentAddressComponent() {
		return parentAddressComponent;
	}

	@Override
	public void setParentAddressComponent(IAddressComponent parentAddressComponent) {
		this.parentAddressComponent = parentAddressComponent;
	}

	@Override
	public AddressComponentEnum getAddressComponentEnum() {
		return addressComponentName;
	}

	@Override
	public String getAddressComponentValue() {
		return addressComponentValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IAddressComponent){
			IAddressComponent secondAddressComp = (IAddressComponent)obj;
			if(secondAddressComp.getAddressComponentEnum() == addressComponentName && secondAddressComp.getAddressComponentValue().equalsIgnoreCase(addressComponentValue)){
				return true;
			}
		}
		return false;
	}

	public List<AddressComponent> getComponentAddress(AddressComponentEnum addressComponentEnum) {
		List<AddressComponent> componentAddressList = new ArrayList<>();
		if(this.addressComponentName.compareTo(addressComponentEnum) >= 0){
			if(this.addressComponentName == addressComponentEnum){
				componentAddressList.add(this);
			}else{
				for(IAddressComponent child : childAddressComponent){
					if(child instanceof AddressComponent){
						AddressComponent childAddressComponent = (AddressComponent)child;
						List<AddressComponent> matchingComponentAddressList = childAddressComponent.getComponentAddress(addressComponentEnum);
						if(matchingComponentAddressList.size() > 0){
							componentAddressList.addAll(matchingComponentAddressList);
						}
					}
					
				}
			}
		}
		return componentAddressList;
	}
	
}
