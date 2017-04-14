package ip.multithreading.delayqueue;

public class Task implements Runnable {
	
	private DelayedElement element;
	
	public Task(DelayedElement element) {
		this.element = element;
	}

	@Override
	public void run() {
		while(element.getTime() >=0){
			element.setTime(element.getTime() -1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
