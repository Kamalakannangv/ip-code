package ip.multithreading.future;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;


public class FutureMailClass {
	
	final static Logger logger = Logger.getLogger(FutureMailClass.class);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<Callable<List<String>>> callableList = new ArrayList<>();
		Callable<List<String>> romanBuilderCallable = new RomanNumberBuildingTask();
		Callable<List<String>> alphaNumberBuilderCallable = new AlphaNumberBuildingTask();
		callableList.add(romanBuilderCallable);
		callableList.add(alphaNumberBuilderCallable);
		long startTime = new Date().getTime();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		// ExecutorService's invokeAll() method execution
		List<Future<List<String>>> futures = executorService.invokeAll(callableList);
		for(Future<List<String>> future : futures){
			if(future.isDone()){
				//System.out.println(future.get());
			}
		}
		
		// ExecutorService's invokeAny() method execution
		/*List<String> result = executorService.invokeAny(callableList);
		System.out.println(result);*/
		long endTime = new Date().getTime();
		logger.info("Time for total: "+ (endTime - startTime));
		executorService.shutdown();
	}
	
}
