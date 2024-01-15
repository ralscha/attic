import java.io.*;
import gtf.control.*;
import common.util.*;
import ViolinStrings.*;
import java.util.Calendar;

public class CrapaCheck {

	public CrapaCheck() {				
		String path = AppProperties.getStringProperty("crapa.archive.path");
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
		
		System.out.println(maxFile.getPath());
		
		try {
			File inputFile = new File(AppProperties.getStringProperty("crapa.input.file"));
			System.out.println(inputFile);
			if (inputFile.exists()) {		
				if (FileUtil.compare(inputFile, maxFile) == FileUtil.NOT_EQUAL) {
					
					Calendar rightNow = Calendar.getInstance();
					rightNow.add(Calendar.MONTH, -1);
					int month = rightNow.get(Calendar.MONTH)+1;
					int year  = rightNow.get(Calendar.YEAR);
					String fileName = AppProperties.getStringProperty("crapa.input.file");
					
					new CrapaImporter(fileName, year, month).importData();
			
					String newFileName = AppProperties.getStringProperty("crapa.archive.path");
					newFileName += "crapa_"+year+Strings.rightJustify(String.valueOf(month),2,'0')+".txt";
					FileUtil.copy(fileName,  newFileName);
				}
			} else
				System.out.println("InputFile does not exists");
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	public static void main(String args[]) {			
		new CrapaCheck();
	}
	
}