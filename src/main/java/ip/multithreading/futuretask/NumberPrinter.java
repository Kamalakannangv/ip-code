package ip.multithreading.futuretask;

public class NumberPrinter implements Runnable {

	@Override
	public void run() {
		int i = 0; 
		try {
			while(true){
				Thread.sleep(1000);
				System.out.println(++i);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread execution interrupted");
			//e.printStackTrace();
		}

	}

}
