package ip.designpattern.structural.composite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressCollection{

	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/structural/composite/addresses.csv";
	private List<ComponentAddress> componentAddressCollection = new ArrayList<>();
	private ComponentAddress rootComponentAddress = null;

	public AddressCollection(){
		loadAddressesFromCSV();
		rootComponentAddress = componentAddressCollection.get(0);
	}

	public ComponentAddress getRootComponentAddress() {
		return rootComponentAddress;
	}

	public void printAllState(ComponentAddress rootComponentAddress){
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.STATE);
		System.out.println("All State List:");
		System.out.println("***************");
		for(ComponentAddress address : componentAddress){
			System.out.println(address.getAddress());
		}
		System.out.println("\n");
	}
	
	public void printAllDistrict(ComponentAddress rootComponentAddress){
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.DISTRICT);
		System.out.println("All District List:");
		System.out.println("******************");
		for(ComponentAddress address : componentAddress){
			System.out.println(address.getCompleteAddress());
		}
		System.out.println("\n");
	}
	
	public void printAllStateAndDistrict(ComponentAddress rootComponentAddress){
		System.out.println("All State and District List:");
		System.out.println("****************************");
		List<ComponentAddress> componentAddress = rootComponentAddress.getComponentAddress(AddressComponentEnum.STATE);
		for(ComponentAddress address : componentAddress){
			System.out.println(address.getAddress());
			List<ComponentAddress> componentDistrictAddress = address.getComponentAddress(AddressComponentEnum.DISTRICT);
			for(ComponentAddress districtAddress : componentDistrictAddress){
				System.out.println("\t" + districtAddress.getAddress());
			}
		}
		System.out.println("\n");
	}
	
	
	private void loadAddressesFromCSV(){
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
