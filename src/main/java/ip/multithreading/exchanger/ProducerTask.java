package ip.multithreading.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ProducerTask implements Runnable {
	
	private Exchanger<Integer> exchanger;
	Random randomNumberGenerator = new Random();
	
	public ProducerTask(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		Integer num = new Integer(0);
		while(num != null){
			num = randomNumberGenerator.nextInt(1000);
			System.out.println("ProducerTask : " + num);
			try {
				Thread.sleep(1000);
				num = exchanger.exchange(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
