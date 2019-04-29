package ip.designpattern.structural.composite;

import java.util.List;

public interface ComponentAddress {
	
	public AddressComponentEnum getAddressComponentEnum();
	
	public String getAddressComponentValue();
	
	public ComponentAddress getParentComponentAddress();
	
	public void setParentComponentAddress(ComponentAddress parentCompositeAddress);
	
	public List<ComponentAddress> getChildAddressComponents();
	
	public String getCompleteAddress();
	
	public String getAddress();
	
	public int getComponentAddressCount(AddressComponentEnum addressComponentEnum);
	
	public List<ComponentAddress> getComponentAddress(AddressComponentEnum addressComponentEnum);
	
	
}
