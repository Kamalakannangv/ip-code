package ip.designpattern.creational.singleton;

/**
 * The easier way to create a thread-safe singleton class is to make the global access method synchronized, so 
 * that only one thread can execute this method at a time.
 */
public class D_ThreadSafeSingleton {

	private static D_ThreadSafeSingleton instance;
    
    private D_ThreadSafeSingleton(){}
    
    /*
     * Only one thread can access this method at a time but it reduces the performance because of cost 
     * associated with the synchronized method. 
    */
    public static synchronized D_ThreadSafeSingleton getInstance(){
        if(instance == null){
            instance = new D_ThreadSafeSingleton();
        }
        return instance;
    }
    
    /*
     * To avoid this extra overhead every time associated with above method, double checked locking principle 
     * is used. In this approach, the synchronized block is used inside the if condition with an additional check 
     * to ensure that only one instance of singleton class is created.
    */
    public static D_ThreadSafeSingleton getInstanceUsingDoubleLocking(){
        if(instance == null){
            synchronized (D_ThreadSafeSingleton.class) {
                if(instance == null){
                    instance = new D_ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}
