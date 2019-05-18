package ip.designpattern.behavioural.mediator;

import java.util.ArrayList;
import java.util.List;

public abstract class StockAbservable {

	private List<StockObserver> stockObservers = new ArrayList<>();
	
	public void attach(StockObserver stockObserver) {
		if(!stockObservers.contains(stockObserver)){
			stockObservers.add(stockObserver);
		}
	}

	public boolean remove(StockObserver stockObserver) {
		return stockObservers.remove(stockObserver);
	}

	public void notifyObserver(Object event) {
		for(StockObserver observer: stockObservers){
			observer.update(event);
		}
	}
	
}
