package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesktopVersionPolicy extends AbstractPolicy {

	public DesktopVersionPolicy(long customerId, long policyId) {
		super(customerId, policyId);
	}

	private long premiumAmount;
	private Date nextDueDate;
	private long sumInsured;
		
	public long getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public long getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(long sumInsured) {
		this.sumInsured = sumInsured;
	}

	@Override
	public void printPolicy() {
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(1);
		columnWidth.add(20);
		columnWidth.add(1);
		columnWidth.add(20);
		columnWidth.add(1);
		columnWidth.add(20);
		columnWidth.add(1);
		columnWidth.add(30);
		columnWidth.add(1);
		List<List<Object>> data = new ArrayList<>();
		List<Object> dataRow = new ArrayList<>();
		dataRow.add("|");
		dataRow.add(getPolicyId());
		dataRow.add("|");
		dataRow.add(getPremiumAmount());
		dataRow.add("|");
		dataRow.add(getSumInsured());
		dataRow.add("|");
		dataRow.add(getNextDueDate());
		dataRow.add("|");
		data.add(dataRow);
		DisplayUtil.display(columnWidth, data);
		

	}

}
