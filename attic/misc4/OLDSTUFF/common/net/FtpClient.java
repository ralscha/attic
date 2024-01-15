package common.net;

import java.io.*;
import java.util.*;
import common.util.*;
import com.ibm.webrunner.net.FTPSession;  

public class FtpClient {
		
	private List ftpFilesList;	
	private FtpConnection connection;
	
	public FtpClient(FtpConnection connection, FtpFile[] files) {
		ftpFilesList = new ArrayList();
		this.connection = connection;
		
		for (int i = 0; i < files.length; i++)
			addFile(files[i]);
		
		setProxySettings();
	}
	
	public void addFiles(FtpFile[] ffArr) {
		for (int i = 0; i < ffArr.length; i++)
			addFile(ffArr[i]);
	}
	
	public void addFile(FtpFile ff) {
		ftpFilesList.add(ff);
	}
	
	public void send() throws IOException {
		
		FTPSession session = null;
		
		try {
			session = new FTPSession();
			session.setUser(connection.getUser());
			session.setPassword(connection.getPassword());
			session.setServer(connection.getServer());
			
			System.out.println("log on...");
			session.logon();
			
			String mRemoteDir = null;
			Iterator it = ftpFilesList.iterator();
			while(it.hasNext()) {
				FtpFile ff = (FtpFile)it.next();
				
				if (!ff.getRemoteDirectory().equals(mRemoteDir)) {
					mRemoteDir = ff.getRemoteDirectory();
					System.out.println("change directory ..... " + mRemoteDir);
					session.changeDirectory(mRemoteDir);
				}
				
				try {
					session.deleteFile(ff.getRemoteFileName());
					System.out.println("delete file .... " + ff.getRemoteFileName());
				} catch (IOException ioe) { }
				
				System.out.println("store file .... " + ff.getLocalFileName());
				session.storeFile(ff.getLocalFileName(), ff.getRemoteFileName(), ff.getType());
				
			}
			session.logoff();
			System.out.println("logoff");
		} catch (IOException ioe) {
			if (session.getLoggedOn()) {
				try {
					session.logoff();
				} catch (IOException io) { }
			}
			throw ioe;
		}
	}
	
	private void setProxySettings() {
		String proxyHost = AppProperties.getStringProperty("proxy.host");
		String proxyPort = AppProperties.getStringProperty("proxy.port");
		
		if ((proxyHost != null) && !(proxyHost.trim().length() > 0)) {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", proxyHost);
			System.getProperties().put("proxyPort", proxyPort); 
		}
	}
}