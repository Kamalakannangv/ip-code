package ip.multithreading.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerMainClass {
	
	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		ProducerTask producerTask = new ProducerTask(exchanger);
		ConsumerTask consumerTask = new ConsumerTask(exchanger);
		Thread producingThread = new Thread(producerTask);
		Thread consumingThread = new Thread(consumerTask);
		producingThread.start();
		consumingThread.start();
	}

}
