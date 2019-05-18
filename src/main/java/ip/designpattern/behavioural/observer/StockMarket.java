package ip.designpattern.behavioural.observer;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class StockMarket {
	private static StockMarket instance;
	private Set<Stock> fullStockList = new TreeSet<>();
	
	private StockMarket(){
	}
	
	public static StockMarket getInstance(){
		if(null == instance){
			instance = new StockMarket();
		}
		return instance;
	}
	
	public Set<Stock> getFullStockList() {
		return fullStockList;
	}

	public void startTrading(){
		Stock stock1 = new Stock("Infosys LTD", "INFY");
		Stock stock2 = new Stock("TCS LTD", "TCSP");
		Stock stock3 = new Stock("DXC Private LTD", "DXCP");
		Stock stock4 = new Stock("BHEL Govt Company", "BHEL");
		Stock stock5 = new Stock("The Boeing", "BOEG");
		fullStockList.add(stock1);
		fullStockList.add(stock2);
		fullStockList.add(stock3);
		fullStockList.add(stock4);
		fullStockList.add(stock5);
		Runnable tradeTask = new Runnable() {
			Random random = new Random();
			@Override
			public void run() {
				System.out.println("Trading started");
				while(true){
					try {
						Thread.sleep(2000);
						updateStockRandomly();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			private void updateStockRandomly(){
				Stock stock = getRandomStock();
				double updateValue = random.nextDouble() * random.nextInt(2);
				double newValue;
				if(random.nextBoolean()){
					newValue = stock.getStockValue() + updateValue;
				}else{
					newValue = stock.getStockValue() - updateValue;
				}
				stock.setStockValue(newValue);
			}
			
			private Stock getRandomStock(){
				Stock randomStock = null;
				int randomStockNum = random.nextInt(fullStockList.size());
				Iterator<Stock> iter = fullStockList.iterator();
				int randStockPos = 0;
				while(iter.hasNext()){
					randomStock = iter.next();
					if(randomStockNum == randStockPos){
						break;
					}
					randStockPos++;
				}
				return randomStock;
			}
		};
		Thread tradingThread = new Thread(tradeTask);
		tradingThread.start();
	}
	
}
