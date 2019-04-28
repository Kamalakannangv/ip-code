package ip.designpattern.behavioural.iterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ip.designpattern.behavioural.iterator.AddressComponentEnum;
import ip.designpattern.behavioural.iterator.ComponentAddress;
import ip.designpattern.behavioural.iterator.CompositeAddress;
import ip.designpattern.behavioural.iterator.Proprietor;

public class AddressCollection implements IterableAddress<ComponentAddress>{

	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/behavioural/iterator/addresses.csv";
	private List<ComponentAddress> componentAddressCollection = new ArrayList<>();
	private ComponentAddress rootComponentAddress = null;

	public AddressCollection(){
		loadAddressesFromCSV();
		rootComponentAddress = componentAddressCollection.get(0);
	}

	@Override
	public AddressIterator<ComponentAddress> createIterator() {
		return new AddressIterator<ComponentAddress>(rootComponentAddress);
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
								componentAddress.setParentComponentAddress(parentAddress);
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
			if(address.getAddressComponent() == addressComponent 
					&& address.getAddressComponentValue().equalsIgnoreCase(addressComponentName)
					&& address.getAddress().equalsIgnoreCase(addressPath)){
				componentAddress = address;
				break;
			}
		}
		return componentAddress;
	}
	
	private class AddressIterator<E> implements Iterator<ComponentAddress> {

		private ComponentAddress rootComponentAddress;
		private ComponentAddress currentAddress;
		
		public AddressIterator(ComponentAddress rootComponentAddress) {
			this.rootComponentAddress = rootComponentAddress;
		}
		
		@Override
		public boolean hasNext() {
			if(null == currentAddress){
				return (null != rootComponentAddress.getLeftLeafComponentAddress());
			}else if(currentAddress.hasRightSibling()){
				return true;
			}else{
				ComponentAddress parentComponentAddress = currentAddress.getParentComponentAddress();
				while(parentComponentAddress != null){
					if(parentComponentAddress.hasRightSibling() 
						&& null != parentComponentAddress.getRigthSibling().getLeftLeafComponentAddress()){
						return true;
					}
					parentComponentAddress = parentComponentAddress.getParentComponentAddress();
				}
				return false;
			}
		}

		@Override
		public ComponentAddress next() {
			if(null == currentAddress){
				currentAddress = rootComponentAddress.getLeftLeafComponentAddress();
				return currentAddress;
			}else if(currentAddress.hasRightSibling()){
				currentAddress = currentAddress.getRigthSibling();
				return currentAddress;
			}else{
				ComponentAddress parentComponentAddress = currentAddress.getParentComponentAddress();
				while(parentComponentAddress != null){
					if(parentComponentAddress.hasRightSibling()){
						currentAddress = parentComponentAddress.getRigthSibling().getLeftLeafComponentAddress();
						if(null == currentAddress){
							currentAddress = parentComponentAddress.getRigthSibling();
						}
						if(null != currentAddress){
							return currentAddress;
						}
					}
					parentComponentAddress = parentComponentAddress.getParentComponentAddress();
				}
				currentAddress = null;
			}
			return currentAddress;
		}

		@Override
		public ComponentAddress current() {
			return currentAddress;
		}
		
	}

}
