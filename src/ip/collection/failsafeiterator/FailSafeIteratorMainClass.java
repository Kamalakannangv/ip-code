package ip.collection.failsafeiterator;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeIteratorMainClass {
	
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
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
