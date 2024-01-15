
import java.io.*;
import java.util.*;
import common.util.*;
import common.io.*;
import java.text.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import ViolinStrings.*;
import com.ibm.webrunner.net.FTPSession;  

public class FtpBackup {
		
	List pathList;
	String backupZipFileName, backupZipPath;
	String keyDatFileName, keyDatFilePath;
	
	public FtpBackup() {
		
		AppProperties.setProxyProperties();
		
		backupZipFileName = AppProperties.getStringProperty("backup.zip.file");
		backupZipPath     = AppProperties.getStringProperty("backup.zip.path");
		keyDatFileName = AppProperties.getStringProperty("encrypt.key.file", "key.dat");
		keyDatFilePath = AppProperties.getStringProperty("encrypt.key.path", "");
		
		pathList = AppProperties.getStringArrayProperty("path");		

	}
	
	private void ftpBackupFile() {
		
		if (!AppProperties.getBooleanProperty("ftp.send", false)) return;
		
		FTPSession session = null;	
		try {
			session = new FTPSession(); 
		
			
			session.setUser(AppProperties.getStringProperty("ftp.user"));
			session.setPassword(AppProperties.getStringProperty("ftp.password"));
			session.setServer(AppProperties.getStringProperty("ftp.host"));
			
			System.out.println("log on...");
			session.logon();
			
			String remoteDirectory = AppProperties.getStringProperty("ftp.directory");
			if ((remoteDirectory != null) && (!remoteDirectory.trim().equals(""))) {
				System.out.println("change directory...");
				session.changeDirectory(remoteDirectory);
			}
			
			System.out.println("delete old file...");
			try {
				session.deleteFile(backupZipFileName);
				session.deleteFile(keyDatFileName);
			} catch (IOException ioe) { }
			System.out.println("store backup file...");
			session.storeFile(backupZipPath+backupZipFileName, backupZipFileName, 'I');
			session.storeFile(keyDatFilePath+keyDatFileName, keyDatFileName, 'I');
			System.out.println("logoff...");			
			session.logoff(); 
			System.out.println("DONE");
		} catch (IOException ioe) {
			if (session.getLoggedOn()) {
				try {
					session.logoff();
				} catch (IOException io) { }
			}
			
			System.err.println(ioe);
		}

	}
	
	private FileFilter getFileFilter() {
		
		List excludeEndingList = AppProperties.getStringArrayProperty("exclude.ending");							
		List excludeFileList   = AppProperties.getStringArrayProperty("exclude.file");
		List excludeDirList    = AppProperties.getStringArrayProperty("exclude.dir");

		
		ExcludeFileFilter eff = new ExcludeFileFilter((String[])excludeFileList.toArray(new String[excludeFileList.size()]));
		ExcludeEndingFileFilter eeff = new ExcludeEndingFileFilter((String[])excludeEndingList.toArray(new String[excludeEndingList.size()]));
		
		ExcludeDirFilter edf = new ExcludeDirFilter((String[])excludeDirList.toArray(new String[excludeDirList.size()]));
		
		
		MultiFileFilter mff = new MultiFileFilter();
		mff.addFilter(edf);
		mff.addFilter(eff);
		mff.addFilter(eeff);
		return mff;
	}
	
	public void doIt() {
		try {
		
			
			File zipFile = new File(backupZipPath+backupZipFileName);
			//zipFile.deleteOnExit();
			
			ZipOutputFile zof = new ZipOutputFile(zipFile, getFileFilter(), getCipher());
			//ZipOutputFile zof = new ZipOutputFile(zipFile, eff);
			
			Iterator it = pathList.iterator();
			while(it.hasNext()) {
				File file = new File((String)it.next());
				if (file.isFile()) 
					zof.addFile(file);
				else
					zof.add(file);
			}
			zof.close();
			
			ftpBackupFile();
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	private Cipher getCipher() {
		
		Cipher cipher = null;
		
		if (AppProperties.getBooleanProperty("encrypt", true)) {
		
			try {		
				KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
				keyGenerator.init(128);
				SecretKey key = keyGenerator.generateKey();
				
				cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				
				SecretKeyFactory keyFact = SecretKeyFactory.getInstance("Blowfish");
				SecretKeySpec keySpec = (SecretKeySpec)keyFact.getKeySpec(key, SecretKeySpec.class);
				byte[] keyBytes = keySpec.getEncoded();
								
				FileOutputStream fos = new FileOutputStream(keyDatFilePath+keyDatFileName);
				fos.write(keyBytes);
				fos.close();
				
			} catch (Exception e) {
				System.err.println(e);
			}
			
		}
		return cipher;

	}
	
	public static void main(String[] args) {
		Security.addProvider(new au.net.aba.crypto.provider.ABAProvider());
		new FtpBackup().doIt();	
	} 
}