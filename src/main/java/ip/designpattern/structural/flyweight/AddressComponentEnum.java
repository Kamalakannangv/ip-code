package ip.designpattern.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public enum AddressComponentEnum {
	
	PROPRIETOR("Mr/Mrs. "),
	DOORNO("No. "),
	STREET("Street"),
	VILLAGE("Village"),
	POST("Post"),
	TALUK("Taluk"),
	DISTRICT("District"),
	STATE("State"),
	COUNTRY("Country");
	
	private String componentName;
	
	private static final Map<String, AddressComponentEnum> addressComponentEnumMap = new HashMap<>();
	
	static{
		for(AddressComponentEnum addressEnum : values()){
			addressComponentEnumMap.put(addressEnum.componentName, addressEnum);
		}
	}
	
	public static AddressComponentEnum getAddressComponentEnum(String addressComponentStr){
		return addressComponentEnumMap.get(addressComponentStr);
	}
	
	private AddressComponentEnum(String componentName){
		this.componentName = componentName;
	}
	
	public String getValue(){
		return componentName;
	}
	
}
