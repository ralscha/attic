package ch.ess.speedsend;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
  private static FileService fileService = new FileService();

  public static void main(String[] args) {
    try {
      FileServiceInterface stub = (FileServiceInterface)UnicastRemoteObject.exportObject(fileService, 0);

      // Bind the remote object's stub in the registry)

      Registry registry = LocateRegistry.createRegistry(3456);
      registry.bind("FileService", stub);

      System.err.println("Server ready");

    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }

}
