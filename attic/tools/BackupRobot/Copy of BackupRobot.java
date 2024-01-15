import java.io.*;
import common.util.*;
import java.text.*;
import java.util.*;

public class BackupRobot implements Runnable {
	private final static int DEFAULT_WAIT_TIME = 60;
	private Thread myThread;
	private int waitTimeInSeconds;

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

	public BackupRobot() {
		this(AppProperties.getIntegerProperty("waittime", DEFAULT_WAIT_TIME));
	}

	public BackupRobot(int waitTimeInSeconds) {
		this.waitTimeInSeconds = waitTimeInSeconds;
		myThread = new Thread(this);
		myThread.start();
	}

	public static void main(String args[]) {
		new BackupRobot();
	} 

	public void run() {
		while (!myThread.isInterrupted()) {
			String okFileName = AppProperties.getStringProperty("okfile");
			File okFile = new File(okFileName);

			if (okFile.exists()) {
			
				try {
					BufferedReader br = new BufferedReader(new FileReader(okFile));
					String backupFileName = br.readLine();
					br.close();
					okFile.delete();

					File backupFile = new File(backupFileName);
					
					String remoteFileName = AppProperties.getStringProperty("backuppath") + backupFile.getName();
					File remoteFile = new File(remoteFileName);
					
					if ((backupFile.exists()) && (remoteFile != null)) {
						FileUtil.copy(backupFile, remoteFile);
					}
					
					// lokales File löschen
					backupFile.delete();


					deleteOldBackups(new File(AppProperties.getStringProperty("backuppath")));
					// Thread beenden
					return;
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
			} 

			try {
				myThread.sleep(waitTimeInSeconds * 1000);
			} catch (InterruptedException ex) {}
		} 
	} 

	public void stop() {
		myThread.interrupt();
	} 


	private void deleteOldBackups(File backupPath) {
		String front = AppProperties.getStringProperty("backupfilenamepre");
		String end   = AppProperties.getStringProperty("backupfilenamepost");
		Calendar deleteDate = Calendar.getInstance();		
		int keep = AppProperties.getIntegerProperty("keepdays", 5);
		deleteDate.add(Calendar.DATE, -keep);
						
		String deleteFileName = front + dateFormat.format(deleteDate.getTime()) + end;
	
		if (backupPath.exists() && backupPath.isDirectory()) {
			File[] files = backupPath.listFiles();
			for (int i = 0; i < files.length; i++) {
			   System.out.println(files[i]);		
				if (files[i].getName().startsWith(front)) {				
					if (files[i].getName().compareTo(deleteFileName) <= 0) {
						if (!files[i].delete()) {
							System.out.println("WARNING: DELETE FILE");
						}
					}
				}
			}
		}
	}
	
}



		