package ip.designpattern.structural.flyweight;

public interface IAddressComponent {
	
	public String getAddress();
	
	public IAddressComponent getParentAddressComponent();
	
	public void setParentAddressComponent(IAddressComponent parentAddressComponent);
	
	public AddressComponentEnum getAddressComponentEnum();
	
	public String getAddressComponentValue();

	/*public List<IAddressComponent> getComponentAddress(AddressComponentEnum addressComponentEnum);*/
}
