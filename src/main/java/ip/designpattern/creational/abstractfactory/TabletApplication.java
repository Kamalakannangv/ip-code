package ip.designpattern.creational.abstractfactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TabletApplication extends DisplayApplication {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected Customer getCustomer(long customerId) {
		JSONObject jsonData = getData();
		long custId = (Long)jsonData.get("customer-id");
		TabletVersionCustomer tabletVersionCustomer = null;
		if(custId == customerId){
			tabletVersionCustomer = new TabletVersionCustomer(customerId);	
			tabletVersionCustomer.setFirstName((String)jsonData.get("first-name"));
			tabletVersionCustomer.setLastName((String)jsonData.get("last-name"));
			tabletVersionCustomer.setEmailId((String)jsonData.get("email-id"));
		}
		return tabletVersionCustomer;
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
				TabletVersionPolicy policy = new TabletVersionPolicy(customerId, policyId);
				policy.setPremiumAmount((long)policyJson.get("premium"));
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
		System.out.println("************************");
		System.out.println("Customer Detail");
		System.out.println("************************");
		System.out.println("----------------------------------------------------------------------------------------------");
		Customer customer = getCustomer(customerId);
		customer.printCustomer();
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("************************");
		System.out.println("Policy Detail");
		System.out.println("************************");
		List<Policy> policies = getPolicy(customerId);
		System.out.println("----------------------------------------------------------------------------------------------");
		for(Policy policy : policies){
			policy.printPolicy();
			System.out.println("----------------------------------------------------------------------------------------------");
		}
	}

}
