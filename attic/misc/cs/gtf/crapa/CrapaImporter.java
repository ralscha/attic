package gtf.crapa;

import java.io.*;
import java.util.Calendar;
import java.math.BigDecimal;
import java.sql.SQLException;

import common.util.*;

public class CrapaImporter {

	private int month, year;
	private String fileName;
	
	
	public CrapaImporter() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -1);
		month = rightNow.get(Calendar.MONTH);
		year  = rightNow.get(Calendar.YEAR);
		fileName = AppProperties.getStringProperty(gtf.common.Constants.P_CRAPA_INPUT_FILE);
	}
	
	public CrapaImporter(String fileName, int year, int month) {
		this.fileName = fileName;
		this.month = month;
		this.year = year;
	}
	
	public static void main(String[] args) {
		
		if (args.length == 3) {
			new CrapaImporter(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2])).importData();
		} else {
			System.out.println("java CrapaImporter <fileName> <YYYY> <MM>");
		}
	}
	
	public void importData() {				

		try {
			
			CrapaDbManager.openDb();
						
			BufferedReader dis = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = dis.readLine()) != null) {
				if (line.length() >= 44) {
					try {
						CrapaItem ci = new CrapaItem();
						ci.setMonth(month);
						ci.setYear(year);
						ci.setCif(line.substring(0, 12));
						ci.setBranch(line.substring(16, 20));
						ci.setKst(line.substring(20, 23));
						ci.setLegalEntity(line.substring(12, 16));
						ci.setCrapaCode(Integer.parseInt(line.substring(23, 26)));
						ci.setAmount(new BigDecimal(line.substring(26, 44).trim()));
						
						CrapaDbManager.insertCrapaItem(ci);
	
					} catch (NumberFormatException nfe) {
						System.err.println(nfe);
					}
				}
			}
			dis.close();	
			
			CrapaDbManager.closeDb();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}