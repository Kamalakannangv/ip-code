package ip.multithreading.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RomanNumberBuildingTask implements Callable<List<String>> {
	
	 private final String[] rnChars = { "M",  "CM", "D", "C",  "XC", "L",  "X", "IX", "V", "I" };
	 private final int[] rnVals = {  1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };

	@Override
	public List<String> call() throws Exception {
		List<String> romanNumbers = new ArrayList<>();
		for(int i = 1; i <= 1000; i++){
			romanNumbers.add(convertToRoman(i));
		}
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
