package ip.multithreading.runnablefuturesubmit;

public class RunnableTask implements Runnable {

	@Override
	public void run() {
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Task completed successfully....");

	}

}
