package ip.multithreading.excutorservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class WordCounter implements Callable<WordCounterResult> {
	
	private File file;
	
	public WordCounter(File file) {
		this.file = file;
	}

	@Override
	public WordCounterResult call(){
		WordCounterResult result = new WordCounterResult();
		try(
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				){
			String line = null;
			while((line = br.readLine()) != null){
				for(String word : line.split("[^\\w']+")){
					if(word.trim().length() > 0){
						result.addWord(word.trim());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("File:"+file.getName() + " Count:" + result.getTotalCount());
		return result;
	}
}
