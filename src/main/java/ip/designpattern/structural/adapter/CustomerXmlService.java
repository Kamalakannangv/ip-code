package ip.designpattern.structural.adapter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class CustomerXmlService {
	
	private static final String dataFilePath = "/META-INF/config/ip/designpattern/structural/adapter/customer-policy-data.json";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private String customerXmlString;
	
	public CustomerXmlService() {
		customerXmlString = loadData();
	}
	
	public String getCustomerXmlData(){
		return customerXmlString;
	}
	
	private String loadData(){
		String xmlString = null;
		JSONObject jsonData = null;
		JSONParser jsonParser = null;
		try {
			URL fileUrl = CustomerXmlService.class.getResource(dataFilePath);
			FileReader fileReader = new FileReader(fileUrl.getFile());
			jsonParser = new JSONParser();
			Object dataObject = jsonParser.parse(fileReader);
			jsonData = (JSONObject)dataObject;
			
			Customer customer = new Customer((Long)jsonData.get("customer-id"));	
			customer.setSalutation((String)jsonData.get("salutation"));
			customer.setFirstName((String)jsonData.get("first-name"));
			customer.setLastName((String)jsonData.get("last-name"));
			customer.setMobileNumber((long)jsonData.get("mobile"));
			customer.setEmailId((String)jsonData.get("email-id"));
			customer.setAddress((String)jsonData.get("address"));
			
			Object obj = jsonData.get("policy");
			List<Policy> policyList = new ArrayList<>();
			if(obj instanceof JSONArray){
				JSONArray policyArray = (JSONArray)obj;
				for(int policyIndex = 0; policyIndex < policyArray.size(); policyIndex++){
					Object policyJsonObj = policyArray.get(policyIndex);
					JSONObject policyJson = (JSONObject)policyJsonObj;
					long policyId = (Long)policyJson.get("policy-id");
					Policy policy = new Policy(policyId);
					policy.setPremiumAmount((long)policyJson.get("premium"));
					policy.setSumInsured((long)policyJson.get("sum-insured"));
					String dateStr = (String)policyJson.get("next-due");
					Date dueDate = dateFormat.parse(dateStr);
					policy.setNextDueDate(dueDate);
					policyList.add(policy);
				}
			}
			customer.setPolicies(policyList);
			
			JAXBContext context = JAXBContext.newInstance(Customer.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(customer, stringWriter);
			xmlString = stringWriter.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return xmlString;
	}
	
}
