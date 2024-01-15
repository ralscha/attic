package lotto.update;

import java.io.*;
import java.util.*;
import common.io.*;
import common.util.*;

public class DatabaseZipper {
                
    
	public FileSender zip(boolean send) {
   
		String zipName = AppProperties.getStringProperty("file.database.zip");
		String zipPath = AppProperties.getStringProperty("path.database.zip");
		
		try {
			ZipOutputFile zof = new ZipOutputFile(zipPath+zipName);
			zof.add(AppProperties.getStringProperty("file.database"));
			zof.add(AppProperties.getStringProperty("file.database.help.1"));
			zof.add(AppProperties.getStringProperty("file.database.help.2"));
			zof.close();
		} catch (IOException ioe) {
			//LOG
			return null;
		}
		
		if (send) {
			FileSender fs = 
				new FileSender(zipPath+zipName, zipName,
				  AppProperties.getStringProperty("ftp.home.backup"), true);
			fs.start();
			return fs;
		} else {
			return null;
		}
	}
 
	public static void main(String args[]) {
		new DatabaseZipper().zip(true);
	}
    
    
}