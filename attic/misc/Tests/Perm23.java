

import java.util.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import ViolinStrings.*;
import java.sql.*;

import gtf.common.*;
import gtf.db.*;

public class Perm23{

	private static Set pwSet;
	
	//private final static String SEARCHPW = "67A3A06A80BE1A18E52C5C42EA208179";
	private static MessageDigest digest = null;


	public static void main(String[] args) {

		StringBuffer sb = new StringBuffer();
		StringBuffer rs = new StringBuffer();
		
		byte[] pw = null;
		byte[] resultmd = null;
		
		pwSet = new HashSet();
		
		try {
			ServiceCenter sc = new ServiceCenter("ZRH");
			Connection conn = sc.getConnection();
			USERTable ut = new USERTable(conn);
			
			Iterator it = ut.select();
			while(it.hasNext()) {
				pwSet.add(((USER)it.next()).getENCRYPTED_PASSWORD());
			}
			
			sc.closeConnection();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ne) {
			System.err.println(ne);
			return;
		}

		long startTime = System.currentTimeMillis();
		boolean kons;
		
		for (int a = 'F'; a <= 'Z'; a++) {
			kons = isKonsonant((char)a);
			
			for (int b = 'A'; b <= 'Z'; b++) {
				if (kons)
					if (isKonsonant((char)b)) continue;	
				
			System.out.println(""+(char)a+(char)b);
			for (int c = 'A'; c <= 'Z'; c++) {	
			for (int d = 'A'; d <= 'Z'; d++) {
			for (int e = 'A'; e <= 'Z'; e++) {
			for (int f = 'A'; f <= 'Z'; f++) {
			
				if (a != b &&
				    a != c &&
				    a != d &&
				    a != e &&
				    a != f &&
				    b != c &&
				    b != d &&
				    b != e &&
				    b != f &&
				    c != d &&
				    c != e &&
				    c != f &&
				    d != e &&
				    d != f &&
				    e != f) continue;
			
				sb.setLength(0);
				rs.setLength(0);
					
				sb.append((char)a);
				sb.append((char)b);
				sb.append((char)c);
				
				sb.append((char)d);
				sb.append((char)e);
				sb.append((char)f);
				
				//System.out.println(sb);
				
				pw = sb.toString().getBytes();
				digest.update(pw);
				resultmd = digest.digest();
				
				rs.setLength(0);
				for (int x = 0; x < resultmd.length; x++) {
					rs.append(Strings.rightJustify(Integer.toHexString(resultmd[x]).toUpperCase(), 2, '0'));
				}	
				if (pwSet.contains(rs.toString())) {
					System.out.println(rs.toString());
					System.out.println(sb.toString());
					System.out.println();
					
					pwSet.remove(rs.toString());
					if (pwSet.size() == 0)
						System.exit(0);
				}
			}
			}
			}
			}
			}
		}
		
		System.out.println("Millis : " + (System.currentTimeMillis() - startTime));
		
	}
	
	private static boolean isKonsonant(char c) {
		return (c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U');
	}
}