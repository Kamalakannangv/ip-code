package ip.javamemorymanagement.weakhashmap;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapMainClass {
	
	public static void main(String[] args) {
		Map<KeyObjectClass, String> map = getMap();
		KeyObjectClass obj1 = new KeyObjectClass(10, "Ten");
		KeyObjectClass obj2 = new KeyObjectClass(20, "Twenty");
		map.put(obj1, "Ten Value");
		map.put(obj2, "Twenty Value");
		System.out.println("Before derefencing..");
		iterateMap(map);
		obj1 = null;
		System.gc();// GC should happen here to see the difference
		System.out.println("\nAfter derefencing..");
		iterateMap(map);
	}
	
	private static void iterateMap(Map<KeyObjectClass, String> map){
		Iterator<KeyObjectClass> keyObjIter = map.keySet().iterator();
		while(keyObjIter.hasNext()){
			KeyObjectClass key = keyObjIter.next();
			String value = map.get(key);
			System.out.println("Key="+key.getIntValue()+"/"+key.getStrValue()+"; Value="+value);
			
		}
	}
	
	private static Map<KeyObjectClass, String> getMap(){
		WeakHashMap<KeyObjectClass, String> map = new WeakHashMap<>();
		//HashMap<KeyObject, String> map = new HashMap<>();
		return map;
	}

}
