package ip.multithreading.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ServerTask implements Runnable {
	
	private CountDownLatch latch;
	
	public ServerTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Server waiting for services to start...");
		try {
			latch.await();
			System.out.println("All Services started, Server starting...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
