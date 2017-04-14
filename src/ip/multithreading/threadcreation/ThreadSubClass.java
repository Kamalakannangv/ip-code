package ip.multithreading.threadcreation;

public class ThreadSubClass extends Thread {
	
	public ThreadSubClass(Runnable task, String name) {
		super(task, name);
	}
	
	 public ThreadSubClass(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		System.out.println("Inside ThreadSubClass run() method : " + Thread.currentThread().getName());
	}

}
