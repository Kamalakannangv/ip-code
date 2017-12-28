package ip.corejava.xxx;

import java.util.ArrayList;

public class CodeExample1 {
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(3);
		list.add(5);
		list.add(2);
		list.add(8);
		System.out.println("Initial List :\n" + list);
		int intPrimitive = new Integer(3);
		list.remove(intPrimitive);
		System.out.println("\nList after removing using int primitive:\n" + list);
		
		Integer integerObject = new Integer(3);
		list.remove(integerObject);
		System.out.println("\nList after removing using Integer object:\n" + list);
	}

}
