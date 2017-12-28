package ip.multithreading.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMainClass {
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(3);
		ServerTask serverTask = new ServerTask(latch);
		Thread serverStartingThread = new Thread(serverTask);
		serverStartingThread.start();
		Thread.sleep(1000);
		ServiceStartingTask cacheServiceStartingTask = new ServiceStartingTask(latch, "Cache Service");
		ServiceStartingTask alertServiceStartingTask = new ServiceStartingTask(latch, "Alert Service");
		ServiceStartingTask validationServiceStartingTask = new ServiceStartingTask(latch, "Validation Service");
		Thread cacheServiceThread = new Thread(cacheServiceStartingTask);
		Thread alertServiceThread = new Thread(alertServiceStartingTask);
		Thread validationServiceThread = new Thread(validationServiceStartingTask);
		cacheServiceThread.start();
		alertServiceThread.start();
		validationServiceThread.start();
	}

}
