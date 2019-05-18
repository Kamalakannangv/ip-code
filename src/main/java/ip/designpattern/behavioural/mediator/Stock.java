package ip.designpattern.behavioural.mediator;

public interface Stock {
	
	public StockMarket getStockMarket();

	public StockListedCompany getCompany();
	
	public String getStockId();
	
	public void setStockValue(double stockValue);
	
	public double getStockValue();
	
}
