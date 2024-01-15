package gtf.usermanager;

import java.security.*;
import java.sql.*;
import java.util.*;

import gtf.db.USERTable;
import gtf.db.USER;
import gtf.common.*;

import ViolinStrings.*;
import common.swing.*;

public class GMBLoginValidator implements LoginValidator {

	
	private ServiceCenter sc;
	private MessageDigest digest;
	
	public GMBLoginValidator() {
		try {
			sc = new ServiceCenter("ZRH");
			digest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean validate(String name, String password) {
		if ((name != null) && (password != null)) {
			try {
				USERTable ut = new USERTable(sc.getConnection());
				Iterator it = ut.select("USERID = '"+name.toUpperCase()+"'");
				if (it.hasNext()) {
					byte[] msg = password.toUpperCase().getBytes();
					digest.update(msg);
					byte[] result = digest.digest();
					
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < result.length; i++) {
						sb.append(Strings.rightJustify(Integer.toHexString(result[i]).toUpperCase(), 2, '0'));
					}

					USER user = (USER)it.next();
					if (user.getENCRYPTED_PASSWORD().trim().equals(sb.toString()))
						return true;
					else
						return false;
					
				} else {
					return false;
				}
			} catch (SQLException sqle) {
				System.err.println(sqle);
				return false;
			} finally {
				try {
					sc.closeConnection();
				} catch (SQLException sqle) {
					System.err.println(sqle);
				}
			}
		}
		return false;
	}

	public void validationCancelled() {
		System.exit(1);
	}

}