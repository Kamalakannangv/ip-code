package ip.multithreading.waitnotify;

public class WaitNotifyMainClass {
	
	public static void main(String[] args) {
		MessageCarringChannel channel = new MessageCarringChannel();
		ProducerTask producerTask = new ProducerTask(channel);
		ConsumerTask consumerTask = new ConsumerTask(channel);
		
		Thread consumer1Thread = new Thread(consumerTask, "Consumer1");
		Thread producerThread = new Thread(producerTask, "Producer");
		Thread consumer2Thread = new Thread(consumerTask, "Consumer2");
		consumer1Thread.start();
		producerThread.start();
		consumer2Thread.start();
		System.out.println("All the threads are started");
	}

}
