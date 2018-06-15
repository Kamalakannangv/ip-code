package ip.designpattern.creational.abstractfactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class DisplayApplication {
	
	private static final String dataFilePath = "/META-INF/config/ip/designpattern/creational/abstractfactory/customer-policy-data.json";
	
	public JSONObject getData(){
		JSONObject jsonObject = null;
		JSONParser jsonParser = null;
		try {
			URL fileUrl = getClass().getResource(dataFilePath);
			FileReader fileReader = new FileReader(fileUrl.getFile());
			jsonParser = new JSONParser();
			Object dataObject = jsonParser.parse(fileReader);
			jsonObject = (JSONObject)dataObject;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public abstract void displayCustomerDetail(long customerId);

	protected abstract Customer getCustomer(long customerId);
	
	protected abstract List<Policy> getPolicy(long customerId);
	
}
