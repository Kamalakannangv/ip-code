package ip.javamemorymanagement.weakreference;

public class ObjectClass {
	
	private Integer intValue;
	private String strValue;
	
	public ObjectClass(int intValue, String strValue){
		this.intValue = intValue;
		this.strValue = strValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public String getStrValue() {
		return strValue;
	}
	
	@Override
	public String toString() {
		return intValue+"/"+strValue;
	}

}
