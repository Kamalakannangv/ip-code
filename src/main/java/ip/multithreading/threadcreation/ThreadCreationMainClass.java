package ip.multithreading.threadcreation;

public class ThreadCreationMainClass {
	
	public static void main(String[] args) {
		Runnable task = new RunnableClass();
		
		Thread thread = new Thread(task, "RunnableThread");
		thread.start();
		
		Thread threadSubClass = new ThreadSubClass("SubClassingThread");
		threadSubClass.start();
		
		Thread threadSubClassWithRunnable = new ThreadSubClass(task, "Runnable&SubClassingThread");
		threadSubClassWithRunnable.start();
		
	}

}
