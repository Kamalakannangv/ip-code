package ip.designpattern.creational.builder;

public class Car {
	
	private String makeModel;
	private int regYear;
	private String regNo;
	private String engineNo;
	
	public Car(String makeModel, int regYear, String regNo, String engineNo) {
		this.makeModel = makeModel;
		this.regYear = regYear;
		this.regNo = regNo;
		this.engineNo = engineNo;
	}

	public String getMakeModel() {
		return makeModel;
	}

	public int getRegYear() {
		return regYear;
	}

	public String getRegNo() {
		return regNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	@Override
	public String toString() {
		return "Car [makeModel=" + makeModel + ", regYear=" + regYear + ", regNo=" + regNo + ", engineNo=" + engineNo
				+ "]";
	}
	
}
