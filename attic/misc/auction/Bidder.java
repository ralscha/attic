

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bidder extends Remote {
    void updateBid(Bid b) throws RemoteException;
    void serverShuttingDown() throws RemoteException;
}
    