package ip.collection.failfastiterator;

import java.util.ArrayList;
import java.util.Iterator;

public class FailFastIteratorMainClass {
	
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
			//iterator.remove();
			list.remove(0);
		}
	}

}
