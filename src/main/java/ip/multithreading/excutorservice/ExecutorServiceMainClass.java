package ip.multithreading.excutorservice;

import java.io.File;

public class ExecutorServiceMainClass {
	
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		File folder = new File("D:\\Work\\Workspace\\CSC\\Github\\gkamalakanna\\integral-life");
		int noOfThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("No of threads : " + noOfThreads);
		//SuperExecutorService service = new ThreadPoolExecutorService(folder, noOfThreads);
		SuperExecutorService service = new ForkJoinExecutorService(folder, noOfThreads);
		WordCounterResult result = service.process();
		System.out.println("Total Counts : " + result.getTotalCount());
		System.out.println("Word List : " + result.getSortedWordCount(false));
		service.shutDownService();
		long timeTake = System.currentTimeMillis() - startTime;
		System.out.println("Time taken : " + timeTake);
	}
}
