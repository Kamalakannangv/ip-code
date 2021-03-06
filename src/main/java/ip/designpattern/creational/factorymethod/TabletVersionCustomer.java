package ip.designpattern.creational.factorymethod;

import java.util.ArrayList;
import java.util.List;

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
		firstRow.add("Customer Email ID");
		firstRow.add(":");
		firstRow.add(getEmailId());
		data.add(firstRow);
		List<Object> secondRow = new ArrayList<>();
		secondRow.add("Customer First Name");
		secondRow.add(":");
		secondRow.add(getFirstName());
		secondRow.add("Customer Last Name");
		secondRow.add(":");
		secondRow.add(getLastName());
		data.add(secondRow);
		UtilityClass.display(columnWidth, data);
		
		
		/*
		System.out.format("%-20s", "Customer ID").println(": " + getCustomerId());
		
		System.out.format("%-20s", "Customer First Name").print(": " + getFirstName());
		System.out.format("\t\t%-20s", "Customer Last Name").println(": " + getLastName());
		
		System.out.format("%-20s", "Customer Email ID").println(": " + getEmailId());*/
	}

}
