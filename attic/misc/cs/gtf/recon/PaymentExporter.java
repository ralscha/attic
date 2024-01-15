package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.PAYMENT_TMP;
import gtf.db.PAYMENT_TMPTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class PaymentExporter implements gtf.common.Constants {

	private List paymentList;

	private void deleteContents(ServiceCenter sc) {
		try {
			PAYMENT_TMPTable ptt = new PAYMENT_TMPTable(sc.getConnection());
			Log.log(sc.getShortName() + " delete payment_tmp rows : " + ptt.delete());
		} catch (SQLException sqle) {
			Log.log(sc.getShortName() + sqle.toString());
		}
	}

	private boolean export(ServiceCenter sc) throws SQLException, IOException {

		String fileName = AppProperties.getStringProperty(sc.getShortName()+P_PAYMENT_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_PAYMENT_FILE property not found");
			return false;
		}

		String datapath = AppProperties.getStringProperty(sc.getShortName()+P_PAYMENT_PATH);
		if (datapath == null) {
			Log.log(sc.getShortName() + " P_PAYMENT_PATH property not found");
			datapath = "";
		}
		
		File dataFile = new File(datapath + fileName + fileDateFormat.format(new java.util.Date()));
		PrintWriter br = new PrintWriter(new FileWriter(dataFile));
		
		readData(sc.getConnection(), br);
		br.close();

		if (!TEST)
			deleteContents(sc);
			
		sc.closeConnection();
		return true;
	}

	public static void main(String[] args) {
		int rc = 1;
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
	
			if (args.length == 1) {
	
				ServiceCenters scs = new ServiceCenters();
				java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");
	
				List scList = new ArrayList();
	
				while (st.hasMoreTokens()) {
					String center = st.nextToken();
					if (scs.exists(center)) {
						ServiceCenter sc = scs.getServiceCenter(center);
						scList.add(sc);
					}
				}
				if (!scList.isEmpty())
					if (new PaymentExporter().start(scList))
						rc = 0;
					else
						rc = 1;
				else
					Log.log("payment exporter: no service centers specified");
			} else
				Log.log("payment exporter: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}

	}


	private void readData(Connection conn, PrintWriter out) throws SQLException {
		PAYMENT_TMPTable ptt = new PAYMENT_TMPTable(conn);
		StringBuffer sb = new StringBuffer();

		Iterator it = ptt.select();
		while (it.hasNext()) {
			PAYMENT_TMP obj = (PAYMENT_TMP) it.next();

			sb.setLength(0);

			sb.append(Strings.leftJustify(String.valueOf(obj.getKEY()), 3));
			sb.append(";");
			sb.append(Strings.rightJustify(obj.getGTF_NUMBER().trim(), 7, '0'));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getGTF_TYPE().trim(), 20));
			sb.append(";");
			sb.append(Strings.leftJustify(String.valueOf(obj.getSEQUENCE_NUMBER()), 3));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getTYPE().trim(), 3));
			sb.append(";");
			sb.append(Strings.rightJustify(obj.getTX_AMOUNT().toString(), 18));
			sb.append(";");
			sb.append(Strings.rightJustify(obj.getACCT_AMOUNT().toString(), 18));
			sb.append(";");
			sb.append( Strings.leftJustify(obj.getACCT_MGMT_UNIT() +
                               			obj.getACCOUNT_NUMBER().trim(), 24));
			sb.append(";");

			if (obj.getVALUE_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getVALUE_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			if (obj.getORDER_ENTRY_DATE() != null)
				sb.append(
  				Strings.leftJustify(dateFormat.format(obj.getORDER_ENTRY_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			if (obj.getORDER_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getORDER_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			sb.append(Strings.rightJustify(obj.getEXCHANGE_RATE().toString(), 18));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getTX_CURRENCY_ISO().trim(), 3));
			sb.append(";");
			sb.append(Strings.leftJustify(String.valueOf(obj.getTX_CURRENCY_PROP()), 4));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getACCT_CURRENCY().trim(), 3));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getTICKET_NUMBER().trim(), 7));
			sb.append(";");
			sb.append(Strings.rightJustify(obj.getINTERNAL_RATE().toString(), 18));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getPRIORITY().trim(), 1));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getGTF_PROC_CENTER().trim(), 4));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getREFERENCE_NUMBER().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getRELATED_REF_NUMBER().trim(), 16));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getANNOUNCE_NUMBER().trim(), 9));
			sb.append(";");

			if (obj.getFIXING_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getFIXING_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			sb.append(Strings.leftJustify(String.valueOf(obj.getFIXING_NUMBER()), 10));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getBU_CODE().trim(), 4));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getBC_NUMBER().trim(), 11));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getCIF_NUMBER().trim(), 12));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getPAY().trim(), 1));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_1().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_2().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_3().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_4().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_5().trim(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_6().trim(), 35));

			paymentList.add(sb.toString());
			out.println(sb.toString());
		}
	}
	protected boolean start(List scList) {

		paymentList = new ArrayList();
		try {

			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					if (export(sc))
						Log.log(sc.getShortName() + " payment export successful");
					else
						Log.log(sc.getShortName() + " payment export not successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}


			if (!paymentList.isEmpty()) {
				String cdpath = AppProperties.getStringProperty(P_CD_PATH);
				if (cdpath == null) {
					Log.log(sc.getShortName() + " P_CD_PATH property not found");
					return false;
				}

				String fileName = AppProperties.getStringProperty(P_PAYMENT_TRAN_FILE);
				if (fileName == null) {
					Log.log(sc.getShortName() + " P_PAYMENT_TRAN_FILE property not found");
					return false;
				}

				File tranFile = new File(cdpath + fileName);
				if (tranFile.exists())
					tranFile.delete();

				PrintWriter br = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
				Iterator its = paymentList.iterator();
				while (its.hasNext()) {
					br.println((String) its.next());
				}
				br.close();
				return true;
			} else {
				//empty
				return false;
			}
		} catch (Exception e) {
			Log.log(e.toString());
			return false;
		}
	}
}