package ip.multithreading.runnablefuturesubmit;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableFutureSubmitMainClass {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		String result = "Completed";
		RunnableTask task = new RunnableTask();
		Future<String> returnValue = executorService.submit(task, result);
		if(returnValue.get() != null){
			System.out.println("Return value is : " + returnValue.get());
		}
		executorService.shutdown();
	}

}
