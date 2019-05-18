package ip.designpattern.structural.proxy;

import java.util.ArrayList;
import java.util.List;

public class CompositeAddress implements ComponentAddress {

	private AddressComponentEnum addressComponentEnum;
	private String addressComponentValue;
	private ComponentAddress parentCompositeAddress;
	private List<ComponentAddress> childAddressComponents = new ArrayList<>();

	public CompositeAddress(AddressComponentEnum addressComponentEnum, String addressComponentName) {
		this.addressComponentEnum = addressComponentEnum;
		this.addressComponentValue = addressComponentName;
	}
	
	@Override
	public AddressComponentEnum getAddressComponentEnum() {
		return addressComponentEnum;
	}

	@Override
	public String getAddressComponentValue() {
		return addressComponentValue;
	}
	
	@Override
	public ComponentAddress getParentComponentAddress() {
		return parentCompositeAddress;
	}

	@Override
	public void setParentComponentAddress(ComponentAddress parentCompositeAddress) {
		this.parentCompositeAddress = parentCompositeAddress;
	}
	
	@Override
	public List<ComponentAddress> getChildAddressComponents() {
		return childAddressComponents;
	}
	
	@Override
	public String getCompleteAddress() {
		StringBuffer address = new StringBuffer(addressComponentEnum.getValue()+": " + addressComponentValue);
		if(getParentComponentAddress() != null){
			address.append(", " + getParentComponentAddress().getCompleteAddress());
		}
		return address.toString() ;
	}
	
	@Override
	public String getAddress() {
		return addressComponentEnum.getValue()+": " + addressComponentValue;
	}

	@Override
	public int getComponentAddressCount(AddressComponentEnum addressComponentEnum) {
		if(this.addressComponentEnum.compareTo(addressComponentEnum) > 0){
			return this.getComponentAddress(addressComponentEnum).size();
		}
		return 0;
	}

	@Override
	public List<ComponentAddress> getComponentAddress(AddressComponentEnum addressComponentEnum) {
		List<ComponentAddress> componentAddressList = new ArrayList<>();
		if(this.addressComponentEnum.compareTo(addressComponentEnum) >= 0){
			if(this.addressComponentEnum == addressComponentEnum){
				componentAddressList.add(this);
			}else{
				for(ComponentAddress child : childAddressComponents){
					List<ComponentAddress> matchingComponentAddressList = child.getComponentAddress(addressComponentEnum);
					if(matchingComponentAddressList.size() > 0){
						componentAddressList.addAll(matchingComponentAddressList);
					}
				}
			}
		}
		return componentAddressList;
	}
	
	public void addComponentAddress(ComponentAddress componentAddress){
		childAddressComponents.add(componentAddress);
		componentAddress.setParentComponentAddress(this);
	}
	
	public boolean removeComponentAddress(ComponentAddress componentAddress){
		boolean isCompAddrRemoved = childAddressComponents.remove(componentAddress);
		if(isCompAddrRemoved){
			componentAddress.setParentComponentAddress(null);
			return true;
		}
		return false;
	}

	/*
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CompositeAddress){
			CompositeAddress componentAddress = (CompositeAddress)obj;
			if(componentAddress.addressComponentEnum == this.addressComponentEnum
					&& componentAddress.addressComponentValue.equals(this.addressComponentValue)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return addressComponentEnum.getValue() + " : " + addressComponentValue;
	}
	*/	
	
}
