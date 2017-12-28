package ip.multithreading.customlock;

public class Synchronizer{
	Lock lock = new Lock();

	public void doSynchronized() throws InterruptedException{
		this.lock.lock();
		Thread.sleep(1000);
		this.lock.unlock();
	}

}
