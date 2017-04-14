package ip.multithreading.waitnotify;

public class ProducerTask implements Runnable {
	
	private MessageCarringChannel channel;
	
	public ProducerTask(MessageCarringChannel channel) {
		this.channel = channel;
	}
	
	private int counter = 0;

	@Override
	public void run() {
		String runningThreadName = Thread.currentThread().getName();
		while(true){
			try {
				System.out.println("Producer thread:" + runningThreadName + " started producing task");
				counter++;
				String message = "Task-"+counter;
				Thread.sleep(3000);
				synchronized (channel) {
					channel.setMessage(message);
					System.out.println("Producer thread:" + runningThreadName + " notifying consumer thread for the task");
					System.out.println("\n\n");
					channel.notifyAll();
				}
				if(counter == 5){
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
