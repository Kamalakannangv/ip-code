package ip.multithreading.semaphore;

public class SemaphoreMainClass {
	
	public static void main(String[] args) {
		String[] wordPool = {"zero","one","two","three","four","five","six","seven","eight","nine"};
		PrinterResourcePool pool = new PrinterResourcePool(2);
		Task task = new Task(pool, wordPool);
		Thread threadOne = new Thread(task, "FirstThread");
		Thread threadTwo = new Thread(task, "SecondThread");
		Thread threadThree = new Thread(task, "ThirdThread");
		Thread threadFour = new Thread(task, "FourthThread");
		threadOne.start();
		threadTwo.start();
		threadThree.start();
		threadFour.start();
	}

}
