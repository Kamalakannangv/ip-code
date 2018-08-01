package ip.designpattern.structural.composite;

public enum AddressComponent {
	
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
	
	private AddressComponent(String componentName){
		this.componentName = componentName;
	}
	
	public String getValue(){
		return componentName;
	}

}
