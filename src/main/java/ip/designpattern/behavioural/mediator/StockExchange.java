package ip.designpattern.behavioural.mediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ip.designpattern.behavioural.mediator.TradeRequest.PROCESSED_STATUS;
import ip.designpattern.behavioural.mediator.TradeRequest.TRADE_TYPE;

public class StockExchange implements StockMarket {


	private String stockExchangeName;
	private Map<Stock, StockHolder> tradingStocks = new HashMap<>();
	private Map<Stock, StockListedCompany> ipoStocks = new HashMap<>();
	private List<StockListedCompany> listedCompanies = new ArrayList<>();
	private List<StockHolder> stockHolders = new ArrayList<>();
	private Set<TradeRequest> buyingTradeRequests = new HashSet<>();
	private Set<TradeRequest> sellingTradeRequests = new HashSet<>();

	public StockExchange(String stockExchangeName){
		this.stockExchangeName = stockExchangeName;
	}

	@Override
	public Set<Stock> getStockList() {
		Set<Stock> stockList = new HashSet<>();
		stockList.addAll(tradingStocks.keySet());
		stockList.addAll(ipoStocks.keySet());
		return stockList;
	}

	@Override
	public Stock getStock(String stockId) {
		Iterator<Stock> stockIter = tradingStocks.keySet().iterator();
		while(stockIter.hasNext()){
			Stock stock = stockIter.next();
			if(stockId.equalsIgnoreCase(stock.getStockId())){
				return stock;
			}
		}
		stockIter = ipoStocks.keySet().iterator();
		while(stockIter.hasNext()){
			Stock stock = stockIter.next();
			if(stockId.equalsIgnoreCase(stock.getStockId())){
				return stock;
			}
		}
		return null;
	}

	@Override
	public double getStockCurrentPrice(String stockId) {
		Stock stock = getStock(stockId);
		if(stock != null){
			return stock.getStockValue();
		}
		return 0;
	}

	@Override
	public void trade(TradeRequest tradeRequest) {
		synchronized (sellingTradeRequests) {
			for(Stock stock : getStockList()){
				if(stock.getStockId().equalsIgnoreCase(tradeRequest.getStockId())){
					//stock.attach(this);
					if(tradeRequest.getTradeType() == TRADE_TYPE.BUY){
						boolean alreadyExit = false;
						for(TradeRequest buyStockTrade : buyingTradeRequests){
							if(buyStockTrade.equals(tradeRequest)){
								alreadyExit = true;
								buyStockTrade.setBitPrice(tradeRequest.getBitPrice());
								break;
							}
						}
						if(!alreadyExit){
							buyingTradeRequests.add(tradeRequest);
						}
					}else if(tradeRequest.getTradeType() == TRADE_TYPE.SELL 
							&& (/*(tradingStocks.get(stock) instanceof StockListedCompany) ||*/ getStockHolder(stock.getStockId()).getName().equalsIgnoreCase(tradeRequest.getStockTrader().getName()))){
						boolean alreadyExit = false;
						for(TradeRequest sellStockTrade: sellingTradeRequests){
							if(sellStockTrade.equals(tradeRequest)){
								alreadyExit = true;
								sellStockTrade.setBitPrice(tradeRequest.getBitPrice());
								break;
							}
						}
						if(!alreadyExit){
							sellingTradeRequests.add(tradeRequest);
						}
						StockSellingThread stockSellingThread = new StockSellingThread(tradeRequest, 20000);
						stockSellingThread.start();
					}
					break;
				}
			}
		}
	}

	@Override
	public void addDivident(String stockId, double value) {
		StockMarketUser stockHolder = getStockHolder(stockId);
		if(stockHolder instanceof StockHolder){
			((StockHolder)stockHolder).addDividentValue(value);
			System.out.println("\n\n\n\nStockHolder " + stockHolder.getName() + " got divident " + String.format("%.2f", value) + " for stock " + stockId
					+ ", Current Value: [Balance=" + String.format("%.2f", ((StockHolder) stockHolder).availableBalance()) + ", Divident=" 
					+ String.format("%.2f", ((StockHolder)stockHolder).getDividentValue()) +"]");
		}
	}

	public String register(StockMarketUser stockMarketUser) {
		String stockMarkerUserId = null;
		if(stockMarketUser instanceof StockListedCompany){
			StockListedCompany company = (StockListedCompany)stockMarketUser;
			String stockId = company.getName().replaceAll(" ", "").substring(0, 4).toUpperCase();
			stockMarkerUserId = stockId;
			StockImpl newStock = new StockImpl(this, company, stockId, company.getIpoValue());
			ipoStocks.put(newStock, company);
			TradeRequest tradeRequest = new TradeRequest(stockId, TRADE_TYPE.SELL, stockMarketUser);
			tradeRequest.setBitPrice(company.getIpoValue());
			trade(tradeRequest);
			listedCompanies.add(company);
		}else if(stockMarketUser instanceof StockHolder){
			stockHolders.add((StockHolder)stockMarketUser);
		}
		return stockMarkerUserId;
	}

	public void unRegister(StockMarketUser stockMarketUser) {
		if(stockMarketUser instanceof StockListedCompany){
			listedCompanies.remove((StockListedCompany)stockMarketUser);
		}else if(stockMarketUser instanceof StockHolder){
			stockHolders.remove((StockHolder)stockMarketUser);
		}
	}

