package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.List;

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
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(20);
		List<List<Object>> data = new ArrayList<>();
		List<Object> firstRow = new ArrayList<>();
		firstRow.add("Customer ID");
		firstRow.add(":");
		firstRow.add(getCustomerId());
		data.add(firstRow);
		List<Object> secondRow = new ArrayList<>();
		secondRow.add("Customer Short Name");
		secondRow.add(":");
		secondRow.add(getShortName());
		data.add(secondRow);
		List<Object> thirdRow = new ArrayList<>();
		thirdRow.add("Customer Mobile No.");
		thirdRow.add(":");
		thirdRow.add(getMobileNumber());
		data.add(thirdRow);
		DisplayUtil.display(columnWidth, data);
		
	}

}
