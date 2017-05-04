package ip.multithreading.excutorservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorService implements SuperExecutorService{
	
	private File folder;
	private ThreadPoolExecutor executor;
	
	public ThreadPoolExecutorService(File folder, int noOfThreads) {
		this.folder = folder;
		executor =  (ThreadPoolExecutor)Executors.newFixedThreadPool(noOfThreads);
	}

	@Override
	public WordCounterResult process() {
		List<Future<WordCounterResult>> futureResults = startCounting(executor, folder);
		WordCounterResult finalResult = new WordCounterResult();
		try {
			/*executor.awaitTermination(10, TimeUnit.MINUTES);
			for(Future<WordCounterResult> futureResult : futureResults){
				if(futureResult.isDone()){
					WordCounterResult counter = futureResult.get();
					finalResult = counter.merge(finalResult);
				}
			}*/
			
			while(futureResults.size() > 0){
				for(int i = futureResults.size()-1; i >=0; i--){
					if(futureResults.get(i).isDone()){
						WordCounterResult counter = futureResults.get(i).get();
						finalResult = counter.merge(finalResult);
						futureResults.remove(i);
					}
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return finalResult;
	}
	
	@Override
	public void shutDownService() {
		executor.shutdown();
	}
	
	private List<Future<WordCounterResult>> startCounting(ThreadPoolExecutor executor, File folder){
		List<Future<WordCounterResult>> futureResults = new ArrayList<>();
		File[] files = folder.listFiles();
		for(File file : files){
			if(file.isFile()){
				if(file.getAbsolutePath().trim().endsWith(".java")){
					WordCounter wordCounter = new WordCounter(file);
					futureResults.add(executor.submit(wordCounter));
				}
			}else{
				futureResults.addAll(startCounting(executor, file));
			}
		}
		return futureResults;
	}
	
}
