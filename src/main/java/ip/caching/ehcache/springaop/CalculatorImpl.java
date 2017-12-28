package ip.caching.ehcache.springaop;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl implements ICalculator {
	
	public List<Integer> calculateFibonacciSeries(int num) {
		int firstNum = 0;
		int SecondNum = 1;
		int nextNum = 0;
		int fibonacciCount = num;
		List<Integer> fibonacciSeries = new ArrayList<>();
		while(fibonacciCount > 0){
			nextNum = firstNum + SecondNum;
			firstNum = SecondNum;
			SecondNum = nextNum;
			fibonacciSeries.add(nextNum);
			fibonacciCount = fibonacciCount - 1;
		}
		System.out.println("Returning value for " + num + " after calculation..");
		return fibonacciSeries;
	}
	
}

