package ip.multithreading.semaphore;

import java.util.Random;

import ip.multithreading.semaphore.PrinterResourcePool.CapsPrinter;

public class Task implements Runnable {
	
	private PrinterResourcePool pool;
	private String[] wordPool;
	private Random rand = new Random();
	
	public Task(PrinterResourcePool pool, String[] wordPool) {
		this.pool = pool;
		this.wordPool = wordPool;
	}

	@Override
	public void run() {
		CapsPrinter printer = pool.getPrinter();
		String word = wordPool[rand.nextInt(10)];
		printer.print(word);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.releasePrinter(printer);
	}

}
