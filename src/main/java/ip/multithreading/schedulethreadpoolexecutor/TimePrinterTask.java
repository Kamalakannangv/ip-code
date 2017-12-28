package ip.multithreading.schedulethreadpoolexecutor;

public class TimePrinterTask implements Runnable {
	
	private TimePrinter timePrinter;
	
	public TimePrinterTask(TimePrinter timePrinter) {
		this.timePrinter = timePrinter;
	}

	@Override
	public void run() {
		timePrinter.printTime();
	}

}
