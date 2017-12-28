package ip.multithreading.lock;

public class Consumer extends Thread {
	
	private Channel channel;
	
	public Consumer(Channel channel) {
		super("CONSUMER");
		this.channel = channel;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				Thread.sleep(5000);
				Integer item = channel.take();
				System.out.println("Consumer -> consuming item:" + item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
