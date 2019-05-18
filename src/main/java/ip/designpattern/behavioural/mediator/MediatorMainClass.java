package ip.designpattern.behavioural.mediator;

import java.util.ArrayList;
import java.util.List;

public class MediatorMainClass {
	
	public static void main(String[] args) {
		StockExchange stockMarket = new StockExchange("NSE");
		
		List<String> stockIds = new ArrayList<>();		
		StockMarketUser dxc = new StockListedCompany("DXC Technology", stockMarket, 10);
		stockMarket.register(dxc);
		stockIds.add(((StockListedCompany)dxc).getStockId());
		StockMarketUser infy = new StockListedCompany("Infosys Limited", stockMarket, 10);
		stockMarket.register(infy);
		stockIds.add(((StockListedCompany)infy).getStockId());
		StockMarketUser tcs = new StockListedCompany("TCS Limited", stockMarket, 10);
		stockMarket.register(tcs);
		stockIds.add(((StockListedCompany)tcs).getStockId());
		StockMarketUser sbi = new StockListedCompany("State Bank of India", stockMarket, 10);
		stockMarket.register(sbi);
		stockIds.add(((StockListedCompany)sbi).getStockId());
		StockMarketUser bhel = new StockListedCompany("BHEL Gov. Limited", stockMarket, 10);
		stockMarket.register(bhel);
		stockIds.add(((StockListedCompany)bhel).getStockId());
		
		StockBroker stockBroker = new StockBroker("ABC Stock Brokers", stockMarket);
		stockBroker.startMonitoring(stockIds);
		
		StockHolder kamal = new StockHolder("Kamal", stockMarket, 2000);
		kamal.startTrading();
		
		StockHolder kishanth = new StockHolder("Kishanth", stockMarket, 2000);
		kishanth.startTrading();
	}

}
