package ip.multithreading.daemonthread;

public class DaemonThreadMainClass {
	
	public static void main(String[] args) throws InterruptedException {
		Task task1 = new Task();
		Thread thread1 = new Thread(task1, "TaskThread");
		thread1.setDaemon(true);
		thread1.start();
		Thread.sleep(10000);
		System.out.println("Thread - "+Thread.currentThread().getName()+" finished");
		
	}

}
