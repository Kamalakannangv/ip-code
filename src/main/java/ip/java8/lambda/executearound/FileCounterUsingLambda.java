package ip.java8.lambda.executearound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCounterUsingLambda {

	private File file;

	public FileCounterUsingLambda(File file) {
		this.file = file;
	}

	public int counter(BufferedReaderCounter counter){
		int count = 0;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			count = counter.counter(bufferedReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileReader != null){
					fileReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}
