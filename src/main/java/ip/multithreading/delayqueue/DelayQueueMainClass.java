package ip.multithreading.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueMainClass {
	
	public static void main(String[] args) throws InterruptedException {
		
		DelayedElement e1 = new DelayedElement(6, "six");
		DelayedElement e2 = new DelayedElement(9, "nine");
		DelayedElement e3 = new DelayedElement(3, "three");
		DelayedElement e4 = new DelayedElement(0, "zero");
		DelayedElement e5 = new DelayedElement(4, "four");
		DelayQueue<DelayedElement> queue = new DelayQueue<>();
		queue.put(e1);
		queue.put(e2);
		queue.put(e3);
		queue.put(e4);
		queue.put(e5);
		while(!queue.isEmpty()){
			DelayedElement x = queue.poll();
			if(x == null){
				//System.out.println("Null");
				//Thread.sleep(400);
			}else{
				System.out.println(x.getName()+" " + x.getTime());
				System.out.println("Queue size : " + queue.size()+"\n");
			}
		}
	}

}
