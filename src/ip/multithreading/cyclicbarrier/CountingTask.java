package ip.multithreading.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CountingTask implements Runnable {
	
	private CyclicBarrier cyclicBarrier;
	private int[][] building;
	private int maxFloor;
	private int floorTotal = 0;
	private int buildingNum;
	private CyclicBarrierResetter resetter;
	
	public int getFloorTotal() {
		return floorTotal;
	}

	public CountingTask(int[][] buildingData, int maxFloor, CyclicBarrier cyclicBarrier, int buildingNum, CyclicBarrierResetter resetter) {
		building = buildingData;
		this.maxFloor = maxFloor;
		this.cyclicBarrier = cyclicBarrier;
		this.buildingNum = buildingNum;
		this.resetter = resetter;
	}

	@Override
	public void run() {
		try {
			for(int floorCounter = 0; floorCounter < maxFloor; floorCounter++){
				floorTotal = 0;
				if(floorCounter < building.length){
					for(int houseCounter = 0 ; houseCounter < building[floorCounter].length; houseCounter++){
						floorTotal = floorTotal + building[floorCounter][houseCounter];
						Thread.sleep(250);
					}
				}
				System.out.println("Building No: " + buildingNum + " Floor No: " + floorCounter + " Total: " + floorTotal);
				System.out.println("Task for Building No: " + buildingNum + " waiting for other tasks");
				cyclicBarrier.await();
				synchronized (resetter) {
					if(resetter.getValue() == 0){
						cyclicBarrier.reset();
					}
					resetter.increment();;
					if(resetter.getValue() == 3){
						resetter.reset();
					}
				}
				System.out.println("Task for Building No: " + buildingNum + " started counting next floor...");
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("Task for building: " + buildingNum + " completed");
	}
}
