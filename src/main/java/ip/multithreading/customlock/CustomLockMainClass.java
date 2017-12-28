package ip.multithreading.customlock;

public class CustomLockMainClass {
	
	public static void main(String[] args) {
		Synchronizer synchronizer = new Synchronizer();
		Task task = new Task(synchronizer);
		Thread thread1 = new Thread(task, "Thread1");
		Thread thread2 = new Thread(task, "Thread2");
		thread1.start();
		thread2.start();
	}

}
