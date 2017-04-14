package ip.multithreading.callable;

import java.util.concurrent.Callable;

public class Task implements Callable<String> {

	@Override
	public String call() throws Exception {
		for(int i = 0; i < 6 ; i++){
			Thread.sleep(1000);
			System.out.println("Priting in thread : " + i);
		}
		return "Task";
	}

}
