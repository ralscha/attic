package common.net;

import java.io.*;
import java.util.*;
import common.util.*;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FTPConnectMode; 

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
        } catch (Exception e) { }
        
        System.out.println("store file .... " + ff.getLocalFileName());
        
        if (ff.getType() == FtpFile.ASCII) {
          ftp.setType(FTPTransferType.ASCII);
        } else {
          ftp.setType(FTPTransferType.BINARY);
        }
        
        ftp.put(ff.getLocalFileName(), ff.getRemoteDirectory());
                
        
      }

      System.out.println("logoff");
      ftp.quit();
      
    } catch (FTPException ex) {
      throw new IOException(ex.toString());
    }
    



  
	}
	
}