package ip.designpattern.creational.singleton;

/**
 * Lazy initialization method to implement Singleton pattern creates the instance in the global access method. It 
 * works fine in case of single threaded environment but in multithreaded environment its not thread-safe. 
 */
public class C_LazyInitializedSingleton {

    private static C_LazyInitializedSingleton instance;
    
    private C_LazyInitializedSingleton(){}
    
    public static C_LazyInitializedSingleton getInstance(){
    	/*
    	More than one instance will be created if multiple threads are inside the below if loop in 
    	multithreaded system
    	*/
        if(instance == null){
            instance = new C_LazyInitializedSingleton();
        }
        return instance;
    }
}
