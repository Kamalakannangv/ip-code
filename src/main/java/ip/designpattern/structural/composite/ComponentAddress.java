package ip.designpattern.structural.composite;

import java.util.ArrayList;
import java.util.List;

public class ComponentAddress implements CompositeAddress {

	private AddressComponent addressComponent;
	private String addressComponentValue;
	private CompositeAddress parentCompositeAddress;
	private List<CompositeAddress> childAddressComponents = new ArrayList<>();

	public ComponentAddress(AddressComponent addressComponent, String addressComponentName) {
		this.addressComponent = addressComponent;
		this.addressComponentValue = addressComponentName;
	}
	
	@Override
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	@Override
	public String getAddressComponentValue() {
		return addressComponentValue;
	}
	
	@Override
	public CompositeAddress getParentCompositeAddress() {
		return parentCompositeAddress;
	}

	@Override
	public void setParentCompositeAddress(CompositeAddress parentCompositeAddress) {
		this.parentCompositeAddress = parentCompositeAddress;
		parentCompositeAddress.getChildAddressComponents().add(this);
	}
	
	@Override
	public List<CompositeAddress> getChildAddressComponents() {
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
		if(obj instanceof ComponentAddress){
			ComponentAddress componentAddress = (ComponentAddress)obj;
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
