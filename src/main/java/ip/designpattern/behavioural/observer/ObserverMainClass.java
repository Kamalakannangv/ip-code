package ip.designpattern.behavioural.observer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObserverMainClass {

	public static void main(String[] args) throws InterruptedException {
		StockMarket stockMarket = StockMarket.getInstance();
		stockMarket.startTrading();
		Thread.sleep(2000);
		
		
		String[] intialStocks = {"INFY", "DXCP"};
		List<String> initialStockList = Arrays.asList(intialStocks);
		StockTrader stockTrader = new StockTrader("ABC", initialStockList);
		Thread.sleep(5000);
		stockTrader.startMonitoring("TCSP");
		Thread.sleep(5000);
		stockTrader.startMonitoring("BOEG");
		Thread.sleep(5000);
		stockTrader.startMonitoring("BHEL");
		
		
		MutualFundManager mutualFundManager = new MutualFundManager("XYZ");
		Set<String> firstMFStocks = new HashSet<>();
		firstMFStocks.add("INFY");
		firstMFStocks.add("DXCP");
		firstMFStocks.add("TCSP");
		MutualFund firstMF = new MutualFund("First Mutual Fund", firstMFStocks);
		mutualFundManager.addMutualFunds(firstMF);
		Thread.sleep(2000);
		
		Set<String> secondMFStocks = new HashSet<>();
		secondMFStocks.add("TCSP");
		secondMFStocks.add("BOEG");
		secondMFStocks.add("BHEL");
		MutualFund secondMF = new MutualFund("Second Mutual Fund", secondMFStocks);
		mutualFundManager.addMutualFunds(secondMF);
		Thread.sleep(2000);
		
		Set<String> thirdMFStocks = new HashSet<>();
		thirdMFStocks.add("DXCP");
		thirdMFStocks.add("TCSP");
		thirdMFStocks.add("BOEG");
		thirdMFStocks.add("INFY");
		MutualFund thirdMF = new MutualFund("Third Mutual Fund", thirdMFStocks);
		mutualFundManager.addMutualFunds(thirdMF);
		Thread.sleep(2000);
		
		
	}
	
	
}
