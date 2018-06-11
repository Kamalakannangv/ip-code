package ip.designpattern.creational.singleton;

/**
 * When this singleton class is loaded, SingletonHelper class is not loaded into memory and only when someone 
 * calls the getInstance method, this class gets loaded and creates the Singleton class instance
 */
public class E_BillPughSingleton {

    private E_BillPughSingleton(){}
    
    private static class SingletonHelper{
        private static final E_BillPughSingleton INSTANCE = new E_BillPughSingleton();
    }
    
    public static E_BillPughSingleton getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
