package gtf.util;


import java.io.*;
import java.util.*;
import gtf.util.*;
import gtf.util.*;
import gtf.swift.*;
import gtf.swift.util.*;
import gtf.swift.state.*;
import gtf.swift.input.*;

public class File2Db {

	public File2Db(String headerFile, String dataFile, String statFile) {
	
		System.out.println("StatFile");
		if (!statFile.trim().equals("")) 
			readStatFile(statFile);
	
		System.out.println("HeaderFile");
		if (!headerFile.trim().equals(""))			
			readHeaderFile(headerFile);
	
		System.out.println("DataFile");			
		if (!dataFile.trim().equals(""))			
			readDataFile(dataFile);		
			
		SWIFTInsertService.destroy();	
	}

	public static void main(String[] args) {
		if (args.length == 3) 
			new File2Db(args[0], args[1], args[2]);
		else
			System.out.println("java File2Db <headerFile> <msgLineFile> <statFile>");		
	}

	public void readDataFile(String fileName) {
		MessageLine obj = null;
		List ml = new ArrayList();
		BufferedReader dis;
		String line;
	
		try {
			dis = new BufferedReader(new FileReader(fileName));
			while ((line = dis.readLine()) != null) {
				if (line.length() > 2) {
					obj = new MessageLine();
					obj.setTOSN(line.substring(0,6));
					obj.setLineno(Short.parseShort(line.substring(6,10)));
					obj.setFieldTag(line.substring(10,13).trim());
					obj.setMsgLine(line.substring(13).trim());
	
					ml.add(obj);
				}
			}
			dis.close();
			
			if (ml.size() > 0)
				SWIFTInsertService.insertMessageLine(ml);
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}

	public void readHeaderFile(String file) {
		SWIFTHeader obj = null; 
		int year, month, day, h, m;
		GregorianCalendar date;
		BufferedReader dis;
		String line;
		List headers = new ArrayList();
		
		try {
			dis = new BufferedReader(new FileReader(file));
			while ((line = dis.readLine()) != null) {
				if (line.length() > 2) {
					obj = new SWIFTHeader();
					obj.setTOSN(line.substring(0, 6));
					year = Integer.parseInt(line.substring(6, 10));
					month = Integer.parseInt(line.substring(10, 12)) - 1;
					day = Integer.parseInt(line.substring(12, 14));
					date = new GregorianCalendar(year, month, day);
					obj.setSendDate(new java.sql.Timestamp(date.getTime().getTime()));
					obj.setSenderAddress(line.substring(14, 26).trim());
					obj.setSessionNumber(line.substring(26, 30));
					obj.setSequenceNumber(line.substring(30, 36));
					obj.setReceiverAddress(line.substring(45, 56).trim());
					obj.setProcCenter(line.substring(36, 40));
					obj.setMessageType(line.substring(40, 43));
					obj.setDuplicate(line.substring(44, 45));
					obj.setPriority(line.substring(43, 44));
					year = Integer.parseInt(line.substring(56, 60));
					month = Integer.parseInt(line.substring(60, 62)) - 1;
					day = Integer.parseInt(line.substring(62, 64));
					h = Integer.parseInt(line.substring(64, 66));
					m = Integer.parseInt(line.substring(66, 68));
					date = new GregorianCalendar(year, month, day, h, m, 0);
					obj.setReceiveTS(new java.sql.Timestamp(date.getTime().getTime()));
					
					headers.add(obj);
				}
			}
			dis.close();
		
			if (headers.size() > 0)
				SWIFTInsertService.insertSWIFTHeader(headers);
		
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	public void readStatFile(String file) {
		State obj = null;
		int year, month, day, h, m;
		GregorianCalendar date;
		BufferedReader dis;
		String line;
		List states = new ArrayList();
		
		try {
			
			dis = new BufferedReader(new FileReader(file));
			while ((line = dis.readLine()) != null) {
				State state = new State();
				state.setProcCenter(line.substring(0, 4));
				state.setOrderNo(line.substring(4, 39));
				state.setStateNum(line.substring(39, 41));
				state.setStateTran(line.substring(41, 44));
				state.setMessageType(line.substring(44, 45));
				StringTokenizer st = new StringTokenizer(line.substring(4, 39), ".");
				if (st.hasMoreTokens())
					state.setGtfNo(Integer.parseInt(st.nextToken()));
				else
					state.setGtfNo(0);
				year = Integer.parseInt(line.substring(45, 49));
				month = Integer.parseInt(line.substring(49, 51)) - 1;
				day = Integer.parseInt(line.substring(51, 53));
				h = Integer.parseInt(line.substring(53, 55));
				m = Integer.parseInt(line.substring(55, 57));
				date = new GregorianCalendar(year, month, day, h, m, 0);
				state.setStateTS(new java.sql.Timestamp(date.getTime().getTime()));
				
				states.add(state);
			}
			dis.close();
	
			if (states.size() > 0)
				SWIFTInsertService.insertState(states);
			
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
}