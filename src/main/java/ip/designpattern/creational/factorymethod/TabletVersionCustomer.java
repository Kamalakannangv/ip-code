package ip.designpattern.creational.factorymethod;

public class TabletVersionCustomer extends AbstractCustomer {
	
	private String firstName;
	private String lastName;
	private String emailId;

	public TabletVersionCustomer(long id) {
		super(id);
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public void printCustomer() {
		System.out.println("\nCustomer data in Tablet Application");
		System.out.println("***********************************");
		System.out.format("%-20s", "Customer ID").println(": " + getCustomerId());
		
		System.out.format("%-20s", "Customer First Name").print(": " + getFirstName());
		System.out.format("\t\t%-20s", "Customer Last Name").println(": " + getLastName());
		
		System.out.format("%-20s", "Customer Email ID").println(": " + getEmailId());
	}

}
