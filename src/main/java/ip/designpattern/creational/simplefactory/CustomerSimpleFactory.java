package ip.designpattern.creational.simplefactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ip.designpattern.creational.simplefactory.MobileVersionCustomer;
import ip.designpattern.creational.simplefactory.DesktopVersionCustomer;

public class CustomerSimpleFactory {
	
private static final String dataFilePath = "/META-INF/config/ip/designpattern/creational/simplefactory/customer-data.json";
	
	private static CustomerSimpleFactory instance = null;
	
	private JSONObject jsonData = null;
	
	public static CustomerSimpleFactory getInstance(){
		if(instance == null){
			instance = new CustomerSimpleFactory();
		}
		return instance;
	}
	
	private CustomerSimpleFactory(){
		getData();
	}

	private JSONObject getData(){
		JSONParser jsonParser = null;
		try {
			URL fileUrl = getClass().getResource(dataFilePath);
			FileReader fileReader = new FileReader(fileUrl.getFile());
			jsonParser = new JSONParser();
			Object dataObject = jsonParser.parse(fileReader);
			jsonData = (JSONObject)dataObject;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonData;
	}
	
	public Customer createCustomer(String type){
		long customerId = (Long)jsonData.get("customer-id");
		Customer customer = null;
		if("Desktop".equalsIgnoreCase(type)){
			DesktopVersionCustomer desktopVersionCustomer = new DesktopVersionCustomer(customerId);
			desktopVersionCustomer.setSalutation((String)jsonData.get("salutation"));
			desktopVersionCustomer.setFirstName((String)jsonData.get("first-name"));
			desktopVersionCustomer.setLastName((String)jsonData.get("last-name"));
			desktopVersionCustomer.setMobileNumber((long)jsonData.get("mobile"));
			desktopVersionCustomer.setEmailId((String)jsonData.get("email-id"));
			desktopVersionCustomer.setAddress((String)jsonData.get("address"));
			customer = desktopVersionCustomer;
		}else if("Mobile".equalsIgnoreCase(type)){
			MobileVersionCustomer mobileVersionCustomer = new MobileVersionCustomer(customerId);	
			mobileVersionCustomer.setShortName((String)jsonData.get("short-name"));
			mobileVersionCustomer.setMobileNumber((Long)jsonData.get("mobile"));
			customer = mobileVersionCustomer;
		}
		return customer;
	}

}
