package ip.designpattern.structural.composite;

import java.util.List;

public interface CompositeAddress {
	
	public AddressComponent getAddressComponent();
	
	public String getAddressComponentValue();
	
	public CompositeAddress getParentCompositeAddress();
	
	public void setParentCompositeAddress(CompositeAddress parentCompositeAddress);
	
	public List<CompositeAddress> getChildAddressComponents();
	
	public String getAddress();
	
}
