package gtf.crapa;

import java.io.*;
import java.util.*;
import java.math.*;
import common.util.sort.*;
import common.util.*;
import java.sql.*;

public class CrapaPageCreator {

	private int year, month;
	String branch;

	public CrapaPageCreator() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -1);
		int month = rightNow.get(Calendar.MONTH);
		int year  = rightNow.get(Calendar.YEAR);
		branch = "0835";		
	}

	public CrapaPageCreator(String branch, int year, int month) {
		this.branch = branch;
		this.year = year;
		this.month = month;
	}

	public void producePage(PrintWriter out) {
		BufferedReader dis;
		String line;

		BigDecimal total = new BigDecimal(0.0);
		BigDecimal totalBranch = new BigDecimal(0.0);

				
		try {
			ResultSet rs = CrapaDbManager.selectCrapaItems(branch, year, month);
	
			CrapaHTMLPage page = new CrapaHTMLPage(out, branch, year, month);		
			CrapaItem mCrapa = null;
			int number = 0;

			while(rs.next()) {
				CrapaItem ci = CrapaItem.createFromResultSet(rs);
				
				if (mCrapa == null)
					mCrapa = ci;
				
				if (mCrapa.equals(ci)) {
					total = total.add(ci.getAmount());
					number++;
				} else {
					page.addLine(mCrapa.getKst(), mCrapa.getLegalEntity(),
             					mCrapa.getCrapaCode(), number, total);

					mCrapa = ci;
					total = ci.getAmount();
					number = 1;
				}

				totalBranch = totalBranch.add(ci.getAmount());
	
			}

			if (mCrapa != null) {
				page.addLine(mCrapa.getKst(), mCrapa.getLegalEntity(),
             				mCrapa.getCrapaCode(), number, total);
				page.addTotalLine(totalBranch);
				
			}
			page.close();
			
			rs.close();

			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}

}