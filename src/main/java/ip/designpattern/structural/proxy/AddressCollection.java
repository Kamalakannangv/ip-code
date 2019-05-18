package ip.designpattern.structural.proxy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressCollection implements AddressDirectory{

	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/structural/proxy/addresses.csv";
	private List<ComponentAddress> componentAddressCollection = new ArrayList<>();
	private ComponentAddress rootComponentAddress = null;

	public AddressCollection(){
		loadAddressesFromCSV();
		rootComponentAddress = componentAddressCollection.get(0);
	}

	public ComponentAddress getRootComponentAddress() {
		return rootComponentAddress;
	}

	@Override
	public List<String> getAllState(){
		System.out.println("Retrieving all state names...");
		List<String> stateList = new ArrayList<>();
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.STATE);
		for(ComponentAddress address : componentAddress){
			stateList.add(address.getAddress());
		}
		System.out.println("All state names retrieved");
		return stateList;
	}
	
	@Override
	public List<String> getAllDistrict(){
		System.out.println("Retrieving all district names...");
		List<String> districtList = new ArrayList<>();
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.DISTRICT);
		for(ComponentAddress address : componentAddress){
			districtList.add(address.getCompleteAddress());
		}
		System.out.println("All district names retrieved");
		return districtList;
	}
	
	@Override
	public Map<String, List<String>> getAllStateAndDistrict(){
		System.out.println("Retrieving all state and district names...");
		Map<String, List<String>> stateDistrictList = new HashMap<>();
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.STATE);
		for(ComponentAddress address : componentAddress){
			List<String> districtList = new ArrayList<>();
			List<ComponentAddress> componentDistrictAddress = address.getComponentAddress(AddressComponentEnum.DISTRICT);
			for(ComponentAddress districtAddress : componentDistrictAddress){
				districtList.add(districtAddress.getAddress());
			}
			stateDistrictList.put(address.getAddress(), districtList);
		}
		System.out.println("All state and district names retrieved");
		return stateDistrictList;
	}
	
	
	private void loadAddressesFromCSV(){
		System.out.println("Loading Address....");
		try (
				FileReader fileReader = new FileReader(AddressCollection.class.getResource(csvAddressFilePath).getFile());
				BufferedReader bf = new BufferedReader(fileReader);
				){
			String line = null;
			List<AddressComponentEnum> headers = new ArrayList<>();
			boolean headerFilled = false;	
			while((line = bf.readLine()) != null){
				if(headerFilled){
					String[] valuesArr = line.split(",");
					ComponentAddress parentAddress = null;
					StringBuffer addressPath = new StringBuffer();
					for(int i = valuesArr.length-1; i >= 0; i--){
						AddressComponentEnum addressComponent = headers.get(i);
						String addressValue = valuesArr[i].trim();
						if(i == valuesArr.length - 1){
							addressPath.append(addressComponent.getValue() + ": " + addressValue);
						}else{
							addressPath.insert(0, addressComponent.getValue() + ": " + addressValue + ", ");
						}
						ComponentAddress matchingComponentAddress = getMatchingComponentAddress(addressComponent, addressValue, addressPath.toString());
						if(null == matchingComponentAddress){
							ComponentAddress componentAddress = null;
							if(addressComponent == AddressComponentEnum.PROPRIETOR){
								componentAddress = new Proprietor(addressValue);
							}else{
								componentAddress = new CompositeAddress(addressComponent, addressValue);
							}
							componentAddressCollection.add(componentAddress);
							if(parentAddress != null){
								((CompositeAddress)parentAddress).addComponentAddress(componentAddress);
							}
							parentAddress = componentAddress;
						}else{
							parentAddress = matchingComponentAddress;
						}
					}
				}else{
					String[] headersArr = line.split(",");
					for(String hrd : headersArr){
						headers.add(AddressComponentEnum.valueOf(hrd));
					}
					headerFilled = true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Address loaded");
	}
	
	private ComponentAddress getMatchingComponentAddress(AddressComponentEnum addressComponent, String addressComponentName, String addressPath){
		ComponentAddress componentAddress = null;
		for(ComponentAddress address : componentAddressCollection){
			if(address.getAddressComponentEnum() == addressComponent 
					&& address.getAddressComponentValue().equalsIgnoreCase(addressComponentName)
					&& address.getCompleteAddress().equalsIgnoreCase(addressPath)){
				componentAddress = address;
				break;
			}
		}
		return componentAddress;
	}
	
}
