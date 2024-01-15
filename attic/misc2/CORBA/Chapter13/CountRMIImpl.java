import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class CountRMIImpl extends UnicastRemoteObject implements CountRMI {

    private int sum;

    public CountRMIImpl(String name) throws RemoteException {
        super();
        try {
            Naming.rebind(name, this);
            sum = 0;
        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public int sum() throws RemoteException {
        return sum;
    }

    public void sum(int _val) throws RemoteException {
        sum = _val;
    }

    public int increment() throws RemoteException {
        sum++;
        return sum;
    }

}