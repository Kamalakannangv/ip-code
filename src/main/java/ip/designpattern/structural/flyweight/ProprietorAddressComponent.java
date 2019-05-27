
package ip.designpattern.structural.flyweight;


public class ProprietorAddressComponent implements IAddressComponent {
	
	private AddressComponentEnum propertityAddressComponnent = AddressComponentEnum.PROPRIETOR;
	private String addressComponentValue;
	private IAddressComponent parentAddressComponent;
	
	public ProprietorAddressComponent(String addressComponentValue) {
		this.addressComponentValue = addressComponentValue;
	}

	@Override
	public String getAddress() {
		StringBuilder address = new StringBuilder(propertityAddressComponnent.getValue() + ":" + addressComponentValue);
		if(parentAddressComponent != null){
			address.append("," +parentAddressComponent.getAddress());
		}
		return address.toString() ;
	}

	@Override
	public IAddressComponent getParentAddressComponent() {
		return parentAddressComponent;
	}

	@Override
	public void setParentAddressComponent(IAddressComponent parentAddressComponent) {
		this.parentAddressComponent = parentAddressComponent;
	}

	@Override
	public AddressComponentEnum getAddressComponentEnum() {
		return propertityAddressComponnent;
	}

	@Override
	public String getAddressComponentValue() {
		return addressComponentValue;
	}

}
