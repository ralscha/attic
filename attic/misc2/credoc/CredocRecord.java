package credoc;


public class CredocRecord {
	public String[] fields;
	public final static int NUMBER_OF_FIELDS=79;
	
	//Record field selectors
	//Values: Sequence number of the field within the CREDOC2000 record
	//Counting starts with 0
	public final static int DOSSIER_NUMBER = 0;
	public final static int LC_TYPE = 1;
	public final static int ISSUANCE_DATE = 2;
	public final static int EXPIRY_DATE = 3;
	public final static int LIQUIDATION_DATE = 4;
	public final static int A_CIF_NUMBER = 5;
	public final static int A_ACCOUNT_NUMBER = 6;
	public final static int A_ADDRESS = 7;
	public final static int A_REFERENCE = 8;
	public final static int CB_CIF_NUMBER = 11;
	public final static int CB_ACCOUNT_NUMBER = 12;
	public final static int CB_ADDRESS = 14;
	public final static int CB_REFERENCE = 15;
	public final static int NB_CIF_NUMBER = 18;
	public final static int NB_ACCOUNT_NUMBER = 19;
	public final static int NB_ADDRESS = 21;
	public final static int NB_REFERENCE = 22;
	public final static int IB_CIF_NUMBER = 25;
	public final static int IB_ACCOUNT_NUMBER = 26;
	public final static int IB_ADDRESS = 28;
	public final static int IB_REFERENCE = 29;
	public final static int RF_CIF_NUMBER = 32;
	public final static int RF_ACCOUNT_NUMBER = 33;
	public final static int RF_ADDRESS = 35;
	public final static int RF_REFERENCE = 36;
	public final static int B_CIF_NUMBER = 39;
	public final static int B_ACCOUNT_NUMBER = 40;
	public final static int B_ADDRESS = 41;
	public final static int B_REFERENCE = 42;
	public final static int RB_CIF_NUMBER = 45;
	public final static int RB_ACCOUNT_NUMBER = 46;
	public final static int RB_ADDRESS = 48;
	public final static int RB_REFERENCE = 49;
	public final static int AMOUNT_CURRENCY = 56;
	public final static int AMOUNT = 57;
	
	public CredocRecord() {
		fields = new String[NUMBER_OF_FIELDS];
	}
	
	public String getField(int index) {
		return fields[index];
	}
	
	public int length() {
		int len = 0;
	
		for (int i = 1; i < fields.length; i++) {
			len += fields[i].length() + 1;
		}
		return (++len);
	}
	
	public void setField(int index, String value) {
		fields[index]=value;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
	
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			sb.append((char)CredocRecordStream.TAB);
			if(fields[i]!=null)	sb.append(fields[i]);
		}
		sb.append((char)CredocRecordStream.CR);
		return sb.toString();
	}
}