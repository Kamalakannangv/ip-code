package ip.multithreading.excutorservice;

public interface SuperExecutorService {
	
	public WordCounterResult process();
	
	public void shutDownService();

}
