package ip.designpattern.structural.adapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class CustomerJsonClassAdapter extends CustomerXmlService implements CustomerJsonService {

	@Override
	public JSONObject getCustomerData() {
		String xmlString = getCustomerXmlData();
		JSONObject jsonObject = null; 
		try {
			jsonObject = XML.toJSONObject(xmlString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
