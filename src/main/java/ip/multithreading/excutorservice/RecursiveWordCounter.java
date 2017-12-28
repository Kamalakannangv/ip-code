package ip.multithreading.excutorservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class RecursiveWordCounter extends RecursiveTask<WordCounterResult> {

	private static final long serialVersionUID = 1L;
	private File folder;

	public RecursiveWordCounter(File folder) {
		this.folder = folder;
	}

	@Override
	protected WordCounterResult compute() {
		if(folder.isFile()){
			if(folder.getAbsolutePath().trim().endsWith(".java")){
				WordCounter wordCounter = new WordCounter(folder);
				return wordCounter.call();	
			}else{
				return new WordCounterResult();
			}
		}else{
			List<RecursiveWordCounter> recursiveWordCounters = new ArrayList<>();
			File[] files = folder.listFiles();
			boolean forkNeeded = false;
			for(File file : files){
				forkNeeded = (file.isDirectory()) ? true : (file.isFile() && file.getAbsolutePath().trim().endsWith(".java")) ? true : false;
				if(forkNeeded){
					final RecursiveWordCounter recursiveWordCounter = new RecursiveWordCounter(file);
					recursiveWordCounter.fork();
					recursiveWordCounters.add(recursiveWordCounter);
				}
			}
			WordCounterResult result = new WordCounterResult();
			for(RecursiveWordCounter counter : recursiveWordCounters){
				result = counter.join().merge(result);
			}
			return result;
		}
	}

}
