package ip.designpattern.creational.abstractfactory;

public class MobileVersionCustomer extends AbstractCustomer {

	private String shortName;
	private long mobileNumber;
	
	public MobileVersionCustomer(long id) {
		super(id);
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public void printCustomer() {
		System.out.println("\nCustomer data in Mobile Application");
		System.out.println("***********************************");
		System.out.format("%-20s", "Customer ID").println(": " + getCustomerId());
		System.out.format("%-20s", "Customer Short Name").println(": " + getShortName());
		System.out.format("%-20s", "Customer Mobile No.").println(": " + getMobileNumber());
	}

}
