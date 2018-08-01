package ip.designpattern.structural.composite;

import java.util.ArrayList;
import java.util.List;

public class Proprietor implements CompositeAddress {
	
	private String proprietorName;
	private CompositeAddress parentCompositeAddress;

	public Proprietor(String proprietorName){
		this.proprietorName = proprietorName;
	}

	@Override
	public AddressComponent getAddressComponent() {
		return AddressComponent.PROPRIETOR;
	}

	@Override
	public String getAddressComponentValue() {
		return proprietorName;
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
	public String getAddress() {
		List<Integer> columnLength = new ArrayList<>();
		columnLength.add(8);
		columnLength.add(30);
		List<Object> addressValue = new ArrayList<>();
		addressValue.add(AddressComponent.PROPRIETOR.getValue());
		addressValue.add(proprietorName);
		List<List<Object>> data = new ArrayList<>();
		data.add(addressValue);
		return DisplayUtil.getFormattedString(columnLength, data);
	}

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
		return AddressComponent.PROPRIETOR.getValue() + " : " + proprietorName;
	}

	@Override
	public List<CompositeAddress> getChildAddressComponents() {
		return null;
	}

}
