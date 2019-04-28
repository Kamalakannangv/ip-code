package ip.designpattern.structural.composite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//AddressDirectory
public class CompositeMainClass {

	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/structural/composite/addresses.csv";
	
	private List<ComponentAddress> addressDirectory = new ArrayList<>();
	private List<Proprietor> proprietorAddresses = new ArrayList<>();

	public static void main(String[] args) {
		CompositeMainClass compositeClient = new CompositeMainClass();
		compositeClient.loadAddressesFromCSV(csvAddressFilePath);
		
		//Print individual addresses
		for(Proprietor proprietorAddress : compositeClient.proprietorAddresses){
			compositeClient.printAddress(proprietorAddress);
		}
		
		//Get all address and print together
		Iterator<ComponentAddress> addressDirectoryIter = compositeClient.addressDirectory.iterator();
		while(addressDirectoryIter.hasNext()){
			ComponentAddress rootAddress = addressDirectoryIter.next();
			List<String> completeAddressList = compositeClient.getAllAddresses(rootAddress);
			for(String addressLines : completeAddressList){
				System.out.println(addressLines + "\n");
			}
		}
		System.out.println("****  End  ****");
	}
	
	private void loadAddressesFromCSV(String csvAddressFilePath){
		try (
				FileReader fileReader = new FileReader(CompositeMainClass.class.getResource(csvAddressFilePath).getFile());
				BufferedReader bf = new BufferedReader(fileReader);
				){
			String line = null;
			List<AddressComponentEnum> headers = new ArrayList<>();
			boolean headerFilled = false;	
			while((line = bf.readLine()) != null){
				if(headerFilled){
					String[] valuesArr = line.split(",");
					ComponentAddress parentAddress = null;
					for(int i = valuesArr.length-1; i >= 0; i--){
						AddressComponentEnum addressComponent = headers.get(i);
						String addressValue = valuesArr[i];
						if(null == parentAddress){
							for(ComponentAddress rootAddress : addressDirectory){
								if(rootAddress.getAddressComponent() == addressComponent && addressValue.equals(rootAddress.getAddressComponentValue())){
									parentAddress = rootAddress;
									break;
								}
							}
							if(null == parentAddress){
								parentAddress = new CompositeAddress(addressComponent, addressValue);
								addressDirectory.add(parentAddress);
							}
						}else{
							List<ComponentAddress> childAddreses = parentAddress.getChildAddressComponents();
							ComponentAddress matchedChildAddress = null;
							for(ComponentAddress childAddress : childAddreses){
								if(childAddress.getAddressComponent() == addressComponent && addressValue.equals(childAddress.getAddressComponentValue())){
									matchedChildAddress = childAddress;
									break;
								}
							}
							if(null == matchedChildAddress){
								if(i == 0){
									Proprietor proprietor = new Proprietor(addressValue);
									proprietorAddresses.add(proprietor);
									matchedChildAddress = proprietor;
								}else{
									matchedChildAddress = new CompositeAddress(addressComponent, addressValue);
								}
								matchedChildAddress.setParentComponentAddress(parentAddress);
							}
							parentAddress = matchedChildAddress;
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

	private List<String> getAllAddresses(ComponentAddress compositeAddress, List<String>... addressLinesVarArg){
		List<String> addressLines = null;
		if(addressLinesVarArg.length == 1){
			addressLines = addressLinesVarArg[0];
		}else{
			addressLines = new ArrayList<>();
		}
		List<String> completeAddresses = null;
		String addressStr = compositeAddress.getAddress();
		addressLines.add(addressStr);
		List<ComponentAddress> childAddresses = compositeAddress.getChildAddressComponents();
		if(null != childAddresses && childAddresses.size() > 0){
			completeAddresses = new ArrayList<>();
			for(ComponentAddress childAddress : childAddresses){
				List<String> childAddressLines = new ArrayList<>();
				for(String addressLine : addressLines){
					childAddressLines.add(addressLine);
				}
				List<String> completeAddress = getAllAddresses(childAddress, childAddressLines);
				completeAddresses.addAll(completeAddress);
			}
		}else{
			completeAddresses = new ArrayList<>();
			StringBuffer completeAddressLines = new StringBuffer();
			for(int i = addressLines.size() -1 ; i >= 0; i--){
				completeAddressLines.append(addressLines.get(i) + "\n");
			}
			completeAddresses.add(completeAddressLines.toString());
		}
		return completeAddresses;
	}
	
	private boolean isCompositeAddressEqual(ComponentAddress address1, ComponentAddress address2){
		if(null != address1 && null != address2){
			if(address1.equals(address2)){
				ComponentAddress parent1 = address1.getParentComponentAddress();
				ComponentAddress parent2 = address2.getParentComponentAddress();
				if(parent1 == null && parent2 == null){
					return true;
				}else{
					return isCompositeAddressEqual(parent1, parent2);
				}
			}
		}
		return false;
	}
	
	
	private void printAddress(ComponentAddress compositeAddress){
		ComponentAddress address = compositeAddress;
		while(address != null){
			System.out.println(address.getAddress());
			address = address.getParentComponentAddress();
		}
	}

}
