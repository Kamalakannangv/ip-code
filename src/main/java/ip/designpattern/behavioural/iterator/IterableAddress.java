package ip.designpattern.behavioural.iterator;

public interface IterableAddress<E> {
	
	public Iterator<E> createIterator();
	
}
