

import java.util.*;
import java.io.*;
import java.text.*;

import gtf.common.*;

import common.io.*;

public class Tests1 {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	private Tests1() {
		
	
		String remotePathStr = "d:\\projects\\tests\\zips";
		Calendar lastBackupDate = Calendar.getInstance();
		Calendar deleteDate = Calendar.getInstance();
		
		if (lastBackupDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			lastBackupDate.add(Calendar.DATE, -3);
		else
			lastBackupDate.add(Calendar.DATE, -1);
		
		deleteDate.add(Calendar.DATE, -6);
		
		String lookFile = dateFormat.format(lastBackupDate.getTime()) + ".zip";
		String deleteFileName = dateFormat.format(deleteDate.getTime());
		
		boolean found = false;
		
		File remotePath = new File(remotePathStr);

		if (remotePath.exists() && remotePath.isDirectory()) {
			File[] files = remotePath.listFiles(new IncludeEndingFileFilter(".zip"));
			for (int i = 0; i < files.length; i++) {

				if (files[i].getName().compareTo(deleteFileName) <= 0) {
					if (files[i].isDirectory()) {
					//	deleteFiles(files[i]);
					}
					files[i].delete();
					System.out.println("DELETE " + files[i].getName());
				}
				
				if (files[i].getName().equals(lookFile)) {
					found = true;
				}
					
			}
			if (!found) {
				System.out.println("kein Backup gefunden ");
			} else 
				System.out.println("Backup ok");
			
		
		} else {
			System.out.println("path not found");
		}
		
	}


	
	public static void main(String args[]) {
		new Tests1();
	} 

}