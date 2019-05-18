package ip.designpattern.behavioural.mediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ip.designpattern.behavioural.mediator.TradeRequest.PROCESSED_STATUS;
import ip.designpattern.behavioural.mediator.TradeRequest.TRADE_TYPE;

public class StockHolder extends StockMarketUser {
	
	private int tradingFrequency;
	private double currentBalance = 150.00; 
	private double dividentValue = 0.0;
	private Map<String, Double> failedBits = new HashMap<>();

	public StockHolder(String name, StockMarket stockMarket, int tradingFrequency) {
		super(name, stockMarket);
		this.tradingFrequency = tradingFrequency;
		register(stockMarket);
	}

	@Override
	protected void register(StockMarket stockMarket) {
		if(stockMarket instanceof StockExchange){
			StockExchange stockExchange = (StockExchange)stockMarket;
			stockExchange.register(this);
		}
	}

	@Override
	public void stockTradeCompleted(TradeRequest stockTrade) {
		if(stockTrade.getStatus() == PROCESSED_STATUS.SUCCESS){
			if(stockTrade.getTradeType() == TRADE_TYPE.BUY){
				currentBalance = currentBalance - stockTrade.getProcessedPrice();
			}else if(stockTrade.getTradeType() == TRADE_TYPE.SELL){
				currentBalance = currentBalance + stockTrade.getProcessedPrice();
			}
			failedBits.remove(stockTrade.getStockId());
		}else{
			failedBits.put(stockTrade.getStockId(), stockTrade.getProcessedPrice());
		}
	}
	
	private StockHolder getCurrentStockHolder() {
		return this;
	}
	
	public void addDividentValue(double dividentValue){
		synchronized (this) {
			this.dividentValue = this.dividentValue + dividentValue;
		}
	}
	
	public double getDividentValue() {
		return dividentValue;
	}

	
	
	
	public void startTrading() {
		
		Runnable tradingTask = new Runnable() {
			Random random = new Random();
			public void run() {
				while(true){
					try {
						sendTradeRequest();
						Thread.sleep(tradingFrequency);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			private void sendTradeRequest(){
				TRADE_TYPE tradeType = TRADE_TYPE.getType(random.nextInt(2));
				List<String> tradableStocks = getStockListForTrade(tradeType);
				if(tradableStocks.size() > 0){
					String stockForTrade = tradableStocks.get(random.nextInt(tradableStocks.size()));
					Double lastFailedTradePrice = failedBits.get(stockForTrade);
					if(null == lastFailedTradePrice || lastFailedTradePrice.doubleValue() == 0.0){
						lastFailedTradePrice = stockMarket.getStockCurrentPrice(stockForTrade);
					}
					if(tradeType == TRADE_TYPE.BUY){
						lastFailedTradePrice = lastFailedTradePrice + random.nextDouble();
						if(lastFailedTradePrice <= currentBalance){
							TradeRequest tradeRequest = new TradeRequest(stockForTrade, tradeType, getCurrentStockHolder());
							tradeRequest.setBitPrice(lastFailedTradePrice);
							//System.out.println(stockHolderName + " is trying to " + tradeType +" " + stockTrade.getStockId());
							stockMarket.trade(tradeRequest);
						}
					}else{
						lastFailedTradePrice = lastFailedTradePrice - random.nextDouble();
						TradeRequest tradeRequest = new TradeRequest(stockForTrade, tradeType, getCurrentStockHolder());
						tradeRequest.setBitPrice(lastFailedTradePrice);
						//System.out.println(stockHolderName + " is trying to " + tradeType +" " + stockTrade.getStockId());
						stockMarket.trade(tradeRequest);
					}
				}
			}
		};
		Thread tradingThread = new Thread(tradingTask);
		tradingThread.start();
	}
	
	private List<String> getStockListForTrade(TRADE_TYPE tradeType){
		List<String> stockHolding = getCurrentStockHolding();
		List<String> tradableStocks = new ArrayList<>();
		if(tradeType == TRADE_TYPE.SELL){
			tradableStocks = stockHolding;
		}else if(tradeType == TRADE_TYPE.BUY){
			for(Stock stock : stockMarket.getStockList()){
				if(!stockHolding.contains(stock.getStockId())){
					tradableStocks.add(stock.getStockId());
				}
			}
		}
		return tradableStocks;
	}

	public List<String> getCurrentStockHolding() {
		List<String> stockHolding = new ArrayList<>();
		Set<Stock> fullStocks = stockMarket.getStockList();
		for(Stock stock : fullStocks){
			if(((StockExchange)stockMarket).getStockHolder(stock.getStockId()) != null && ((StockExchange)stockMarket).getStockHolder(stock.getStockId()).getName().equalsIgnoreCase(this.getName())){
				stockHolding.add(stock.getStockId());
			}
		}
		return stockHolding;
	}

	public double availableBalance() {
		return currentBalance;
	}

	

	
	
}
