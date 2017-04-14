package ip.multithreading.customlock;

public class Task implements Runnable {
	
	private Synchronizer synchronizer;
	
	public Task(Synchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}
	
	@Override
	public void run() {
		try {
			synchronizer.doSynchronized();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
