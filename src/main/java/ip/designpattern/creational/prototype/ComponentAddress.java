package ip.designpattern.creational.prototype;

import java.io.Serializable;
import java.util.List;

public interface ComponentAddress extends Serializable{
	
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
