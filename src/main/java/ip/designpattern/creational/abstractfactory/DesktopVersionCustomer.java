package ip.designpattern.creational.abstractfactory;

public class DesktopVersionCustomer extends AbstractCustomer {

	private String salutation;
	private String firstName;
	private String lastName;
	private long mobileNumber;
	private String emailId;
	private String address;

	public DesktopVersionCustomer(long id) {
		super(id);
	}
	
	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void printCustomer() {
		System.out.println("\nCustomer data in Desktop Application");
		System.out.println("************************************");
		System.out.format("%-20s", "Customer ID").println(": " + getCustomerId());
		System.out.format("%-20s", "Customer Name").println(": " + getSalutation() + " " + getFirstName() + " " + getLastName());
		System.out.format("%-20s", "Customer Mobile No.").println(": " + getMobileNumber());
		System.out.format("%-20s", "Customer Email ID").println(": " + getEmailId());
		System.out.format("%-20s", "Customer Address").println(": " + getAddress());
	}

}
