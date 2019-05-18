package ip.designpattern.behavioural.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ip.designpattern.behavioural.observer.MutualFund.StockDisplayState;

public class MutualFundManager implements StockObserver {
	
	private String mutualFundManager;
	
	public Set<MutualFund> mutualFundList = new TreeSet<>();
	
	public MutualFundManager(String mutualFundManager) {
		this.mutualFundManager = mutualFundManager;
	}
	
	public String getMutualFundManager() {
		return mutualFundManager;
	}

	public void addMutualFunds(MutualFund mutualFund){
		synchronized (mutualFundList) {
			mutualFundList.add(mutualFund);
			Iterator<Stock> stockIter = StockMarket.getInstance().getFullStockList().iterator();
			while(stockIter.hasNext()){
				Stock stock = stockIter.next();
				Iterator<String> mutualFundStockIter = mutualFund.getStockIds().iterator();
				while(mutualFundStockIter.hasNext()){
					String mutualFundStock = mutualFundStockIter.next();
					if(mutualFundStock.equalsIgnoreCase(stock.getStockId())){
						stock.attach(this);
					}
				}
				
			}
		}
	}


	@Override
	public void update(Object event) {
		if(event instanceof Stock){
			Stock stock = (Stock)event;
			boolean update = false;
			for(MutualFund mutualFund : mutualFundList){
				if(mutualFund.udpate(stock.getStockId(), stock.getStockValue())){
					update = true;
				}
			}
			if(update){
				synchronized (mutualFundList) {
					display();
				}
			}
		}
		
	}
	
	private void display(){
		System.out.println("\n\n\n|***********************************************************************************************|");
		System.out.println("|********************************* " + mutualFundManager +" Mutual Fund stock values  *******************************|");
		System.out.println("|***********************************************************************************************|");
		List<Integer> firstHeaderColumnWidth = new ArrayList<>();
		List<Object> firstHeaderDashData = new ArrayList<>();
		List<Object> firstHeaderSpaceData = new ArrayList<>();
		List<Object> firstHeaderTitleData = new ArrayList<>();
		List<Integer> secondHeaderColumWidth = new ArrayList<>();
		List<Object> secondeaderSpaceData = new ArrayList<>();
		List<Object> secondHeaderTitleData = new ArrayList<>();
		List<Object> secondHeaderDashData = new ArrayList<>();
		List<Object> dataMFtotalData = new ArrayList<>();
		List<Object> dataMFtotalDash = new ArrayList<>();
		for(MutualFund mf : mutualFundList){
			firstHeaderColumnWidth.add(1);
			firstHeaderColumnWidth.add(27);
			firstHeaderColumnWidth.add(1);
			firstHeaderColumnWidth.add(5);
			firstHeaderDashData.add("|");
			firstHeaderDashData.add("---------------------------");
			firstHeaderDashData.add("|");
			firstHeaderDashData.add(" ");
			firstHeaderSpaceData.add("|");
			firstHeaderSpaceData.add("");
			firstHeaderSpaceData.add("|");
			firstHeaderSpaceData.add(" ");
			firstHeaderTitleData.add("|");
			firstHeaderTitleData.add(mf.getMutualFundName());
			firstHeaderTitleData.add("|");
			firstHeaderTitleData.add(" ");
			secondHeaderColumWidth.add(1);
			secondHeaderColumWidth.add(4);
			secondHeaderColumWidth.add(1);
			secondHeaderColumWidth.add(10);
			secondHeaderColumWidth.add(1);
			secondHeaderColumWidth.add(11);
			secondHeaderColumWidth.add(1);
			secondHeaderColumWidth.add(5);
			secondeaderSpaceData.add("|");
			secondeaderSpaceData.add("");
			secondeaderSpaceData.add("|");
			secondeaderSpaceData.add("");
			secondeaderSpaceData.add("|");
			secondeaderSpaceData.add("");
			secondeaderSpaceData.add("|");
			secondeaderSpaceData.add(" ");
			secondHeaderTitleData.add("|");
			secondHeaderTitleData.add("No.");
			secondHeaderTitleData.add("|");
			secondHeaderTitleData.add("Stock Code");
			secondHeaderTitleData.add("|");
			secondHeaderTitleData.add("Stock Value");
			secondHeaderTitleData.add("|");
			secondHeaderTitleData.add(" ");
			secondHeaderDashData.add("|");
			secondHeaderDashData.add("----------");
			secondHeaderDashData.add("|");
			secondHeaderDashData.add("--------------------");
			secondHeaderDashData.add("|");
			secondHeaderDashData.add("-----------------------");
			secondHeaderDashData.add("|");
			secondHeaderDashData.add(" ");
			
			dataMFtotalData.add("|");
			dataMFtotalData.add("");
			dataMFtotalData.add("|");
			dataMFtotalData.add("Total: ");
			dataMFtotalData.add("|");
			dataMFtotalData.add(mf.getTotalValue());
			dataMFtotalData.add("|");
			dataMFtotalData.add(" ");
			
			dataMFtotalDash.add("|");
			dataMFtotalDash.add("");
			dataMFtotalDash.add("|");
			dataMFtotalDash.add("");
			dataMFtotalDash.add("|");
			dataMFtotalDash.add("-----------------");
			dataMFtotalDash.add("|");
			dataMFtotalDash.add(" ");
		}
		List<List<Object>> firstHeaderData = new ArrayList<>();
		firstHeaderData.add(firstHeaderDashData);
		firstHeaderData.add(firstHeaderSpaceData);
		firstHeaderData.add(firstHeaderTitleData);
		firstHeaderData.add(firstHeaderSpaceData);
		firstHeaderData.add(firstHeaderDashData);
		DisplayUtil.display(firstHeaderColumnWidth, firstHeaderData);
		List<List<Object>> secondHeaderData = new ArrayList<>();
		secondHeaderData.add(secondeaderSpaceData);
		secondHeaderData.add(secondHeaderTitleData);
		secondHeaderData.add(secondeaderSpaceData);
		secondHeaderData.add(secondHeaderDashData);
		int dataRow = 0;
		List<Object> data = null;
		while(true){
			boolean haveNewRow = false;
			data = new ArrayList<>();
			for(MutualFund mf : mutualFundList){
				Object[] stockArry = mf.getStockCollection().values().toArray();
				if(stockArry.length > dataRow){
					StockDisplayState stockDisplayState = (StockDisplayState)mf.getStockCollection().values().toArray()[dataRow];
					data.add("|");
					data.add(dataRow + 1);
					data.add("|");
					data.add(stockDisplayState.getStockId());
					data.add("|");
					data.add(stockDisplayState.getStockValue());
					data.add("|");
					data.add(" ");
					haveNewRow = true;
				}else{
					data.add("|");
					data.add("");
					data.add("|");
					data.add("");
					data.add("|");
					data.add("");
					data.add("|");
					data.add(" ");
				}
			}
			if(haveNewRow){
				secondHeaderData.add(data);
			}else{
				break;
			}
			dataRow++;
		}
		secondHeaderData.add(dataMFtotalDash);
		secondHeaderData.add(dataMFtotalData);
		secondHeaderData.add(secondHeaderDashData);
		DisplayUtil.display(secondHeaderColumWidth, secondHeaderData);
	}
	
}
