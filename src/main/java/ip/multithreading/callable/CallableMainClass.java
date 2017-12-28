package ip.multithreading.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableMainClass {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Task task = new Task();
		Future<String> returnFuture = executorService.submit(task);
		Thread.sleep(2000);
		boolean cancelledStatus = returnFuture.cancel(false);
		System.out.println("Task cancelled? - " + cancelledStatus);
		System.out.println("Task stopped? - " + returnFuture.isDone());
		System.out.println("Task Can? - " + returnFuture.isCancelled());
		
		executorService.shutdown();
	}

}
