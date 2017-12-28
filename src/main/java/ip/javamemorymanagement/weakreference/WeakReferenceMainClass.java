package ip.javamemorymanagement.weakreference;

import java.lang.ref.WeakReference;

public class WeakReferenceMainClass {

	public static void main(String[] args) {
		ObjectClass obj1 = new ObjectClass(10, "Ten");
		WeakReference<ObjectClass> weakRef = new WeakReference<ObjectClass>(obj1);
		System.gc();
		System.out.println("Before dereference : "+weakRef.get());
		obj1 = null;
		System.gc();
		System.out.println("After dereference : "+weakRef.get());
	}
	
}
