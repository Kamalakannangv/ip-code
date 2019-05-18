package ip.designpattern.behavioural.mediator;


public class StockListedCompany extends StockMarketUser {

	private String stockId;
	private double ipoValue;

	public StockListedCompany(String name, StockMarket stockMarket, double ipoValue) {
		super(name, stockMarket);
		this.ipoValue = ipoValue;
		register(stockMarket);
	}

	public double getIpoValue() {
		return ipoValue;
	}

	@Override
	protected void register(StockMarket stockMarket) {
		if(stockMarket instanceof StockExchange){
			StockExchange stockExchange = (StockExchange)stockMarket;
			stockId = stockExchange.register(this);
		}
	}

	@Override
	public void stockTradeCompleted(TradeRequest tradeRequest) {
		if(tradeRequest.isProcessed()){
			StockDividentAdder stockDividentAdder = new StockDividentAdder(ipoValue);
			stockDividentAdder.start();
		}
	}

	public String getStockId() {
		return stockId;
	}
	
	class StockDividentAdder extends Thread{

		private double stockValue;

		public StockDividentAdder(double stockValue) {
			this.stockValue = stockValue;
		}

		@Override
		public void run() {
			try {
				while(true){
					Thread.sleep(10000);
					Stock stock = stockMarket.getStock(stockId);
					double currentStockValue = stock.getStockValue();
					if(stock.getStockValue() > (stockValue * 0.25)){
						double dividentValue = (currentStockValue - stockValue)/2;
						if(dividentValue > 0){
							stockMarket.addDivident(stockId, dividentValue);
							stockValue = currentStockValue - (dividentValue * 2);
							stock.setStockValue(stockValue);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}


}
