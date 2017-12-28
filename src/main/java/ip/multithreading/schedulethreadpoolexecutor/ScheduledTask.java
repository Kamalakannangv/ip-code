package ip.multithreading.schedulethreadpoolexecutor;

import java.util.concurrent.Callable;

public class ScheduledTask implements Callable<String> {
	
	private TimePrinter timePrinter;
	
	public ScheduledTask(TimePrinter timePrinter) {
		this.timePrinter = timePrinter;
	}

	@Override
	public String call(){
		return timePrinter.printTime();
	}

}
