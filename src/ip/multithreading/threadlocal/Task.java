package ip.multithreading.threadlocal;

public class Task implements Runnable {

	public String nonThreadLocalObject;
	public static ThreadLocal<String> threadLocalObject;
	
	public Task() {
		nonThreadLocalObject = Thread.currentThread().getName();
		threadLocalObject = new ThreadLocal<>(); // Main Thread's ThreadLocal object
		threadLocalObject.set(Thread.currentThread().getName()); 
		System.out.println("In thread "+ Thread.currentThread().getName()+", values are => nonThreadLocalObject="+nonThreadLocalObject+" & threadLocalObject="+threadLocalObject.get());
	}
	
	@Override
	public void run() {
		System.out.println("In thread "+ Thread.currentThread().getName()+", values are => nonThreadLocalObject="+nonThreadLocalObject+" & threadLocalObject="+threadLocalObject.get());
	}

}
