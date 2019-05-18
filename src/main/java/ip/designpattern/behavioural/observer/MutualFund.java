package ip.designpattern.behavioural.observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class MutualFund implements Comparable<MutualFund>{
	
	private String mutualFundName;
	private Set<String> stockIds = new TreeSet<>();
	private Map<String, StockDisplayState> stockCollection = new HashMap<>();

	public MutualFund(String mutualFundName, Set<String> stockIds) {
		this.mutualFundName = mutualFundName;
		this.stockIds.addAll(stockIds);
	}

	public String getMutualFundName() {
		return mutualFundName;
	}
	
	public Set<String> getStockIds() {
		return stockIds;
	}

	public Map<String, StockDisplayState> getStockCollection() {
		return stockCollection;
	}
	
	public boolean udpate(String stockId, double stockValue){
		StockDisplayState stockDisplayState = stockCollection.get(stockId);
		if(stockIds.contains(stockId)){
			if(stockDisplayState == null){
				stockDisplayState = new StockDisplayState(stockId, stockValue);
				stockCollection.put(stockId, stockDisplayState);
			}else{
				stockDisplayState.setStockValue(stockValue);
			}
			return true;
		}
		return false;
	}
	
	public double getTotalValue(){
		Iterator<StockDisplayState> stockIterator = stockCollection.values().iterator();
		double totalValue = 0;
		while(stockIterator.hasNext()){
			StockDisplayState stock = stockIterator.next();
			totalValue = totalValue + stock.getStockValue();
		}
		return totalValue;
	}
	
	class StockDisplayState{
		private String stockId;
		private double stockValue;
		
		public StockDisplayState(String stockId, double stockValue) {
			this.stockId = stockId;
			this.stockValue = stockValue;
		}

		public String getStockId() {
			return stockId;
		}

		public double getStockValue() {
			return stockValue;
		}

		public void setStockValue(double stockValue) {
			this.stockValue = stockValue;
		}
		
	}

	@Override
	public int compareTo(MutualFund o) {
		return mutualFundName.compareTo(o.mutualFundName);
	}
}
