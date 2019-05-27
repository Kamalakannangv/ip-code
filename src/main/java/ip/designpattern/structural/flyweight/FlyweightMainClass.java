package ip.designpattern.structural.flyweight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FlyweightMainClass {
	
	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/structural/flyweight/addresses.csv";
	private AddressComponentFactory addressComponentFactory = new AddressComponentFactory();
	private List<IAddressComponent> propritorAddresses;
	
	public static void main(String[] args) {
		FlyweightMainClass client = new FlyweightMainClass();
		client.loadAddressFromCsv();
		System.out.println("Total address Components:" + client.addressComponentFactory.getComponentAddressCollection().size());
		client.displayAddress();
		IAddressComponent addComp = client.propritorAddresses.get(0);
		if(addComp instanceof ProprietorAddressComponent){
			ProprietorAddressComponent propritorAddress = (ProprietorAddressComponent)addComp;
			IAddressComponent addressComponent = propritorAddress.getParentAddressComponent();
			while(addressComponent.getParentAddressComponent() != null){
				addressComponent = addressComponent.getParentAddressComponent();
			}
			if(addressComponent instanceof AddressComponent){
				AddressComponent rootAddressComponent = (AddressComponent)addressComponent;
				client.printAllState(rootAddressComponent);
				client.printAllDistrict(rootAddressComponent);
				client.printAllStateAndDistrict(rootAddressComponent);
			}
		}
		
	}
	
	private void loadAddressFromCsv(){
		propritorAddresses = new ArrayList<>();
		try (
				FileReader fileReader = new FileReader(this.getClass().getResource(csvAddressFilePath).getFile());
				BufferedReader bf = new BufferedReader(fileReader);
				){
			String line = null;
			List<AddressComponentEnum> headers = new ArrayList<>();
			boolean headerFilled = false;	
			while((line = bf.readLine()) != null){
				if(headerFilled){
					String[] valuesArr = line.split(",");
					StringBuffer addressPath = new StringBuffer();
					ProprietorAddressComponent proprietorAddressComponent = null;
					for(int i = 0; i < valuesArr.length; i++){
						AddressComponentEnum addressComponent = headers.get(i);
						String addressValue = valuesArr[i].trim();
						if(i == 0){
							proprietorAddressComponent = new ProprietorAddressComponent(addressValue);
							propritorAddresses.add(proprietorAddressComponent);
						}else{
							if(i == valuesArr.length - 1){
								addressPath.append(addressComponent.getValue() + ":" + addressValue);
							}else{
								addressPath.append(addressComponent.getValue() + ":" + addressValue + ",");
							}
						}
					}
					IAddressComponent flyWeightAddressComponent = addressComponentFactory.createAddress(addressPath.toString());
					((AddressComponent)flyWeightAddressComponent).setChildAddressComponent(proprietorAddressComponent);
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
	
	private void displayAddress(){
		System.out.println("\nAll addresses:\n*******************");
		for(IAddressComponent proprietorAddressComponent : propritorAddresses){
			System.out.println(proprietorAddressComponent.getAddress());
		}
	}
	
	public void printAllState(AddressComponent rootAddressComponent){
		List<AddressComponent> componentAddress = rootAddressComponent.getComponentAddress(AddressComponentEnum.STATE);
		System.out.println("All State List:");
		System.out.println("***************");
		for(AddressComponent address : componentAddress){
			System.out.println(address.getAddress());
		}
		System.out.println("\n");
	}
	
	public void printAllDistrict(AddressComponent rootAddressComponent){
		List<AddressComponent> componentAddress = rootAddressComponent.getComponentAddress(AddressComponentEnum.DISTRICT);
		System.out.println("All District List:");
		System.out.println("******************");
		for(AddressComponent address : componentAddress){
			System.out.println(address.getAddress());
		}
		System.out.println("\n");
	}
	
	public void printAllStateAndDistrict(AddressComponent rootAddressComponent){
		System.out.println("All State and District List:");
		System.out.println("****************************");
		List<AddressComponent> componentAddress = rootAddressComponent.getComponentAddress(AddressComponentEnum.STATE);
		for(AddressComponent address : componentAddress){
			System.out.println(address.getAddress());
			List<AddressComponent> componentDistrictAddress = address.getComponentAddress(AddressComponentEnum.DISTRICT);
			for(AddressComponent districtAddress : componentDistrictAddress){
				System.out.println("\t" + districtAddress.getAddress());
			}
		}
		System.out.println("\n");
	}
	
	

}
