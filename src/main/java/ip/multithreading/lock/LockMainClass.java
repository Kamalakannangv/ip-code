package ip.multithreading.lock;

public class LockMainClass {
	
	public static void main(String[] args) {
		Channel channel = new Channel(5);
		Producer producer = new Producer(channel);
		Consumer consumer = new Consumer(channel);
		consumer.start();
		producer.start();
	}

}
