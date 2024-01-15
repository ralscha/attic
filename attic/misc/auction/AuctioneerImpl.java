import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.*;
import common.util.log.*;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;


public class AuctioneerImpl extends UnicastRemoteObject implements Auctioneer {
	Vector fClients = new Vector();
	Hashtable fProducts = new Hashtable();
	PooledExecutor pool = new PooledExecutor(5);
	ServerFrame serverFrame = new ServerFrame(this);

	public AuctioneerImpl(Bid products[]) throws RemoteException {
		//put products into hashtable for quick access
		for (int i = products.length - 1; i >= 0; i--) {
			fProducts.put(products[i].getId(), products[i]);
		}
	}

	/**
	  * Updates the bid for a product. This method performs client notification
	  * asyncrhonously so control is immediately returned to the client.
	  */
	public void updateBid(final Bid bid) throws BidException {
		//make sure the bid is greater than the current bid
		Bid b = (Bid) fProducts.get(bid.getId());

		Log.log("Old bid="+b.getCurrentBid() + ", new bid="+bid.getCurrentBid() + ", comparePrice()="+
         		bid.comparePrice(b));
		if (b == null) {
			Log.log("Could not find bid for " + bid);
			throw new BidException("Could not locate product: " + bid);
		} else if (bid.comparePrice(b) <= 0) {
			Log.log("New bid for " + b + " was not higher");
			throw new BidException("Bid is not higher than the current price");
		}

		fProducts.put(bid.getId(), bid);
		Log.log("Bid updated for " + bid + " to " + bid.getCurrentBid());

		if (fClients.size() == 0) {
			Log.log("No clients to notify of new bid");
			return;
		}
		try {
			pool.execute(new Runnable() {
             				public void run() {
             					sendBidToClients(bid);
             				}
             			}
            			);
		} catch (InterruptedException ie) { }
	}

	/**
	  * Iterates over all of the registered clients and sends each one
	  * the current bid. Any clients that cannot be successfully
	  * notified are removed from the list.
	  */
	void sendBidToClients(Bid bid) {
		Vector v = (Vector) fClients.clone();
		Bidder bidder = null;

		Log.log("Notifying clients of new bid for " + bid);
		for (int i = v.size() - 1; i >= 0; i--) {
			try {
				bidder = (Bidder) v.elementAt(i);
				bidder.updateBid(bid);

				//reset timeout so do not penalize next client for previous
				//time client's
			} catch (RemoteException ex) {
				//not valid anymore so remove it from client list
				fClients.removeElement(bidder);
			}
			catch (Exception ex) { /* ignore */
			}
		}
	}

	public Bid[] getCurrentBids() {
		Log.log("Returning current bids to client");

		int i = 0;
		Bid bids[] = new Bid[fProducts.size()];
		Enumeration e = fProducts.elements();

		while (e.hasMoreElements()) {
			bids[i++] = (Bid) e.nextElement();
		}

		return bids;
	}

	/**
	  * Adds a client from the list of clients to be notified.
	  */
	public void addBidder(Bidder b) {
		fClients.addElement(b);
		Log.log("Added new client");
	}

	/**
	  * Removes a client from the list of clients to be notified.
	  */
	public void removeBidder(Bidder b) {
		Log.log("Removing client");
		fClients.removeElement(b);
	}

	public void closing() {
		if (fClients.size() == 0)
			return;

		//shutdown notifications
		Log.log("Shut down requested by user");

		Log.log("Stopping callback threads...");
		pool.interruptAll();

		//notify all clients going away
		Log.log("Notifying all clients...");
		Vector v = (Vector) fClients.clone();
		Bidder b = null;
		for (int i = v.size() - 1; i >= 0; i--) {
			try {
				b = (Bidder) v.elementAt(i);
				b.serverShuttingDown();
			} catch (Exception ex) { /* ignore */
			}
		}

		Log.log("Shutdown complete");
	}

	public static void main(String args[]) throws Exception {
		Log.setLogger(new ConsoleLogger(400,200));
		String host = InetAddress.getLocalHost().getHostName();
		URL base = new URL("http://" + host + "/");
		Bid bids[] = { new Bid("ABC123", "Nice Watch", new URL(base, "watch.jpg"), 1.0),
               		new Bid("ZYX987", "Ugly Watch", new URL(base, "watch.jpg"), 1.0)};

		AuctioneerImpl auctioneer = new AuctioneerImpl(bids);
		Registry reg = LocateRegistry.createRegistry(1099);
		reg.rebind("SouthBay", auctioneer);

		Log.log("Standing by for bids...");
	}
}