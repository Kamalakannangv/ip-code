package ip.multithreading.daemonthread;

public class Task implements Runnable {

	@Override
	public void run() {
		int counter = 0;
		while(true){
			try {
				Thread.sleep(1000);
				System.out.println("Thread: "+Thread.currentThread().getName()+" Counter: "+counter++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				System.out.println("Finally block in "+Thread.currentThread().getName()+" thread");
			}
		}
	}

}
