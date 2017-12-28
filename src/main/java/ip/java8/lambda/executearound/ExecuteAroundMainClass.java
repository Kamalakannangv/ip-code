package ip.java8.lambda.executearound;

import java.io.BufferedReader;
import java.io.File;

public class ExecuteAroundMainClass {
	
	private final static File file = new File("D:/Test/SampleTestClass.java");
	
	public static void main(String[] args) {
		
		FileCounter fileCounter = new FileCounter(file);
		System.out.println("No of lines : " + fileCounter.countLines());
		System.out.println("No of words : " + fileCounter.countWords());
		
		
		FileCounterUsingLambda fileCounterUsingLambda = new FileCounterUsingLambda(file);
		BufferedReaderCounter lineCounter = (BufferedReader br) -> {
			int count = 0;
			String line = null;
			while((line = br.readLine()) != null){
				count++;
			}
			return count;
		};
		BufferedReaderCounter wordCounter = (BufferedReader br) -> {
			int count = 0;
			String line = null;
			while((line = br.readLine()) != null){
				count = line.split("\\W+").length + count;
			}
			return count;
		};
		System.out.println("No of lines using Lambda: " + fileCounterUsingLambda.counter(lineCounter));
		System.out.println("No of Words using Lambda: " + fileCounterUsingLambda.counter(wordCounter));
		
		TunedFileCounterUsingLambda tunedFileCounterUsingLambda = new TunedFileCounterUsingLambda(file);
		System.out.println("No of lines using Lambda-tuned: " + tunedFileCounterUsingLambda.count((String line) -> 1));
		System.out.println("No of Words using Lambda-tuned: " + tunedFileCounterUsingLambda.count((String line) -> line.split("\\W+").length));
	}

}
