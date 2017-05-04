package ip.multithreading.lock;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Channel {
	
	private int capacity;
	private final Deque<Integer> channelPipe = new LinkedList<>();
	private final Lock lock = new ReentrantLock();
	private final Condition queueNotFull = lock.newCondition();
	private final Condition queueNotEmpty = lock.newCondition();
	
	public Channel(int capacity) {
		this.capacity = capacity;
	}
	
	public void put(Integer item){
		lock.lock();
		try{
			while(channelPipe.size() == capacity){
				System.out.println("Channel  -> Full, waiting for space --- Capacity : " + channelPipe.size());
				queueNotFull.await();
			}
			boolean itemAdded = channelPipe.offerFirst(item);
			System.out.println("Channel  -> " + "recieving Item:" + item + " --- Capacity : " + channelPipe.size());
			if(itemAdded){
				queueNotEmpty.signalAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public Integer take(){
		lock.lock();
		Integer item = null;
		try{
			while(channelPipe.size() == 0){
				System.out.println("Channel  -> Empty, waiting for item --- Capacity : " + channelPipe.size());
				queueNotEmpty.await();
			}
			item = channelPipe.removeLast();
			System.out.println("Channel  -> " + "sending Item:" + item + " --- Capacity : " + channelPipe.size());
			queueNotFull.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return item;
	}
	
}
