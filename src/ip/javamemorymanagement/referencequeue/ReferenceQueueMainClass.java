package ip.javamemorymanagement.referencequeue;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueMainClass {

	private static Object object;
	
	public static void main(String[] args) {
		dereferencingByNull();
		//dereferencingByOutOfScope();
	}
	
	public static void dereferencingByNull() {
		object = new Object();
		// Reference queue, to which registered reference objects are appended by the
		// garbage collector after the appropriate reachability changes are detected.
		ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
		// Create a new weak reference that refers to the given object and is registered with this queue.
		WeakReference<Object> wr = new WeakReference<Object>(object, rq);
		// start a new thread that will remove all references of object
		new Thread(runnable).start();
		// wait for all the references to the object to be removed
		try {
			while (true) {
				Reference<?> r = rq.remove();
				if (r == wr) {
					System.out.println("Object is no longer referenced.");
				}
				break;
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				System.out.println("Setting object to null");
				object = null;
				System.out.println("Running Garbage Collection...");
				Runtime.getRuntime().gc(); // run GC to collect the object
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public static void dereferencingByOutOfScope() {
		ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
		addReference(rq);
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		try {
			Reference<?> r = rq.remove();
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void addReference(ReferenceQueue<Object> rq){
		Object localObject = new Object();
		System.out.println(localObject);
		WeakReference<Object> wr = new WeakReference<Object>(localObject, rq);
	}

	
}
