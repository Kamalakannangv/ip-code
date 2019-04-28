package ip.designpattern.behavioural.iterator;

import java.util.List;

public interface ComponentAddress {
	
	public AddressComponentEnum getAddressComponent();
	
	public String getAddressComponentValue();
	
	public ComponentAddress getParentComponentAddress();
	
	public void setParentComponentAddress(ComponentAddress parentCompositeAddress);
	
	public List<ComponentAddress> getChildAddressComponents();
	
	public String getAddress();
	
	public ComponentAddress getLeftLeafComponentAddress();
	
	public boolean hasRightSibling();
	
	public ComponentAddress getRigthSibling();
	
}
