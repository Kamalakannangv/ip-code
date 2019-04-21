package ip.multithreading.future;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class AlphaNumberBuildingTask implements Callable<List<String>> {

	private final String[] albaphet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
	
	final static Logger logger = Logger.getLogger(AlphaNumberBuildingTask.class);
	
	@Override
	public List<String> call() throws Exception {
		long startTime = new Date().getTime();
		List<String> alphaNumbers = new ArrayList<>();
		for(int i = 1; i <= 10000; i++){
			alphaNumbers.add(getAlphaNumber(i));
		}
		
		// Add to get the result of RomanNumberBuildingTask when invokeAny() method is called in FutureMailClass
		/*if(true){
			throw new Exception();
		}*/
		long endTime = new Date().getTime();
		//System.out.println("Time for Alpha: "+ (endTime - startTime));
		logger.info("Time for Alpha: "+ (endTime - startTime));
		return alphaNumbers;
	}
	
	private String getAlphaNumber(int num){
		StringBuilder alphaEqNum = new StringBuilder();
		while(num > 0){
			num = num -1;
			if(num < 26){
				alphaEqNum.insert(0, albaphet[num]);
				num = 0;
			}else{
				alphaEqNum.insert(0, albaphet[num % 26]);
				num = num / 26;
			}
		}
		return alphaEqNum.toString();
	}

}
