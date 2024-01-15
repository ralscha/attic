package gtf.ss.common;

import java.text.*;
import ViolinStrings.*;

public class SS_BANK_REF {

 	private static DecimalFormat form = null;
 	 
	static {
		form = new DecimalFormat("#,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);
	}
		
	
	private String SERVICECENTER;
	private int DOSSIER_NUMBER;
	private String BRANCH;
	private String BRANCH_GROUP;
	private String BANK_REF_NO;
	private String BANK_REF_NO_S;
	private String BANK_NAME;
	private String BANK_NAME_S;
	private String BANK_LOCATION;
	private String ISO_CODE;
	private java.math.BigDecimal AMOUNT;

	
	
	public SS_BANK_REF() {
		this.SERVICECENTER = null;
		this.DOSSIER_NUMBER = 0;
		this.BRANCH = null;
		this.BRANCH_GROUP = null;
		this.BANK_REF_NO = null;
		this.BANK_REF_NO_S = null;
		this.BANK_NAME = null;
		this.BANK_NAME_S = null;
		this.BANK_LOCATION = null;
		this.ISO_CODE = null;
		this.AMOUNT = null;
	
	}
	
	public SS_BANK_REF(String SERVICECENTER, int DOSSIER_NUMBER, String BRANCH, String BRANCH_GROUP, String BANK_REF_NO, String BANK_REF_NO_S, String BANK_NAME, String BANK_NAME_S, String BANK_LOCATION, String ISO_CODE, java.math.BigDecimal AMOUNT) {
		this.SERVICECENTER = SERVICECENTER;
		this.DOSSIER_NUMBER = DOSSIER_NUMBER;
		this.BRANCH = BRANCH;
		this.BRANCH_GROUP = BRANCH_GROUP;
		this.BANK_REF_NO = BANK_REF_NO;
		this.BANK_REF_NO_S = BANK_REF_NO_S;
		this.BANK_NAME = BANK_NAME;
		this.BANK_NAME_S = BANK_NAME_S;
		this.BANK_LOCATION = BANK_LOCATION;
		this.ISO_CODE = ISO_CODE;
		this.AMOUNT = AMOUNT;
	}

	public String getFormattedDossierNumber() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(BRANCH);
		sb.append(" ");
		sb.append(BRANCH_GROUP);
		sb.append("-");
		sb.append(Strings.rightJustify(String.valueOf(DOSSIER_NUMBER), 7, '0'));					
		return sb.toString();
	}

	public String getSERVICECENTER() {
		return SERVICECENTER;
	}

	public void setSERVICECENTER(String SERVICECENTER) {
		this.SERVICECENTER = SERVICECENTER;
	}

	public int getDOSSIER_NUMBER() {
		return DOSSIER_NUMBER;
	}

	public void setDOSSIER_NUMBER(int DOSSIER_NUMBER) {
		this.DOSSIER_NUMBER = DOSSIER_NUMBER;
	}

	public String getBRANCH() {
		return BRANCH;
	}

	public void setBRANCH(String BRANCH) {
		this.BRANCH = BRANCH;
	}

	public String getBRANCH_GROUP() {
		return BRANCH_GROUP;
	}

	public void setBRANCH_GROUP(String BRANCH_GROUP) {
		this.BRANCH_GROUP = BRANCH_GROUP;
	}

	public String getBANK_REF_NO() {
		return BANK_REF_NO;
	}

	public void setBANK_REF_NO(String BANK_REF_NO) {
		this.BANK_REF_NO = BANK_REF_NO;
	}

	public String getBANK_REF_NO_S() {
		return BANK_REF_NO_S;
	}

	public void setBANK_REF_NO_S(String BANK_REF_NO_S) {
		this.BANK_REF_NO_S = BANK_REF_NO_S;
	}

	public String getBANK_NAME() {
		return BANK_NAME;
	}

	public void setBANK_NAME(String BANK_NAME) {
		this.BANK_NAME = BANK_NAME;
	}

	public String getBANK_NAME_S() {
		return BANK_NAME_S;
	}

	public void setBANK_NAME_S(String BANK_NAME_S) {
		this.BANK_NAME_S = BANK_NAME_S;
	}

	public String getBANK_LOCATION() {
		return BANK_LOCATION;
	}

	public void setBANK_LOCATION(String BANK_LOCATION) {
		this.BANK_LOCATION = BANK_LOCATION;
	}

	public String getISO_CODE() {
		return ISO_CODE;
	}

	public void setISO_CODE(String ISO_CODE) {
		this.ISO_CODE = ISO_CODE;
	}

	public String getFormattedAmount() {
		if (AMOUNT.doubleValue() != 0.0)
			return form.format(AMOUNT);
		else
			return "";
	}
	
	public java.math.BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(java.math.BigDecimal AMOUNT) {
		this.AMOUNT = AMOUNT;
	}


	public String toString() {
		return "SS_BANK_REF("+ SERVICECENTER + " " + DOSSIER_NUMBER + " " + BRANCH + " " + BRANCH_GROUP + " " + BANK_REF_NO + " " + BANK_REF_NO_S + " " + BANK_NAME + " " + BANK_NAME_S + " " + BANK_LOCATION + " " + ISO_CODE + " " + AMOUNT+")";
	}
}
