package ip.designpattern.creational.singleton;


/**
 * In eager initialization, the instance of Singleton Class is created at the time of class loading, this is the 
 * easiest method to create a singleton class but it has a drawback that instance is created even though client 
 * application might not be using it.
 */
public class A_EagerInitializedSingleton {
    
    private static final A_EagerInitializedSingleton instance = new A_EagerInitializedSingleton();
    
    //private constructor to avoid client applications to use constructor
    private A_EagerInitializedSingleton(){}

    public static A_EagerInitializedSingleton getInstance(){
        return instance;
    }
}
