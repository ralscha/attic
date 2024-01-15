

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Auctioneer extends Remote {
    Bid[] getCurrentBids() throws RemoteException;
    void updateBid(Bid b) throws RemoteException, BidException;
    void addBidder(Bidder b) throws RemoteException;
    void removeBidder(Bidder b) throws RemoteException;
}