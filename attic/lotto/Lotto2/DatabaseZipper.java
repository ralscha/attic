import java.io.*;
import java.util.zip.*;
import java.util.*;
import com.oroinc.net.ftp.*;
import lotto.util.*;

public class DatabaseZipper implements Runnable {
        
    private Thread myThread;
    
    private final int MAX_LOOP = 5;
    private final int WAIT_TIME = 600; /* seconds (10 minutes) */
    private String zipName, zipPath;
    private String files[];
        
    public DatabaseZipper() {
        this(new String[0]);
    }
    
    public DatabaseZipper(String args[]) {
        zipName = AppProperties.getInstance().getProperty("dbZip");
        zipPath = AppProperties.getInstance().getProperty("dbZipPath");
        files = new String[3];
        files[0] = AppProperties.getInstance().getProperty("database");
        files[1] = AppProperties.getInstance().getProperty("dbHelperFile1");
        files[2] = AppProperties.getInstance().getProperty("dbHelperFile2");
             
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("unzip")) {
                if (getDbFile()) 
                    unzip();
            }
        } else {             
            if (zip()) {                      
                myThread = new Thread(this);
                myThread.start();       
            } else {
                DiskLog.getInstance().log(DiskLog.FATAL, getClass().getName(), "DBZIP-File konnte nicht erstellt werden");            
            }
        }
    }
    
    public void run() {
        int loopCount = 0;
        boolean ok = false;
        
        while ((Thread.currentThread() == myThread) && (loopCount < MAX_LOOP) && (!ok) ) {    
            ok = sendDbFile();            
            if (!ok) {
                try {
                    myThread.sleep(WAIT_TIME*1000);
                } catch(InterruptedException ex) { return; }
            }            
            loopCount++;
        }
    }
    
    private boolean sendDbFile() {
        FTPClient ftp = new FTPClient();
        int reply;

        try {
            File t = new File(zipPath+File.separator+zipName);
            if (!t.exists()) return false;

            ftp.connect(AppProperties.getInstance().getProperty("hostNameXOOM"));
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), ftp.getReplyString());

            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "FTP server refused connection");
              	return false;
            } else {
                if (ftp.login(AppProperties.getInstance().getProperty("userNameXOOM"), AppProperties.getInstance().getProperty("passwordXOOM"))) {
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), ftp.getReplyString());
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Remote system is " + ftp.getSystemName());

                    ftp.setFileType(FTP.IMAGE_FILE_TYPE);
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), ftp.getReplyString());

                    FileInputStream input = new FileInputStream(t);
                    if (input != null) {
                        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "deleting "+zipName);
                        ftp.deleteFile(zipName);
                        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), ftp.getReplyString());

                        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Storing "+zipName);
                        ftp.storeFile(zipName, input);
                        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), ftp.getReplyString());
                        input.close();
                        return true;
                    } else {
                        return false;
                    }
                } else {
       	            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), ftp.getReplyString());
                	return false;
                }
            }

        } catch(IOException e) {
            if(ftp.isConnected()) {
            	try {
                  ftp.disconnect();
            	} catch(IOException f) { }
            }
            DiskLog.getInstance().log(DiskLog.FATAL, getClass().getName(), ""+e);
            return false;
        }
    }
    
    
    private void unzip() {
        try {
            File file = new File(zipName);
            if (file.exists()) {
                ZipFile archive = new ZipFile(file);
                byte[] buffer = new byte[16384];
                for (Enumeration e = archive.entries(); e.hasMoreElements();) {
                    ZipEntry entry = (ZipEntry)e.nextElement();
                    String fileName = entry.getName();
                    System.out.println(fileName);
                    File destFile = new File(fileName);
                    if (destFile.exists()) {
                        System.out.println(fileName + " exists, delete");
                        destFile.delete();
                    } 
                    InputStream in = archive.getInputStream(entry);
                    OutputStream out = new FileOutputStream(fileName);
                    int count;
                    while((count = in.read(buffer)) != -1)
                        out.write(buffer, 0, count);
                            
                    in.close();
                    out.close();
                }
            } else {
                System.out.println("File "+zipName+" does not exists");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private boolean getDbFile() {
        FTPClient ftp = new FTPClient();
        int reply;

        try {
            File t = new File(zipName);
            if (t.exists()) {
                System.out.println(zipName + " existiert bereits und wird gelöscht");
                t.delete();
            }

            ftp.connect(AppProperties.getInstance().getProperty("hostNameXOOM"));
            System.out.println(ftp.getReplyString());

            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("FTP server refused connection");
                return false;
            } else {
                if (ftp.login(AppProperties.getInstance().getProperty("userNameXOOM"), AppProperties.getInstance().getProperty("passwordXOOM"))) {
                    System.out.println(ftp.getReplyString());
                    System.out.println("Remote system is " + ftp.getSystemName());

                    ftp.setFileType(FTP.IMAGE_FILE_TYPE);
                    System.out.println(ftp.getReplyString());

                    FileOutputStream output = new FileOutputStream(t);
                    if (output != null) {
                        System.out.println("fetch "+zipName);                        
                        ftp.retrieveFile(zipName, output);
                        System.out.println(ftp.getReplyString());
                        output.close();
                        return true;
                    } else {
                        System.out.println("FileOutputStream ist null");
                        return false;
                    }
                } else {
       	            System.out.println(ftp.getReplyString());
       	            return false;
                }
            }

        } catch(IOException e) {
            if(ftp.isConnected()) {
            	try {
                  ftp.disconnect();
            	} catch(IOException f) { }
            }
            System.out.println(e);
            return false;
        }
    }

    
    private boolean zip() {
        try {
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipPath + File.separator + zipName));
            zip.setMethod(ZipOutputStream.DEFLATED);
            zip.setLevel(9);
            
            for (int i = 0; i < files.length; i++) {
                File file = new File(files[i]);
                FileInputStream in = new FileInputStream(file);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                
                ZipEntry entry = new ZipEntry(file.getName());
                entry.setTime(file.lastModified());

                zip.putNextEntry(entry);
                zip.write(bytes);
                zip.closeEntry();
            }
            zip.close();            
            return true;
            
        } catch (Exception e) {
            DiskLog.getInstance().log(DiskLog.FATAL, getClass().getName(), "zip : " + e);
            return false;
        }
                
                
    }
		
    public static void main(String args[]) {
        new DatabaseZipper(args);
    }
}

