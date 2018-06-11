package ip.designpattern.creational.abstractfactory;

import org.json.simple.JSONObject;

public class TabletApplication extends DisplayApplication {

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

}
