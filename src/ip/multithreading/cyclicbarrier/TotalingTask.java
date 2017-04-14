package ip.multithreading.cyclicbarrier;

import java.util.List;

public class TotalingTask implements Runnable {
	
	private List<CountingTask> list;
	private int totalCount = 0;
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setList(List<CountingTask> list) {
		this.list = list;
	}

	@Override
	public void run() {
		int floorWiseTotal = 0;
		for(int i = 0; i < list.size(); i++){
			floorWiseTotal = floorWiseTotal + list.get(i).getFloorTotal();
		}
		System.out.println("Floor Total : " + floorWiseTotal);
		totalCount = totalCount + floorWiseTotal;
		System.out.println("Total count till now : " + totalCount);
	}

}
