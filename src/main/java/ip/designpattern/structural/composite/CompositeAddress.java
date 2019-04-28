package ip.designpattern.structural.composite;

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
		List<Integer> columnLength = new ArrayList<>();
		columnLength.add(10);
		columnLength.add(3);
		columnLength.add(30);
		List<Object> addressValue = new ArrayList<>();
		addressValue.add(addressComponent.getValue());
		addressValue.add(" : ");
		addressValue.add(addressComponentValue);
		List<List<Object>> data = new ArrayList<>();
		data.add(addressValue);
		return DisplayUtil.getFormattedString(columnLength, data);
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

}
