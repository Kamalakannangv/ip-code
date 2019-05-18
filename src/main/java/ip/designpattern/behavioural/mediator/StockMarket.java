package ip.designpattern.behavioural.mediator;

import java.util.Set;

public interface StockMarket {

	public Set<Stock> getStockList();
	
	public Stock getStock(String stockId);
	
	public double getStockCurrentPrice(String stockId);
	
	public void trade(TradeRequest tradeRequest);

	public void addDivident(String stockId, double value);
	
}
