package ip.multithreading.excutorservice;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinExecutorService implements SuperExecutorService {
	
	private File folder;
	private ForkJoinPool executor;
	
	public ForkJoinExecutorService(File folder, int noOfThreads) {
		this.folder = folder;
		executor =  (ForkJoinPool)Executors.newWorkStealingPool(noOfThreads);
	}

	@Override
	public WordCounterResult process() {
		RecursiveWordCounter recursiveWordCounter = new RecursiveWordCounter(folder);
		WordCounterResult result = executor.invoke(recursiveWordCounter);
		return result;
	}

	@Override
	public void shutDownService() {
		executor.shutdown();
	}

}
