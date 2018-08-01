package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.List;

public class MobileVersionPolicy extends AbstractPolicy {

	private long premiumAmount;
	
	public MobileVersionPolicy(long customerId, long policyId) {
		super(customerId, policyId);
	}

	public long getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	@Override
	public void printPolicy() {
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(20);
		columnWidth.add(2);
		columnWidth.add(20);
		List<List<Object>> data = new ArrayList<>();
		List<Object> firstRow = new ArrayList<>();
		firstRow.add("Policy Id");
		firstRow.add(":");
		firstRow.add(getPolicyId());
		data.add(firstRow);
		List<Object> secondRow = new ArrayList<>();
		secondRow.add("Premium Amount");
		secondRow.add(":");
		secondRow.add(getPremiumAmount());
		data.add(secondRow);
		DisplayUtil.display(columnWidth, data);

	}

}
