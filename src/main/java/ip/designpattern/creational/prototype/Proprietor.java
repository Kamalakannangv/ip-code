package ip.designpattern.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class Proprietor implements ComponentAddress {
	
	private static final long serialVersionUID = 3L;

	private String proprietorName;
	private ComponentAddress parentCompositeAddress;

	public Proprietor(String proprietorName){
		this.proprietorName = proprietorName;
	}

	@Override
	public AddressComponentEnum getAddressComponentEnum() {
		return AddressComponentEnum.PROPRIETOR;
	}

	@Override
	public String getAddressComponentValue() {
		return proprietorName;
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
	public String getCompleteAddress() {
		StringBuffer address = new StringBuffer(AddressComponentEnum.PROPRIETOR.getValue() + ": " + proprietorName);
		if(getParentComponentAddress() != null){
			address.append(", " +getParentComponentAddress().getCompleteAddress());
		}
		return address.toString() ;
	}
	
	@Override
	public String getAddress() {
		return AddressComponentEnum.PROPRIETOR.getValue() + ": " + proprietorName;
	}

	@Override
	public int getComponentAddressCount(AddressComponentEnum addressComponentEnum) {
		if(addressComponentEnum == AddressComponentEnum.PROPRIETOR){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public List<ComponentAddress> getComponentAddress(AddressComponentEnum addressComponentEnum) {
		List<ComponentAddress> componentAddressList = new ArrayList<>();
		if(addressComponentEnum == AddressComponentEnum.PROPRIETOR){
			componentAddressList.add(this);
			return componentAddressList;
		}else{
			return componentAddressList;
		}
	}

	
	@Override
	public List<ComponentAddress> getChildAddressComponents() {
		return null;
	}
	
	/*
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Proprietor){
			Proprietor proprietor = (Proprietor)obj;
			if(proprietor.proprietorName.equals(proprietorName)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return AddressComponentEnum.PROPRIETOR.getValue() + " : " + proprietorName;
	}
	*/
	

}
