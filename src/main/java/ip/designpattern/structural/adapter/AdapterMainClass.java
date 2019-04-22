package ip.designpattern.structural.adapter;

import org.json.JSONException;
import org.json.JSONObject;

public class AdapterMainClass {
	
	public static void main(String[] args) {
		System.out.println("Object Adapter:");
		CustomerJsonService customerJsonService = new CustomerJsonObjectAdapter();
		printCustomerJsonString(customerJsonService);
		
		System.out.println("\n\nClass Adapter:");
		customerJsonService = new CustomerJsonClassAdapter();
		printCustomerJsonString(customerJsonService);
	}
	
	
	private static void printCustomerJsonString(CustomerJsonService customerJsonService){
		JSONObject customerJsonData = customerJsonService.getCustomerData();
		try {
			String customerJsonString = customerJsonData.toString(4);
			System.out.println(customerJsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