	public StockMarketUser getStockHolder(String stockId) {
		Iterator<Stock> stockIter = tradingStocks.keySet().iterator();
		while(stockIter.hasNext()){
			Stock stock = stockIter.next();
			if(stock.getStockId().equalsIgnoreCase(stockId)){
				return tradingStocks.get(stock);
			}
		}
		stockIter = ipoStocks.keySet().iterator();
		while(stockIter.hasNext()){
			Stock stock = stockIter.next();
			if(stock.getStockId().equalsIgnoreCase(stockId)){
				return ipoStocks.get(stock);
			}
		}
		return null;
	}
	
	public String getStockExchangeName() {
		return stockExchangeName;
	}

	class StockSellingThread extends Thread{

		private TradeRequest stockTradeRequest; 
		private int stockAuctionTime;

		public StockSellingThread(TradeRequest stockTradeRequest, int stockAuctionTime) {
			this.stockTradeRequest = stockTradeRequest;
			this.stockAuctionTime = stockAuctionTime;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(stockAuctionTime);
				synchronized (sellingTradeRequests) {
					sellingTradeRequests.remove(stockTradeRequest);
					List<TradeRequest> matchingBuyStockTrades = new ArrayList<>();
					TradeRequest winningBuyStock = null;
					double highedBit = 0;
					for(TradeRequest buyStockTrade : buyingTradeRequests){
						if(buyStockTrade.getStockId().equalsIgnoreCase(this.stockTradeRequest.getStockId())){
							matchingBuyStockTrades.add(buyStockTrade);
							if(!buyStockTrade.getStockTrader().getName().equalsIgnoreCase(getStockHolder(this.stockTradeRequest.getStockId()).getName())){
								if(stockTradeRequest.getBitPrice() <= buyStockTrade.getBitPrice() 
										&& ((winningBuyStock == null) || (buyStockTrade.getBitPrice() > winningBuyStock.getBitPrice()))){
									winningBuyStock = buyStockTrade;
								}else{
									if(highedBit < buyStockTrade.getBitPrice()){
										highedBit = buyStockTrade.getBitPrice(); 
									}
								}
							}
						}
					}

					if(winningBuyStock == null){
						if(getStockHolder(stockTradeRequest.getStockId()) instanceof StockListedCompany){
							trade(stockTradeRequest);
						}else{
							stockTradeRequest.setProcessed(true);
							stockTradeRequest.setProcessedPrice(highedBit);
							stockTradeRequest.setStatus(PROCESSED_STATUS.FAIL);
							//System.out.println(stockTrade.getStockHolder().getStockHolderName() + " selling " + stockTrade.getStockId() + " failed");
							StockMarketUser stockMarketUser = getStockHolder(stockTradeRequest.getStockId());
							stockMarketUser.stockTradeCompleted(stockTradeRequest);
						}
					}else{

						Stock matchedStock = null;

						for(Stock stock : getStockList()){
							if(stock.getStockId().equalsIgnoreCase(stockTradeRequest.getStockId())
									&& ((getStockHolder(stock.getStockId()) instanceof StockListedCompany) 
											|| (getStockHolder(stock.getStockId()).getName().equalsIgnoreCase(stockTradeRequest.getStockTrader().getName())))){
								matchedStock = stock;
								break;
							}
						}

						if(matchedStock != null){
							stockTradeRequest.setProcessed(true);
							stockTradeRequest.setStatus(PROCESSED_STATUS.SUCCESS);
							stockTradeRequest.setProcessedPrice(winningBuyStock.getBitPrice());
							highedBit = winningBuyStock.getBitPrice();
							if(stockTradeRequest.getStockTrader() != null){
								stockTradeRequest.getStockTrader().stockTradeCompleted(stockTradeRequest);
							}
							winningBuyStock.setProcessed(true);
							winningBuyStock.setStatus(PROCESSED_STATUS.SUCCESS);
							winningBuyStock.setProcessedPrice(highedBit);
							//matchedStock.setHolderName(winningBuyStock.getStockTrader().getStockMarketUserName());
							ipoStocks.remove(matchedStock);
							tradingStocks.put(matchedStock, (StockHolder)winningBuyStock.getStockTrader());

							System.out.println("\n\n\n\n\n" + stockTradeRequest.getStockId() + " sold from " + (stockTradeRequest.getStockTrader() == null ? "Stock Marked" : stockTradeRequest.getStockTrader().getName()) + " to " 
									+ winningBuyStock.getStockTrader().getName() + " for " + String.format("%.2f", winningBuyStock.getBitPrice()) 
									+ " Current Value: [Balance=" + String.format("%.2f", ((StockHolder)winningBuyStock.getStockTrader()).availableBalance()) + ", Divident=" 
									+ String.format("%.2f", ((StockHolder)winningBuyStock.getStockTrader()).getDividentValue()) +"]");
							matchedStock.setStockValue(winningBuyStock.getBitPrice());
							winningBuyStock.getStockTrader().stockTradeCompleted(winningBuyStock);

						}
						for(TradeRequest matchedBuyTrade : matchingBuyStockTrades){
							sellingTradeRequests.remove(matchedBuyTrade);
							buyingTradeRequests.remove(matchedBuyTrade);
							matchedBuyTrade.setProcessed(true);
							matchedBuyTrade.setStatus(PROCESSED_STATUS.FAIL);
							matchedBuyTrade.setProcessedPrice(highedBit);
							matchedBuyTrade.getStockTrader().stockTradeCompleted(matchedBuyTrade);
						}
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
