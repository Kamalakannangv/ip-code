package ip.collection.unsupportedoperationexception;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnsupportedOperationExceptionMainClass {
	
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
			iterator.remove(); // Unsupported operation in Fail-Safe Iterator
		}
	}
}
