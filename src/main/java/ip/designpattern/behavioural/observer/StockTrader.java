package ip.designpattern.behavioural.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class StockTrader implements StockObserver {
	
	private String traderName;
	private Map<String, StockDisplayState> monitoringStockDisplayStates = new HashMap<>();
	
	public StockTrader(String traderName, List<String> stockInitialList) {
		this.traderName = traderName;
		for(String stockCode : stockInitialList){
			startMonitoring(stockCode);
		}
	}
	
	public void startMonitoring(String stockCode){
		Iterator<Stock> stockIter = StockMarket.getInstance().getFullStockList().iterator();
		while(stockIter.hasNext()){
			Stock stock = stockIter.next();
			if(stockCode.equalsIgnoreCase(stock.getStockId())){
				stock.attach(this);
				monitoringStockDisplayStates.put(stockCode, new StockDisplayState(stock.getCompanyName(), stock.getStockId(), stock.getStockValue()));
			}
		}
	}
	
	@Override
	public void update(Object event) {
		if(event instanceof Stock){
			Stock stock = (Stock)event;
			StockDisplayState stockDisplayState = monitoringStockDisplayStates.get(stock.getStockId());
			if(stockDisplayState != null){
				stockDisplayState.setStockValue(stock.getStockValue());
				display();	
			}
		}
	}
	
	private void display(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n|***********************************************|");
		System.out.println("|********** " + traderName + " Trader stock values  ***********|");
		System.out.println("|***********************************************|");
		System.out.println("|----|--------------------|----------|----------|");
		List<Integer> columnWidth = new ArrayList<>();
		columnWidth.add(1);
		columnWidth.add(4);
		columnWidth.add(1);
		columnWidth.add(20);
		columnWidth.add(1);
		columnWidth.add(10);
		columnWidth.add(1);
		columnWidth.add(10);
		columnWidth.add(1);
		List<List<Object>> headerData = new ArrayList<>();
		List<Object> zeroHeaderRow = new ArrayList<>();
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		zeroHeaderRow.add("");
		zeroHeaderRow.add("|");
		headerData.add(zeroHeaderRow);
		List<Object> headerRow = new ArrayList<>();
		headerRow.add("|");
		headerRow.add("No.");
		headerRow.add("|");
		headerRow.add("Company Name");
		headerRow.add("|");
		headerRow.add("Stock Code");
		headerRow.add("|");
		headerRow.add("Stock Value");
		headerRow.add("|");
		headerData.add(headerRow);
		List<Object> secondHeaderRow = new ArrayList<>();
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		secondHeaderRow.add("");
		secondHeaderRow.add("|");
		headerData.add(secondHeaderRow);
		DisplayUtil.display(columnWidth, headerData);
		System.out.println("|----|--------------------|----------|----------|");
		
		Iterator<StockDisplayState> stockIter = monitoringStockDisplayStates.values().iterator();
		int stockCount = 1;
		List<List<Object>> valueData = new ArrayList<>();
		while(stockIter.hasNext()){
			StockDisplayState stockDisplayState = stockIter.next();
			List<Object> singleRowData = new ArrayList<>();
			singleRowData.add("|");
			singleRowData.add(stockCount);
			singleRowData.add("|");
			singleRowData.add(stockDisplayState.getCompanyName());
			singleRowData.add("|");
			singleRowData.add(stockDisplayState.getStockCode());
			singleRowData.add("|");
			singleRowData.add(stockDisplayState.getStockValue());
			singleRowData.add("|");
			valueData.add(singleRowData);
			stockCount++;
		}
		DisplayUtil.display(columnWidth, valueData);
		System.out.println("|----|--------------------|----------|----------|");
	}
	
	
	private class StockDisplayState{
		
		private String companyName;
		private String stockCode;
		private double stockValue;
		
		public StockDisplayState(String companyName, String stockCode, double stockValue) {
			this.companyName = companyName;
			this.stockCode = stockCode;
			this.stockValue = stockValue;
		}

		public String getCompanyName() {
			return companyName;
		}

		public String getStockCode() {
			return stockCode;
		}

		public double getStockValue() {
			return stockValue;
		}

		public void setStockValue(double stockValue) {
			this.stockValue = stockValue;
		}
		
	}
	
}
