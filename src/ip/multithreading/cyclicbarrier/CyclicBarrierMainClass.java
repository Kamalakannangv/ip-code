package ip.multithreading.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class CyclicBarrierMainClass {
	
	public static void main(String[] args) {
		
		int[][] building1 = new int[][]{
			{8, 2, 4, 5, 0, 9},
			{3, 6, 1, 9, 3, 7, 8, 1, 8, 5},
			{5, 6, 8, 3, 5, 9, 2, 6, 8},
			{8, 5, 2, 8, 3, 6, 9, 3, 1, 0}
		};
		int[][] building2 = new int[][]{
			{8, 5, 2, 8, 3, 6, 9, 3, 1, 0},
			{3, 9, 3, 7, 8, 5},
			{8, 2, 4, 5, 7, 2, 5, 3, 8, 3, 0, 9},
			{5, 8, 3, 8, 5, 2, 6, 8}
		};
		int[][] building3 = new int[][]{
			{3, 6, 1, 7, 8, 1, 9, 2, 4, 0 , 1, 9, 5},
			{5, 6, 8, 3, 4, 2, 7, 3, 6, 4, 9, 2, 8},
			{1, 4, 2, 6, 8, 6, 2, 5, 9, 3, 1, 0}
		};
		
		TotalingTask totalingTask = new TotalingTask();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3, totalingTask);
		CyclicBarrierResetter cyclicBarrierResetFlag = new CyclicBarrierResetter();
		CountingTask countingTaskBldg1 = new CountingTask(building1, 4, cyclicBarrier, 1, cyclicBarrierResetFlag);
		CountingTask countingTaskBldg2 = new CountingTask(building2, 4, cyclicBarrier, 2, cyclicBarrierResetFlag);
		CountingTask countingTaskBldg3 = new CountingTask(building3, 4, cyclicBarrier, 3, cyclicBarrierResetFlag);
		List<CountingTask> countingTasks = new ArrayList<>();
		countingTasks.add(countingTaskBldg1);
		countingTasks.add(countingTaskBldg2);
		countingTasks.add(countingTaskBldg3);
		totalingTask.setList(countingTasks);
		Thread countingThread1 = new Thread(countingTaskBldg1);
		Thread countingThread2 = new Thread(countingTaskBldg2);
		Thread countingThread3 = new Thread(countingTaskBldg3);
		countingThread1.start();
		countingThread2.start();
		countingThread3.start();
	}

}
