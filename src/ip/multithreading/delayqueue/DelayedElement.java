package ip.multithreading.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedElement implements Delayed {
	
	private Integer time;
	private String name;
	Thread t;
	
	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}
	
	public void setTime(Integer time) {
		this.time = time;
	}

	public DelayedElement(int time, String name) {
		this.time = time;
		this.name = name;
		t = new Thread(new Task(this));
		t.start();
	}

	@Override
	public int compareTo(Delayed o) {
		return new Long(getDelay(TimeUnit.NANOSECONDS)).compareTo(((DelayedElement)o).getDelay(TimeUnit.NANOSECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		if(unit.equals(TimeUnit.NANOSECONDS)){
			//System.out.println(name + " - " + time);
			return time;
		}
		return 0;
	}

}
