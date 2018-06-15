package ip.designpattern.creational.abstractfactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DesktopApplication extends DisplayApplication {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected Customer getCustomer(long customerId) {
		JSONObject jsonData = getData();
		long custId = (Long)jsonData.get("customer-id");
		DesktopVersionCustomer desktopVersionCustomer = null;
		if(custId == customerId){
			desktopVersionCustomer = new DesktopVersionCustomer(customerId);	
			desktopVersionCustomer.setSalutation((String)jsonData.get("salutation"));
			desktopVersionCustomer.setFirstName((String)jsonData.get("first-name"));
			desktopVersionCustomer.setLastName((String)jsonData.get("last-name"));
			desktopVersionCustomer.setMobileNumber((long)jsonData.get("mobile"));
			desktopVersionCustomer.setEmailId((String)jsonData.get("email-id"));
			desktopVersionCustomer.setAddress((String)jsonData.get("address"));
		}
		return desktopVersionCustomer;
	}

	@Override
	protected List<Policy> getPolicy(long customerId) {
		
		JSONObject jsonData = getData();
		Object obj = jsonData.get("policy");
		List<Policy> policyList = new ArrayList<>();
		if(obj instanceof JSONArray){
			JSONArray policyArray = (JSONArray)obj;
			for(int policyIndex = 0; policyIndex < policyArray.size(); policyIndex++){
				Object policyJsonObj = policyArray.get(policyIndex);
				JSONObject policyJson = (JSONObject)policyJsonObj;
				long policyId = (Long)policyJson.get("policy-id");
				DesktopVersionPolicy policy = new DesktopVersionPolicy(customerId, policyId);
				policy.setPremiumAmount((long)policyJson.get("premium"));
				policy.setSumInsured((long)policyJson.get("sum-insured"));
				String dateStr = (String)policyJson.get("next-due");
				Date dueDate = null;
				try {
					dueDate = dateFormat.parse(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				policy.setNextDueDate(dueDate);
				policyList.add(policy);
			}
		}
		return policyList;
	}

	@Override
	public void displayCustomerDetail(long customerId) {
		System.out.println("Customer Detail:");
		System.out.println("****************");
		Customer customer = getCustomer(customerId);
		customer.printCustomer();
		System.out.println();
		System.out.println("Policy Detail:");
		System.out.println("**************");
		List<Policy> policies = getPolicy(customerId);
		System.out.println("----------------------------------------------------------------------------------------------");
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
		List<Object> zeroHeaderRow = new ArrayList<>();
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		data.add(zeroHeaderRow);
		List<Object> headerRow = new ArrayList<>();
		headerRow.add("|");
		headerRow.add("Policy Id");
		headerRow.add("|");
		headerRow.add("Premium Amount");
		headerRow.add("|");
		headerRow.add("Sum Insured");
		headerRow.add("|");
		headerRow.add("Next Due Date");
		headerRow.add("|");
		data.add(headerRow);
		List<Object> secondHeaderRow = new ArrayList<>();
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		data.add(secondHeaderRow);
		UtilityClass.display(columnWidth, data);
		
		System.out.println("|--------------------|--------------------|--------------------|------------------------------|");
		for(Policy policy : policies){
			policy.printPolicy();
			System.out.println("|--------------------|--------------------|--------------------|------------------------------|");
		}
	}

}
