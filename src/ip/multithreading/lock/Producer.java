package ip.multithreading.lock;

import java.util.Random;

public class Producer extends Thread {
	
	private Channel channel;
	private Random rand = new Random();
	
	public Producer(Channel channel) {
		super("PRODUCER");
		this.channel = channel;
	}
	
	@Override
	public void run() {
		try {
			Integer item = null;
			for(int i = 0; i < 10 ; i++){
				item = getNextItem();
				System.out.println("Producer -> Adding item:" + item);
				channel.put(item);
			}
			for(int i = 0; i < 10 ; i++){
				Thread.sleep(10000);
				item = getNextItem();
				System.out.println("Producer -> Adding item:" + item);
				channel.put(item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Integer getNextItem(){
		return rand.nextInt(99);
	}

}
