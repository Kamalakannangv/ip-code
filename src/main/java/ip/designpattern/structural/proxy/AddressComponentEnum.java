package ip.designpattern.structural.proxy;

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
	
	private AddressComponentEnum(String componentName){
		this.componentName = componentName;
	}
	
	public String getValue(){
		return componentName;
	}

}
