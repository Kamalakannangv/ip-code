package ip.multithreading.quest;

public class QuestMainClass {
	public static void main(String[] args) {
		ConcurrentlyAccessedClass object = new ConcurrentlyAccessedClass();
		Runnable threadOneTask = new Runnable() {
			public void run() {
				object.threadOneMethod();
			}
		};
		Runnable threadTwoTask = new Runnable() {
			public void run() {
				object.threadTwoMethod();
			}
		};
		Thread threadOne = new Thread(threadOneTask);
		Thread threadTwo = new Thread(threadTwoTask);
		threadOne.start();
		threadTwo.start();
		System.out.println("End");
	}
}

class ConcurrentlyAccessedClass {
	private int value = 0;
	private boolean result = false;
	public void threadOneMethod(){
		value = 1;
		result = true;
	}
	public void threadTwoMethod(){
		if(result){
			System.out.println("Value = " + value);
		}
	}
}

/**
 * 
 * Question: Can the thread "threadTwo" print "Value = 0"?
 * 
 * Answer: TODO
 *
 */