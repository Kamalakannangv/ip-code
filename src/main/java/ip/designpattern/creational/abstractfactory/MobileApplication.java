package ip.designpattern.creational.abstractfactory;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MobileApplication extends DisplayApplication {
	
	@Override
	protected Customer getCustomer(long customerId) {
		JSONObject jsonData = getData();
		long custId = (Long)jsonData.get("customer-id");
		MobileVersionCustomer mobileVersionCustomer = null;
		if(custId == customerId){
			mobileVersionCustomer = new MobileVersionCustomer(customerId);	
			mobileVersionCustomer.setShortName((String)jsonData.get("short-name"));
			mobileVersionCustomer.setMobileNumber((Long)jsonData.get("mobile"));
		}
		return mobileVersionCustomer;
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
				MobileVersionPolicy policy = new MobileVersionPolicy(customerId, policyId);
				policy.setPremiumAmount((long)policyJson.get("premium"));
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
		Customer customer = getCustomer(customerId);
		customer.printCustomer();
		System.out.println();
		System.out.println("************************");
		System.out.println("Policy Detail");
		System.out.println("************************");
		List<Policy> policies = getPolicy(customerId);
		System.out.println("------------------------------");
		for(Policy policy : policies){
			policy.printPolicy();
			System.out.println("------------------------------");
		}
		
	}

}
