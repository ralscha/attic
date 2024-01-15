package ch.ess.speedsend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.swing.SwingWorker;

public class TransferWorker extends SwingWorker<String, String> {

  public static int BUFFER_SIZE = 8192;

  private SpeedSendClient client;
  private FileServiceInterface fileService;    
  
  private String host;
  private String localFileName;
  private String remoteCompareFileName;
  private String remoteFileName;

  public void setClient(SpeedSendClient client) {
    this.client = client;
  }


  public void setHost(String host) {
    this.host = host;
  }

  public void setLocalFileName(String localFileName) {
    this.localFileName = localFileName;
  }

  public void setRemoteCompareFileName(String remoteCompareFileName) {
    this.remoteCompareFileName = remoteCompareFileName;
  }

  public void setRemoteFileName(String remoteFileName) {
    this.remoteFileName = remoteFileName;
  }

  @Override
  protected String doInBackground() throws Exception {
    
    if (remoteFileName == null) {
      remoteFileName = remoteCompareFileName;
    }
    
    Registry registry = LocateRegistry.getRegistry(host, 3456);
    fileService = (FileServiceInterface)registry.lookup("FileService");
    speedSend();
    fileService = null;
    publish("Fertig");
    return null;
  }

  @Override
  protected void process(List<String> chunks) {
    for (String val : chunks) {
      client.getRunningStatusTextArea().append(val);
      client.getRunningStatusTextArea().append("\n");
    }
  }

  @Override
  protected void done() {
    setProgress(100);
    client.getStatusLabel().setText("Ready");
    client.enableAll();

  }

  public void speedSend()
      throws RemoteException, FileNotFoundException, IOException, NoSuchAlgorithmException {

    String localHashCode = Util.getHashcode(localFileName);

    if (fileService.fileExists(remoteCompareFileName)) {
      String remoteHashCode = fileService.getHashCode(remoteCompareFileName);

      if (!localHashCode.equals(remoteHashCode)) {
        //Files sind Local und Remote unterschiedlich    
        
        //Prüfen wir zuerst noch den remoteFileNamen ob der eventuell schon gleich ist
        String tmpHashCode = null;
        
        try {
          fileService.getHashCode(remoteFileName);
        } catch (Exception e) {
          //no action here
        }
        
        if (!localHashCode.equals(tmpHashCode)) {
          //Nein dann übertragen
        
          //Gibt es für den RemoteHashCode eine lokale Kopie?
          File archiv = new File("archiv", remoteHashCode);
          if (archiv.exists()) {
            //ja dann patch erstellen
            publish("Lokale Kopie vorhanden");
            publish("Erstelle Patch File");
            File patch = new File(localHashCode);
            
            File workDir = new File("bsdiff");
            ProcessBuilder pb = new ProcessBuilder(workDir.getCanonicalPath() + "/bsdiff.exe", archiv.getCanonicalPath(),
                localFileName, patch.getCanonicalPath());
            pb.directory(workDir);
            Process p = pb.start();
            try {
              p.waitFor();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
  
            String patchFileLength;
            if (patch.length() > 1024) {
              patchFileLength = (patch.length()) / 1024 + " KB";
            } else {
              patchFileLength = patch.length() + " Bytes";
            }
            publish("Patch File erstellt: " + patchFileLength);
            publish("Starte Patch File Transfer");
            
            
            patch.deleteOnExit();
            
            //patch übertragen            
            if (transferFile(patch.getCanonicalPath(), "patch")) {
              
              publish("Patch File übertragen");
              
              if (fileService.applyPatch(remoteCompareFileName, remoteFileName, "patch")) {
    
                publish("Patch ausgeführt");
                
                //patch löschen
                patch.delete();
    
                //altes archivfile löschen
                archiv.delete();
    
                //lokales file in archiv kopieren
                copyToArchive(localFileName, localHashCode);
              } else {
                publish("FEHLER beim Ausführen des Patches");
              }
            } else {
              patch.delete();
              publish("FEHLER beim Übertragen des Patches");
            }
  
          } else {
            publish("Keine lokale Kopie vorhanden");
            publish("Normaler Transfer");
            //nein keine lokale Archiv Kopie, dann normal übertragen
      
            if (transferFile(localFileName, remoteFileName)) {          
              copyToArchive(localFileName, localHashCode);
            }
          }
        } else {
          publish("Remote File und lokales File sind identisch");
          publish("Kein Transfer");
          
          //Ins Archiv kopieren, wenn nötig
          copyToArchive(localFileName, localHashCode);

        }

      } else {
        publish("Files sind identisch");
        publish("Kein Transfer");

        //Ins Archiv kopieren, wenn nötig
        copyToArchive(localFileName, localHashCode);

      }
    } else {
      publish("File existiert Remote noch nicht");
      publish("Normaler Transfer");
      //File existiert Remote noch nicht
      if (remoteFileName == null) {
        remoteFileName = remoteCompareFileName;
      }
      if (transferFile(localFileName, remoteFileName)) {
        copyToArchive(localFileName, localHashCode);
      }
    }

  }

  private void copyToArchive(String fileName, String hashCode) throws FileNotFoundException, IOException {
    File archiv = new File("archiv", hashCode);
    if (!archiv.exists()) {

      File localFile = new File(fileName);

      FileInputStream fis = new FileInputStream(localFile);
      FileOutputStream archivOutputStream = new FileOutputStream(archiv);

      byte[] buffer = new byte[BUFFER_SIZE];
      int count = 0;
      int n = 0;
      while (-1 != (n = fis.read(buffer))) {
        archivOutputStream.write(buffer, 0, n);
        count++;
      }

      archivOutputStream.close();
      fis.close();

    }
  }

  private boolean transferFile(String local, String remote) throws RemoteException,
      FileNotFoundException, IOException {

    FileInputStream fis = null;


    File localFile = new File(local);
    long totalSize = localFile.length();
    setProgress(0);

    int handle = fileService.openFile(remote);
    try {
      fis = new FileInputStream(localFile);

      byte[] buffer = new byte[BUFFER_SIZE];
      int count = 0;
      int n = 0;

      while (-1 != (n = fis.read(buffer))) {
        fileService.write(handle, buffer, 0, n);

        if (isCancelled()) {
          publish("Abgebrochen");          
          return false;
        }
        
        count++;
        int progress = (int)((100l * (count * n)) / totalSize);
        if (progress > 100) {
          progress = 100;
        }
        setProgress(progress);
        
      }
      
      return true;
    } catch (RemoteException e) {
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (isCancelled()) {
        fileService.closeAndDeleteFile(handle, remote);
      } else {
        fileService.closeFile(handle, remote);
      }

      if (fis != null) {
        fis.close();
      }

      setProgress(100);
    }
  }

}
