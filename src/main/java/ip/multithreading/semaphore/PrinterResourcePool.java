package ip.multithreading.semaphore;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;

public class PrinterResourcePool {
	
	private Semaphore semaphore;
	private int poolSize;
	private  Deque<CapsPrinter> printers;
	
	public PrinterResourcePool(int size){
		poolSize = size;
		semaphore = new Semaphore(poolSize, true);
		printers = new ArrayDeque<>();
		for(int i = 0; i < poolSize; i++){
			printers.push(new CapsPrinter());
		}
		Thread daemonThread = new Thread(new PoolSizeValidator());
		daemonThread.setDaemon(true);
		daemonThread.start();
	}
	
	public CapsPrinter getPrinter(){
		try {
			semaphore.acquire();
			CapsPrinter printer = printers.removeLast();
			return printer;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void releasePrinter(CapsPrinter capsPrinter){
		printers.push(capsPrinter);
		semaphore.release();
	}
	
	class CapsPrinter{
		public void print(String value){
			System.out.println("\n*******************************************************************");
			System.out.println("Current Thread : " + Thread.currentThread().getName());
			System.out.println(value.toUpperCase());
			System.out.println("*******************************************************************\n");
		}
	}
	
	private class PoolSizeValidator implements Runnable{

		@Override
		public void run() {
			while(true){
				if(printers.size() > poolSize){
					System.out.println("########### Exception.. Pool size increased beyond limit ###############");
				}else{
					System.out.println("Pool size is within limit");
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
