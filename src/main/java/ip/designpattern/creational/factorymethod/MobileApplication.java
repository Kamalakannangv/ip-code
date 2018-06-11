package ip.designpattern.creational.factorymethod;

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

}
