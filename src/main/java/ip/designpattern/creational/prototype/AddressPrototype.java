package ip.designpattern.creational.prototype;

public interface AddressPrototype extends Cloneable{

	public AddressPrototype clone() throws CloneNotSupportedException;
	
}
