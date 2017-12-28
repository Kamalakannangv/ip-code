package ip.multithreading.exchanger;

import java.util.concurrent.Exchanger;

public class ConsumerTask implements Runnable {

	private Exchanger<Integer> exchanger;
	
	public ConsumerTask(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
	}
	
	@Override
	public void run() {
		Integer num = new Integer(0);
		while(num != null){
			try {
				Thread.sleep(1000);
				num = exchanger.exchange(num);
				System.out.println("\tConsumerTask : " + num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
