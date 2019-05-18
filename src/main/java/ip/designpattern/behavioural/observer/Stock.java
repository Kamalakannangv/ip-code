package ip.designpattern.behavioural.observer;

public class Stock extends StockAbservable implements StockInterface, Comparable<Stock> {
	
	private String companyName;
	private String stockId;
	private double stockValue;
	
	
	public Stock(String companyName, String stockId) {
		this.companyName = companyName;
		this.stockId = stockId;
		this.stockValue = 10.0d;
	}

	@Override
	public String getCompanyName() {
		return companyName;
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
		return ((obj instanceof Stock)
				&& ((Stock)obj).getStockId().equalsIgnoreCase(stockId));
	}

	@Override
	public int compareTo(Stock o) {
		return stockId.compareTo(o.getStockId());
	}

}
