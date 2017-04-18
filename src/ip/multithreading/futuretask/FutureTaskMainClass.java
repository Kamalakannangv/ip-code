package ip.multithreading.futuretask;

import java.util.concurrent.FutureTask;

public class FutureTaskMainClass {
	
	public static void main(String[] args) throws InterruptedException {
		Void v = null;
		FutureTask<Void> printingTask = new FutureTask<Void>(new NumberPrinter(), v);
		Thread thread = new Thread(printingTask);
		thread.start();
		Thread.sleep(10000);
		boolean cancelStatus = printingTask.cancel(true);
		System.out.println("Cancel Status : " + cancelStatus);
	}

}
