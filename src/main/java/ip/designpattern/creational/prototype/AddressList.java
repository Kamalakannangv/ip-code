package ip.designpattern.creational.prototype;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressList implements AddressPrototype, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static final String csvAddressFilePath = "/META-INF/config/ip/designpattern/creational/prototype/addresses.csv";
	private List<Address> addresses = new ArrayList<>();
	
	public AddressList() {
		loadAddressesFromCSV();
	}
	
	public void printAllAddress(){
		for(Address address : addresses){
			System.out.println(address.getAddress());
		}
	}
	
	@Override
	public AddressPrototype clone() throws CloneNotSupportedException {
		AddressList clonedObject = null;
		try(
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"));
				){
			out.writeObject(this);
			out.close();
			clonedObject = (AddressList)in.readObject();
			in.close();
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return clonedObject;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}

	private void loadAddressesFromCSV(){
		try (
				FileReader fileReader = new FileReader(AddressCollection.class.getResource(csvAddressFilePath).getFile());
				BufferedReader bf = new BufferedReader(fileReader);
				){
			String line = null;
			boolean headerFilled = false;	
			while((line = bf.readLine()) != null){
				if(headerFilled){
					String[] valuesArr = line.split(",");
					Address address = new Address(valuesArr[0], valuesArr[1], valuesArr[2], valuesArr[3], 
							valuesArr[4], valuesArr[5], valuesArr[6], valuesArr[7], valuesArr[8]);
					addresses.add(address);
				}else{
					headerFilled = true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
