package RMITest;

    public interface HobbyInterface extends java.rmi.Remote
    {
        public String recommendHobby(String n, int age) throws java.rmi.RemoteException;
    }