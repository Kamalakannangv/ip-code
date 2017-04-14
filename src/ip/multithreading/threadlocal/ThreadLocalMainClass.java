package ip.multithreading.threadlocal;

public class ThreadLocalMainClass {

	public static void main(String[] args) {
		Task task = new Task();
		Thread thread1 = new Thread(task, "NewThread");
		thread1.start();
	}
	
}
