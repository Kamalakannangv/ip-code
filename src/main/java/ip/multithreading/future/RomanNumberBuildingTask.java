package ip.multithreading.future;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class RomanNumberBuildingTask implements Callable<List<String>> {
	
	 private final String[] rnChars = { "M",  "CM", "D", "C",  "XC", "L",  "X", "IX", "V", "I" };
	 private final int[] rnVals = {  1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };

	 final static Logger logger = Logger.getLogger(RomanNumberBuildingTask.class);
	 
	@Override
	public List<String> call() throws Exception {
		long startTime = new Date().getTime();
		List<String> romanNumbers = new ArrayList<>();
		for(int i = 1; i <= 1000; i++){
			romanNumbers.add(convertToRoman(i));
		}
		long endTime = new Date().getTime();
		//System.out.println("Time for Roman: "+ (endTime - startTime));
		logger.info("Time for Roman: "+ (endTime - startTime));
		return romanNumbers;
	}
	
	private String convertToRoman(int mInt) {
	    String retVal = "";
	    for (int i = 0; i < rnVals.length; i++) {
	        int numberInPlace = mInt / rnVals[i];
	        if (numberInPlace == 0) continue;
	        retVal += numberInPlace == 4 && i > 0? rnChars[i] + rnChars[i - 1]:
	            new String(new char[numberInPlace]).replace("\0",rnChars[i]);
	        mInt = mInt % rnVals[i];
	    }
	    return retVal;
	}

}
