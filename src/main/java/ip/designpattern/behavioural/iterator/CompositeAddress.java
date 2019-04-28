package ip.designpattern.behavioural.iterator;

import java.util.ArrayList;
import java.util.List;

public class CompositeAddress implements ComponentAddress {

	private AddressComponentEnum addressComponent;
	private String addressComponentValue;
	private ComponentAddress parentCompositeAddress;
	private List<ComponentAddress> childAddressComponents = new ArrayList<>();

	public CompositeAddress(AddressComponentEnum addressComponent, String addressComponentName) {
		this.addressComponent = addressComponent;
		this.addressComponentValue = addressComponentName;
	}
	
	@Override
	public AddressComponentEnum getAddressComponent() {
		return addressComponent;
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
		parentCompositeAddress.getChildAddressComponents().add(this);
	}
	
	@Override
	public List<ComponentAddress> getChildAddressComponents() {
		return childAddressComponents;
	}
	
	@Override
	public String getAddress() {
		StringBuffer address = new StringBuffer(addressComponent.getValue()+": " + addressComponentValue);
		if(getParentComponentAddress() != null){
			address.append(", " + getParentComponentAddress().getAddress());
		}
		return address.toString() ;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CompositeAddress){
			CompositeAddress componentAddress = (CompositeAddress)obj;
			if(componentAddress.addressComponent == this.addressComponent
					&& componentAddress.addressComponentValue.equals(this.addressComponentValue)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return addressComponent.getValue() + " : " + addressComponentValue;
	}

	@Override
	public ComponentAddress getLeftLeafComponentAddress() {
		if(childAddressComponents.size() > 0){
			return childAddressComponents.get(0).getLeftLeafComponentAddress();
		}
		return null;
	}

	@Override
	public ComponentAddress getRigthSibling() {
		List<ComponentAddress> siblings = null;
		int siblingIndex = getRightSiblingIndex();
		if(siblingIndex > 0 && siblingIndex < (siblings = getParentComponentAddress().getChildAddressComponents()).size()){
			return siblings.get(siblingIndex);
		}else{
			return null;
		}
	}

	@Override
	public boolean hasRightSibling() {
		int siblingIndex = getRightSiblingIndex();
		return siblingIndex > 0 && siblingIndex < (getParentComponentAddress().getChildAddressComponents()).size() ;
	}
	
	private int getRightSiblingIndex(){
		ComponentAddress parentComponentAddress = getParentComponentAddress();
		if(null == parentComponentAddress){
			return -1;
		}else{
			List<ComponentAddress> siblings = parentComponentAddress.getChildAddressComponents();
			int siblingIndex;
			for(siblingIndex = 0; siblingIndex < siblings.size(); siblingIndex++){
				if(this == siblings.get(siblingIndex)){
					break;
				}
			}
			return ++siblingIndex;
		}
		
	}

}
