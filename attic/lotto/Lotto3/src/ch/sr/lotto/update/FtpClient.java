package ch.sr.lotto.update;

import java.io.*;
import java.util.*;

import ch.sr.lotto.util.*;
import com.enterprisedt.net.ftp.*;

public class FtpClient {
		
	private List ftpFilesList;	
	private FtpConnection connection;
	
	public FtpClient(FtpConnection connection, FtpFile[] files) {
		init(connection, files);
	}
	
	public FtpClient(FtpConnection connection, FtpFile file) {
		FtpFile[] ff = new FtpFile[1];
		ff[0] = file;
		init(connection, ff);
	}
	
	private void init(FtpConnection connection, FtpFile[] files) {
		ftpFilesList = new ArrayList();
		this.connection = connection;
		
		for (int i = 0; i < files.length; i++)
			addFile(files[i]);
		
		AppProperties.setProxyProperties();
	}
	
	public void addFiles(FtpFile[] ffArr) {
		for (int i = 0; i < ffArr.length; i++)
			addFile(ffArr[i]);
	}
	
	public void addFile(FtpFile ff) {
		ftpFilesList.add(ff);
	}
	
	public void send() throws IOException {
		
    try {
      
      FTPClient ftp = new FTPClient(connection.getServer());
      System.out.println("log on...");
      ftp.login(connection.getUser(), connection.getPassword());
      ftp.debugResponses(true);      
      
      
      String mRemoteDir = null;
      Iterator it = ftpFilesList.iterator();
      while(it.hasNext()) {
        FtpFile ff = (FtpFile)it.next();
        
        if (!ff.getRemoteDirectory().equals(mRemoteDir)) {
          mRemoteDir = ff.getRemoteDirectory();
          System.out.println("change directory ..... " + mRemoteDir);
          ftp.chdir(mRemoteDir);          
        }
        
        try {
          ftp.delete(ff.getRemoteFileName());                    
          System.out.println("delete file .... " + ff.getRemoteFileName());
        } catch (Exception e) { 
        //no action  
        }
        
        System.out.println("store file .... " + ff.getLocalFileName());
        System.out.println("store file .... " + ff.getRemoteFileName());
        
        if (ff.getType() == FtpFile.ASCII) {
          ftp.setType(FTPTransferType.ASCII);
        } else {
          ftp.setType(FTPTransferType.BINARY);
        }
        
        ftp.put(ff.getLocalFileName(), ff.getRemoteFileName());
                
        
      }

      System.out.println("logoff");
      ftp.quit();
      
    } catch (FTPException ex) {
      throw new IOException(ex.toString());
    }
    



  
	}
	
}