public final class RessourceManager {

    private static final RessourceManager instance = new RessourceManager();
    
    private boolean available;
    
    public static RessourceManager getInstance() {
        return instance;
    }
    
    public synchronized void getLock() {
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
    }
          
    public synchronized void releaseLock() {
        available = true;
        notifyAll();
    } 

    protected RessourceManager() {
        available = true;
    }
 
 
 
}