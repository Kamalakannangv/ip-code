package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabletVersionPolicy extends AbstractPolicy {

	private long premiumAmount;
	private Date nextDueDate; 
	
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

	public TabletVersionPolicy(long customerId, long policyId) {
		super(customerId, policyId);
	}

	@Override
	public void printPolicy() {
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(35);
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(20);
		List<List<Object>> data = new ArrayList<>();
		List<Object> firstRow = new ArrayList<>();
		firstRow.add("Policy Id");
		firstRow.add(":");
		firstRow.add(getPolicyId());
		firstRow.add("Premium Amount");
		firstRow.add(":");
		firstRow.add(getPremiumAmount());
		data.add(firstRow);
		List<Object> secondRow = new ArrayList<>();
		secondRow.add("Premium Next Due Date");
		secondRow.add(":");
		secondRow.add(getNextDueDate());
		data.add(secondRow);
		DisplayUtil.display(columnWidth, data);

	}

}
