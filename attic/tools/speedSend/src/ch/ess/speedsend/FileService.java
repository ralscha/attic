package ch.ess.speedsend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileService implements FileServiceInterface {

  public boolean fileExists(String fileName) {
    File f = new File(fileName);
    return f.exists();
  }
  
  public String getHashCode(String fileName) throws RemoteException {
    try {
      return Util.getHashcode(fileName);
      
    } catch (NoSuchAlgorithmException e) {
      throw new RemoteException("getHashCode", e);
    } catch (FileNotFoundException e) {
      throw new RemoteException("getHashCode", e);
    } catch (IOException e) {
      throw new RemoteException("getHashCode", e);
    }
    
    
    
  }

  private Map<Integer, FileOutputStream> streams = Collections.synchronizedMap(new HashMap<Integer, FileOutputStream>());
  

  public int openFile(String fileName) throws RemoteException {
    try {      
      FileOutputStream bos = new FileOutputStream(fileName + "_TRANSFER");
      int handle = streams.size();
      streams.put(handle, bos);
      return handle;
    } catch (IOException e) {
      throw new RemoteException("openFile", e);
    }    
  }

  public void write(int handle, byte[] buffer, int off, int len) throws RemoteException {
    try {
      streams.get(handle).write(buffer, off, len);
    } catch (IOException e) {
      throw new RemoteException("write", e);
    }

  }

  public void closeFile(int handle, String fileName) throws RemoteException {
    try {
      streams.get(handle).close();
      streams.remove(handle);
      
      //Altes File löschen, neues umbenennen
      File oldFile = new File(fileName);
      oldFile.delete();
      
      File newFile = new File(fileName + "_TRANSFER");
      newFile.renameTo(oldFile);
      
    } catch (IOException e) {
      throw new RemoteException("closeFile", e);
    }

  }

  public void closeAndDeleteFile(int handle, String fileName) throws RemoteException {
    //Bei einem Abbruch
    try {
      streams.get(handle).close();
      streams.remove(handle);
            
      File file = new File(fileName + "_TRANSFER");
      file.delete();
      
    } catch (IOException e) {
      throw new RemoteException("closeFile", e);
    }

  }
  
  public boolean applyPatch(String oldFileName, String newFileNamep, String patchFileName) throws RemoteException {
    
    boolean rename = false;
    
    String newFileName;
    
    if (newFileNamep == null) {
      newFileName = oldFileName + "_NEW";
      rename = true;
    } else {
      newFileName = newFileNamep;
    }
    
    File patch = new File(patchFileName);
    if (patch.exists()) {
      try {
        File workDir = new File("bsdiff");
        ProcessBuilder pb = new ProcessBuilder(workDir.getCanonicalPath() + "/bspatch.exe", 
            oldFileName, 
            newFileName, 
            patch.getCanonicalPath());
        pb.directory(workDir);
        pb.redirectErrorStream(true);
        
        Process p = pb.start();
        try {
          p.waitFor();
        } catch (InterruptedException e) {
          throw new RemoteException("waitFor", e);
        }
        
        if (rename) {
          //Wenn ok, Altes File löschen und umbenennen
          File oldFile = new File(oldFileName);
          oldFile.delete();
          
          File newFile = new File(newFileName);
          newFile.renameTo(oldFile);
        }
        
        //Patchfile löschen
        patch.delete();
        
        return true;
      } catch (IOException e) {
        throw new RemoteException("applyPatch", e);
      }
    }
    
    return false;
  }


}
