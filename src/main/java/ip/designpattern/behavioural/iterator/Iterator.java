package ip.designpattern.behavioural.iterator;

public interface Iterator<E> {
	
	public boolean hasNext();
	
	public E next();
	
	public E current();

}
