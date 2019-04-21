package ip.designpattern.creational.builder;

public class InsuredObject implements Insured {

	private long sumInsured;
	private int term = 1;
	private Car car;
	private House house;
	
	@Override
	public void setSumInsured(long sumInsuredAmount) {
		this.sumInsured = sumInsuredAmount;
	}

	@Override
	public void setTerm(int noOfYears) {
	}

	@Override
	public long calculatePremium() {
		if(sumInsured > 0 && term > 0){
			return sumInsured/term;
		}
		return 0;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	@Override
	public String toString() {
		return "InsuredObject [sumInsured=" + sumInsured + ", term=" + term + ", car=" + car + ", house=" + house + "]";
	}

	
}
