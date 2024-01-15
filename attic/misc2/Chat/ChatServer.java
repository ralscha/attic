import java.rmi.*;

public interface ChatServer extends Remote {
    public boolean login(String str) throws RemoteException;
    public boolean logout(String str) throws RemoteException;
    public void tell(String str, String m) throws RemoteException;
       
    public ChatServerEvent[] waitForEvent(String str) throws RemoteException;
}
