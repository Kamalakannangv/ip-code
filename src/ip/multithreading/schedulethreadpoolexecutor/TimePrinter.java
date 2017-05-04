package ip.multithreading.schedulethreadpoolexecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TimePrinter {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	private Random rand = new Random();
	
	public String printTime(){
		System.out.print(sdf.format(new Date()));
		int randNumber = rand.nextInt(100) % 4;
		int secs = 8 + randNumber;
		System.out.print("\t" + secs);
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String finishingTime = sdf.format(new Date());
		System.out.println("\t" + finishingTime);
		return finishingTime;
	}

}
