import java.io.*;
import common.util.*;
import java.text.*;
import java.util.*;

public class BackupRobot {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

	public BackupRobot() {
    deleteOldBackups(new File(AppProperties.getStringProperty("backuppath"))); 
	}

	public static void main(String args[]) {
		new BackupRobot();
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



		