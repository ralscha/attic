package ch.ess.speedsend;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServiceInterface extends Remote {
  
  String getHashCode(String fileName) throws RemoteException;
  boolean fileExists(String fileName) throws RemoteException;
  
  //transfer
  int openFile(String fileName) throws RemoteException;
  void write(int handle, byte[] buffer, int off, int len) throws RemoteException;
  void closeFile(int handle, String fileName) throws RemoteException;
  void closeAndDeleteFile(int handle, String fileName) throws RemoteException;
  
  boolean applyPatch(String oldFileName, String newFileName, String patchFileName) throws RemoteException;
}
