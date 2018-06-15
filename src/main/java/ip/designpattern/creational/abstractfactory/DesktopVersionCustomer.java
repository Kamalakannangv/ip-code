package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.List;

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
		
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(30);
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(20);
		
		List<List<Object>> data = new ArrayList<>();
		List<Object> firstRow = new ArrayList<>();
		firstRow.add("Customer ID");
		firstRow.add(":");
		firstRow.add(getCustomerId());
		firstRow.add("Customer Name");
		firstRow.add(":");
		firstRow.add(getSalutation() + " " + getFirstName() + " " + getLastName());
		data.add(firstRow);
		
		List<Object> secondRow = new ArrayList<>();
		secondRow.add("Customer Mobile No.");
		secondRow.add(":");
		secondRow.add(getMobileNumber());
		secondRow.add("Customer Email ID");
		secondRow.add(":");
		secondRow.add(getEmailId());
		data.add(secondRow);
		
		List<Object> thirdRow = new ArrayList<>();
		thirdRow.add("Customer Address");
		thirdRow.add(":");
		thirdRow.add(getAddress());
		data.add(thirdRow);
		UtilityClass.display(columnWidth, data);
	}
}
