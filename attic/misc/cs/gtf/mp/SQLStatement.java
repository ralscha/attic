
package gtf.mp;

public final class SQLStatement {

	public final static String ENGAGEMENT_SELECT_SQL_STR = 
		"WITH "+
		"liabilities AS ( "+
		"	SELECT  "+
		"		gtf_number, acct_mgmt_unit, account_number, value_date, type,  "+
		"		amount, currency, liability_type, is_effected, 1 AS engagement_type  "+
		"	FROM   "+
		"		gtflc.liability_booking	 "+
		"    UNION "+
		"	SELECT  "+
		"		gtf_number, acct_mgmt_unit, account_number, value_date, type,  "+
		"		amount, currency, 'C' AS liability_type, is_effected, 1 AS engagement_type  "+
		"	FROM   "+
		"		gtflc.dc_liability_book "+
 		"   UNION "+
		"	SELECT  "+
		"		gtf_number, acct_mgmt_unit, account_number, value_date, type,  "+
		"		amount, currency, 'C' AS liability_type, is_effected, 1 AS engagement_type  "+
		"	FROM   "+
		"		gtflc.rc_liability_book "+
		"    UNION "+
		"	SELECT  "+
		"		gtf_number, acct_mgmt_unit, account_number, value_date, type,  "+
		"		amount, currency, 'C' AS liability_type, is_effected, 3 AS engagement_type  "+
		"	FROM   "+
		"		gtflc.loi_liability_book "+
		"), "+

		"booked_liabilities AS ( "+
		"	SELECT  "+
 		"	    gtf_number, acct_mgmt_unit, account_number, value_date, engagement_type, "+
 		"    	SUM(CASE  "+
 		"		WHEN type='C' THEN -amount "+
  		"	    WHEN type='D' THEN amount "+
  		"		ELSE 0  "+
		"		END) AS amount, currency "+

		"	FROM  "+
		"		liabilities "+
		"	WHERE  "+
		"		is_effected=1 "+
		"	GROUP BY  "+
		"		gtf_number, acct_mgmt_unit, account_number, currency,  "+
		"		liability_type, value_date, engagement_type "+
		") "+

		"SELECT  "+
		"	b.gtf_number, b.acct_mgmt_unit, b.account_number, a.cif_number, "+
		"	b.value_date, b.currency, b.amount, engagement_type "+
		"FROM  "+
		"	booked_liabilities b, gtflc.account a "+
		"WHERE  "+
		"	b.amount>0 "+
		"	AND a.acct_mgmt_unit=b.acct_mgmt_unit "+
		"	AND a.account_number=b.account_number "+
		"ORDER BY  "+
		"	cif_number ";
}