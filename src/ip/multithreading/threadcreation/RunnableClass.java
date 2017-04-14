package ip.multithreading.threadcreation;

public class RunnableClass implements Runnable {

	@Override
	public void run() {
		System.out.println("Inside RunnableClass run() method : " + Thread.currentThread().getName());

	}

}
