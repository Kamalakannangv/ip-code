package ip.multithreading.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AlphaNumberBuildingTask implements Callable<List<String>> {

	private final String[] albaphet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
	
	@Override
	public List<String> call() throws Exception {
		List<String> alphaNumbers = new ArrayList<>();
		for(int i = 1; i <= 1000; i++){
			alphaNumbers.add(getAlphaNumber(i));
		}
		
		// Add to get the result of RomanNumberBuildingTask when invokeAny() method is called in FutureMailClass
		/*if(true){
			throw new Exception();
		}*/
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
