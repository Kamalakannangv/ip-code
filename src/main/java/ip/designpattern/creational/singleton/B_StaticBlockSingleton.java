package ip.designpattern.creational.singleton;

/**
 * Static block initialization implementation is similar to eager initialization, except that instance of 
 * class is created in the static block that provides option for exception handling.
 */
public class B_StaticBlockSingleton {

	private static B_StaticBlockSingleton instance;

	private B_StaticBlockSingleton(){}

	//static block initialization for exception handling
	static{
		try{
			instance = new B_StaticBlockSingleton();
		}catch(Exception e){
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	public static B_StaticBlockSingleton getInstance(){
		return instance;
	}
}
