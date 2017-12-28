package ip.multithreading.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ServiceStartingTask implements Runnable {
	
	private String serviceName;
	private CountDownLatch latch;
	
	public ServiceStartingTask(CountDownLatch latch, String serviceName) {
		this.latch = latch;
		this.serviceName = serviceName;
	}

	@Override
	public void run() {
		int processingTime = (new Random().nextInt(10) % 10) * 10000;
		System.out.println("Service-" + serviceName + " is startings - waiting time:"+processingTime);
		try {
			Thread.sleep(processingTime);
			latch.countDown();
			System.out.println("Service-" + serviceName + " has started");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
