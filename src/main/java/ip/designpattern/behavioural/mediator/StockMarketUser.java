package ip.designpattern.behavioural.mediator;

public abstract class StockMarketUser {
	
	protected String name;
	protected StockMarket stockMarket;
	
	public StockMarketUser(String name, StockMarket stockMarket) {
		this.name = name;
		this.stockMarket = stockMarket;
	}
	
	public String getName() {
		return name;
	}

	protected abstract void register(StockMarket stockMarket); 
	
	public abstract void stockTradeCompleted(TradeRequest tradeRequest);

}
