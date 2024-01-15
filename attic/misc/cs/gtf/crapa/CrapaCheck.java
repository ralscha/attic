package gtf.crapa;

import java.io.*;
import java.util.Calendar;

import ViolinStrings.*;

import common.util.*;


public class CrapaCheck implements gtf.common.Constants {

	public CrapaCheck() {				
		String path = AppProperties.getStringProperty(P_CRAPA_ARCHIVE_PATH);
		System.out.println(path);
		String maxDate = "000000";
		File maxFile = null;
		
		File pathFile = new File(path);
		File[] files = pathFile.listFiles(new CrapaFileFilter());
		for (int i = 0; i < files.length; i++) {
			String hlp = files[i].getName().substring(6,12);
			if (maxDate.compareTo(hlp) < 0) {
				maxDate = hlp;
				maxFile = files[i];
			}
		}
		
		try {
			String inputFileName = AppProperties.getStringProperty(P_CRAPA_INPUT_FILE);
			File inputFile = new File(inputFileName);
			System.out.println(inputFile);
			if (inputFile.exists()) {		
				if (FileUtil.compare(inputFile, maxFile) == FileUtil.NOT_EQUAL) {
					
					Calendar rightNow = Calendar.getInstance();
					rightNow.add(Calendar.MONTH, -1);
					int month = rightNow.get(Calendar.MONTH)+1;
					int year  = rightNow.get(Calendar.YEAR);
					
					new CrapaImporter(inputFileName, year, month).importData();
			
					String newFileName = path + "crapa_"+year+Strings.rightJustify(String.valueOf(month),2,'0')+".txt";
					FileUtil.copy(inputFileName,  newFileName);
				}
			} else
				System.err.println("InputFile does not exists");
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	public static void main(String args[]) {			
		new CrapaCheck();
	}
	
}