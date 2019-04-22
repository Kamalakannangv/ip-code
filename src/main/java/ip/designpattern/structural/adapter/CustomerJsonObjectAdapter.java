package ip.designpattern.structural.adapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class CustomerJsonObjectAdapter implements CustomerJsonService {

	private CustomerXmlService customerXmlService;
	
	public CustomerJsonObjectAdapter() {
		customerXmlService = new CustomerXmlService();
	}
	
	@Override
	public JSONObject getCustomerData() {
		String xmlString = customerXmlService.getCustomerXmlData();
		JSONObject jsonObject = null; 
		try {
			jsonObject = XML.toJSONObject(xmlString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
