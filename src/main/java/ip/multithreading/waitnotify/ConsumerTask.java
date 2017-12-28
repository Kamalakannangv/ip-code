package ip.multithreading.waitnotify;

public class ConsumerTask implements Runnable{
	
	private MessageCarringChannel channel;

	public ConsumerTask(MessageCarringChannel channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		String runningThreadName = Thread.currentThread().getName();
		while(true){
			synchronized (channel) {
				try {
					System.out.println("Consurmer thread: " + runningThreadName + " waiting to get notified by producer thread");
					channel.wait();
					System.out.println("Consurmer thread:" + runningThreadName + " notified by producer thread");
					if(channel.getMessage() != null){
						System.out.println("Consurmer thread:" + runningThreadName + " consuming task.... " + channel.getMessage());
						channel.setMessage(null);
						Thread.sleep(10000);
					}else{
						System.out.println("Consurmer thread:" + runningThreadName + " waiting for another task.... ");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

}
