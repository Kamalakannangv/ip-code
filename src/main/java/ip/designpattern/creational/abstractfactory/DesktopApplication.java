package ip.designpattern.creational.abstractfactory;

import org.json.simple.JSONObject;

public class DesktopApplication extends DisplayApplication {

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

}
