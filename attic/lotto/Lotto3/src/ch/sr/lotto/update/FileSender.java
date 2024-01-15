package ch.sr.lotto.update;

import ch.sr.lotto.util.*;

public class FileSender  {

	private int maxLoop;
	private int waitTime; /* seconds */
	private FtpClient client;
	private FtpConnection ftpConn;
	
	
	public FileSender(String localPath, boolean binary) {
		init();
		
		char type;
		
		if (binary)
			type = FtpFile.BINARY;
		else
			type = FtpFile.ASCII;
		
		FtpFile[] ff = FtpFile.createFtpFileArray(localPath, 
						AppProperties.getStringProperty("ftp.home"), type);		
		client = new FtpClient(ftpConn, ff);
	
	}
	
	public FileSender(String localPath, String remotePath) {
		init();
		
		FtpFile[] ff = FtpFile.createFtpFileArray(localPath, remotePath, FtpFile.ASCII);		
		client = new FtpClient(ftpConn, ff);

	}
	
	public FileSender(String localFile, String remoteFile, String remoteDir, boolean binary) {
		char type;
		
		init();

		
		if (binary)
			type = FtpFile.BINARY;
		else
			type = FtpFile.ASCII;
		
		FtpFile ff = new FtpFile(localFile, remoteFile, remoteDir, type);
		client = new FtpClient(ftpConn, ff);
	}
	
	private void init() {
		ftpConn = new FtpConnection();
		ftpConn.setServer(AppProperties.getStringProperty("ftp.host"));
		ftpConn.setUser(AppProperties.getStringProperty("ftp.user"));
		ftpConn.setPassword(AppProperties.getStringProperty("ftp.password"));	
		
		try {
			maxLoop = AppProperties.getIntegerProperty("sender.runner.maxloop", 5);
		} catch (NumberFormatException nfe) {
			maxLoop = 5;
		}

		try {
			waitTime = AppProperties.getIntegerProperty("sender.runner.waittime", 300);
		} catch (NumberFormatException nfe) {
			waitTime = 300;
		}

		
	}
	
	public FileSender(String path) {
		this(path, AppProperties.getStringProperty("ftp.home"));	
	}
/*
	public void run() {
		int loopCount = 0;
		System.out.println("FileSender run");
		while ((Thread.currentThread() == this) && (loopCount < maxLoop) && (!sendFiles())) {    
			try {
				sleep(waitTime*1000);
			} catch(InterruptedException ex) { return; }            
			loopCount++;
		}

	}
*/
    
    public boolean sendFiles() {
        try {
        	client.send();
        	return true;
        } catch (Exception e) {
          e.printStackTrace();
        	return false;
        }
    } 
               
}
    