package ip.designpattern.creational.prototype;

import java.io.Serializable;

public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String proprietor;
	private String doorNo;
	private String street;
	private String village;
	private String post;
	private String taluk;
	private String district;
	private String state;
	private String country;
	
	public Address(String proprietor, String doorNo, String street, String village, String post, String taluk,
			String district, String state, String country) {
		super();
		this.proprietor = proprietor;
		this.doorNo = doorNo;
		this.street = street;
		this.village = village;
		this.post = post;
		this.taluk = taluk;
		this.district = district;
		this.state = state;
		this.country = country;
	}
	
	public String getAddress() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(AddressComponentEnum.PROPRIETOR.getValue() + ": " +  proprietor + ", ");
		stringBuilder.append(AddressComponentEnum.DOORNO.getValue() + ": " +  doorNo + ", ");
		stringBuilder.append(AddressComponentEnum.STREET.getValue() + ": " +  street + ", ");
		stringBuilder.append(AddressComponentEnum.VILLAGE.getValue() + ": " +  village + ", ");
		stringBuilder.append(AddressComponentEnum.POST.getValue() + ": " +  post + ", ");
		stringBuilder.append(AddressComponentEnum.TALUK.getValue() + ": " +  taluk + ", ");
		stringBuilder.append(AddressComponentEnum.DISTRICT.getValue() + ": " +  district + ", ");
		stringBuilder.append(AddressComponentEnum.STATE.getValue() + ": " +  state + ", ");
		stringBuilder.append(AddressComponentEnum.COUNTRY.getValue() + ": " +  country);
		return stringBuilder.toString();
	}

}
