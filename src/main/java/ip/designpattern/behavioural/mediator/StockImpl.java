package ip.designpattern.behavioural.mediator;

public class StockImpl extends StockAbservable implements Stock, Comparable<StockImpl> {
	
	private StockMarket stockMarket;
	private StockListedCompany company;
	private String stockId;
	private double stockValue;
	
	public StockImpl(StockMarket stockMarket, StockListedCompany company, String stockId, double stockValue) {
		this.stockMarket = stockMarket;
		this.company = company;
		this.stockId = stockId;
		this.stockValue = stockValue;
	}
	
	@Override
	public StockMarket getStockMarket() {
		return stockMarket;
	}

	@Override
	public StockListedCompany getCompany() {
		return company;
	}

	@Override
	public String getStockId() {
		return stockId;
	}
	
	@Override
	public void setStockValue(double fundValue) {
		this.stockValue = fundValue;
		notifyObserver(this);
	}

	@Override
	public double getStockValue() {
		return stockValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((obj instanceof StockImpl)
				&& ((StockImpl)obj).getStockId().equalsIgnoreCase(stockId));
	}

	@Override
	public int compareTo(StockImpl o) {
		return stockId.compareTo(o.getStockId());
	}

}
