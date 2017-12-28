package ip.multithreading.cyclicbarrier;

public class CyclicBarrierResetter {
	
	private int count = 0;
	
	public void reset(){
		count = 0;
	}

	public void increment(){
		count++;
	}
	
	public int getValue(){
		return count;
	}

}
