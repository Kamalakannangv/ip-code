package ip.designpattern.behavioural.iterator;

public class IteratorMainClass {

	public static void main(String[] args) {
		IterableAddress<ComponentAddress> addressCollection = new AddressCollection();
		Iterator<ComponentAddress> iterator = addressCollection.createIterator();
		while(iterator.hasNext()){
			ComponentAddress componentAddress = iterator.next();
			System.out.println(componentAddress.getAddress());
		}
	}
	
}
