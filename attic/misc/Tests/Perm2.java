

import java.util.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import ViolinStrings.*;
import java.sql.*;

import gtf.common.*;
import gtf.db.*;

public class Perm2{

	private int queue[];
	private boolean flag[];
	private List resultList;
	private Object[] items;
	private int m, n;
	private static Set pwSet;
	
	//private final static String SEARCHPW = "67A3A06A80BE1A18E52C5C42EA208179";
	private static MessageDigest digest = null;
	
	
	//Permutationen mit n-Elementen aus m	
	public Perm2(Object[] input, int n)	 {
	
		m = input.length;
		this.n = n;
		
		this.items = input;
		
		flag = new boolean[m];
		queue = new int[m];
		
		for (int i = 0; i < m; i++)
			flag[i] = false;

		resultList = new ArrayList();
	
		perm(0);
	
	}
			
	private void perm(int level) {

		StringBuffer sb = new StringBuffer();
		StringBuffer rs = new StringBuffer();
		
		byte[] pw = null;
		byte[] resultmd = null;

		
		for (int i = 0; i < m; i++) {
			if (flag[i] == false) {
				flag[i] = true;
				queue[level] = i;
				if (level < n-1)
					perm(level + 1);
				else {
					Object[] result = new Object[n];
					for (int j = 0; j <= level; j++)
						result[j] = items[queue[j]];
					
					
					sb.setLength(0);
					rs.setLength(0);
					sb.append("KU");
					for (int x = 0; x < result.length; x++) {
						sb.append(result[x]);
					}
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
				flag[i] = false;
			}
		}
	}

	public static void main(String[] args) {

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

			
		List abc = new ArrayList();
	
		char start = 'A';
		for (int i = 0; i < 26; i++)
			abc.add(new Character((char)(start+i)));
	
		
		long startTime = System.currentTimeMillis();
		
		/*
		new Perm2(abc.toArray(), 2);
		System.out.println("END 2");
		new Perm2(abc.toArray(), 3);
		System.out.println("END 3");
		*/
		new Perm2(abc.toArray(), 4);
		System.out.println("END 4");
		/*
		new Perm2(abc.toArray(), 5);
		System.out.println("END 5");
		new Perm2(abc.toArray(), 6);
		*/
		System.out.println("Millis : " + (System.currentTimeMillis() - startTime));
		
	}
}