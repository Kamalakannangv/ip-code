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
	
	private List<CompositeAddress> addressDirectory = new ArrayList<>();
	private List<Proprietor> proprietorAddresses = new ArrayList<>();

	public static void main(String[] args) {
		CompositeMainClass compositeClient = new CompositeMainClass();
		compositeClient.loadAddressesFromCSV(csvAddressFilePath);
		
		//Print individual addresses
		for(Proprietor proprietorAddress : compositeClient.proprietorAddresses){
			compositeClient.printAddress(proprietorAddress);
		}
		
		//Get all address and print together
		Iterator<CompositeAddress> addressDirectoryIter = compositeClient.addressDirectory.iterator();
		while(addressDirectoryIter.hasNext()){
			CompositeAddress rootAddress = addressDirectoryIter.next();
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
			List<AddressComponent> headers = new ArrayList<>();
			boolean headerFilled = false;	
			while((line = bf.readLine()) != null){
				if(headerFilled){
					String[] valuesArr = line.split(",");
					CompositeAddress parentAddress = null;
					for(int i = valuesArr.length-1; i >= 0; i--){
						AddressComponent addressComponent = headers.get(i);
						String addressValue = valuesArr[i];
						if(null == parentAddress){
							for(CompositeAddress rootAddress : addressDirectory){
								if(rootAddress.getAddressComponent() == addressComponent && addressValue.equals(rootAddress.getAddressComponentValue())){
									parentAddress = rootAddress;
									break;
								}
							}
							if(null == parentAddress){
								parentAddress = new ComponentAddress(addressComponent, addressValue);
								addressDirectory.add(parentAddress);
							}
						}else{
							List<CompositeAddress> childAddreses = parentAddress.getChildAddressComponents();
							CompositeAddress matchedChildAddress = null;
							for(CompositeAddress childAddress : childAddreses){
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
									matchedChildAddress = new ComponentAddress(addressComponent, addressValue);
								}
								matchedChildAddress.setParentCompositeAddress(parentAddress);
							}
							parentAddress = matchedChildAddress;
						}
					}
				}else{
					String[] headersArr = line.split(",");
					for(String hrd : headersArr){
						headers.add(AddressComponent.valueOf(hrd));
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

	private List<String> getAllAddresses(CompositeAddress compositeAddress, List<String>... addressLinesVarArg){
		List<String> addressLines = null;
		if(addressLinesVarArg.length == 1){
			addressLines = addressLinesVarArg[0];
		}else{
			addressLines = new ArrayList<>();
		}
		List<String> completeAddresses = null;
		String addressStr = compositeAddress.getAddress();
		addressLines.add(addressStr);
		List<CompositeAddress> childAddresses = compositeAddress.getChildAddressComponents();
		if(null != childAddresses && childAddresses.size() > 0){
			completeAddresses = new ArrayList<>();
			for(CompositeAddress childAddress : childAddresses){
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
	
	private boolean isCompositeAddressEqual(CompositeAddress address1, CompositeAddress address2){
		if(null != address1 && null != address2){
			if(address1.equals(address2)){
				CompositeAddress parent1 = address1.getParentCompositeAddress();
				CompositeAddress parent2 = address2.getParentCompositeAddress();
				if(parent1 == null && parent2 == null){
					return true;
				}else{
					return isCompositeAddressEqual(parent1, parent2);
				}
			}
		}
		return false;
	}
	
	
	private void printAddress(CompositeAddress compositeAddress){
		CompositeAddress address = compositeAddress;
		while(address != null){
			System.out.println(address.getAddress());
			address = address.getParentCompositeAddress();
		}
	}

}
