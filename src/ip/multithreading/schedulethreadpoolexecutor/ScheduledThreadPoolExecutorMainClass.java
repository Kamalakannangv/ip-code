package ip.multithreading.schedulethreadpoolexecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorMainClass {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		TimePrinter timePrinter = new TimePrinter();
		
		/*
		ScheduledTask task = new ScheduledTask(timePrinter);
		ScheduledFuture<String> taskReturnValue = service.schedule(task, 1, TimeUnit.SECONDS);
		System.out.println(taskReturnValue.get());
		*/
		
		TimePrinterTask task = new TimePrinterTask(timePrinter);
		//service.scheduleAtFixedRate(task, 1, 10, TimeUnit.SECONDS);
		service.scheduleWithFixedDelay(task, 1, 10, TimeUnit.SECONDS);
		Thread.sleep(200000);
		
		service.shutdown();
	}

}
