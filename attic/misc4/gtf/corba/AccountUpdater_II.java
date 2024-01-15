package gtf.recon.corba;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.ACCOUNT;
import gtf.db.ACCOUNTTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class AccountUpdater_II implements gtf.common.Constants {
	
	private final static String whereClause = "ACCOUNT_TYPE >= '000' AND ACCOUNT_TYPE <= '999' AND INACTIV <> 'I' AND ACCOUNT_TYPE <> '401'";
	private final static String orderClause = "ACCT_MGMT_UNIT ASC, ACCOUNT_NUMBER ASC";
	
	private final static int SPLIT_SIZE = 100;

	public AccountUpdater_II() {
	}
	
	private void update(ServiceCenter sc) throws SQLException {
		Connection conn = sc.getConnection();
		ACCOUNTTable at = new ACCOUNTTable(conn);
		List acctList = new ArrayList();
		
		Iterator it = at.select(whereClause, orderClause);
		while(it.hasNext()) {
			ACCOUNT obj = (ACCOUNT)it.next();
			
			Account acct = new Account(obj.getACCT_MGMT_UNIT().trim()+obj.getACCOUNT_NUMBER().trim(),
			                           obj.getINACTIV().trim(), 
			                           obj.getSALDIERT().trim(), 
			                           obj.getBUSINESS_UNIT().trim(), 
			                           obj.getACCOUNT_TYPE().trim(),
			                           obj.getCURRENCY().trim());
			acctList.add(acct);
		}
				
		int split = 100;
		while(acctList.size() > 0) {
			List sl = acctList.subList(0, acctList.size() > split ? split : acctList.size());
			sl.clear();
		}
		
		
	}
	
	private void start(String[] args) {
		try {
			if (args.length == 1) {
	
				ServiceCenters scs = new ServiceCenters();
				java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");
		
				while (st.hasMoreTokens()) {
					String center = st.nextToken();
					if (scs.exists(center)) {
						ServiceCenter sc = scs.getServiceCenter(center);
						update(sc);
					}
				}
			} else
				System.out.println("wrong arguments");
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	
	public static void main(String[] args) {
		new AccountUpdater_II().start(args);
	}
}
	
	
	
		
		
	
		