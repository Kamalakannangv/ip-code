package ip.designpattern.creational.builder;

public class House {

	private int area;
	private int constructedYear;
	private String address;
	
	public House(int area, int constructedYear, String address) {
		this.area = area;
		this.constructedYear = constructedYear;
		this.address = address;
	}

	public int getArea() {
		return area;
	}

	public int getConstructedYear() {
		return constructedYear;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "House [area=" + area + ", constructedYear=" + constructedYear + ", address=" + address + "]";
	}

}
