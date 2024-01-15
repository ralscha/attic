package credoc;


import java.io.*;

public class CredocRecordStream {
	protected Reader reader;
	protected int ch;

	protected static int TAB=9;
	protected static int LF=10;
	protected static int CR=13;

	protected boolean[] fieldIsRelevant = new boolean[CredocRecord.NUMBER_OF_FIELDS];
	
	public CredocRecordStream() {
		super();
	}
	
	public CredocRecordStream(Reader r) {
		this();
		reader = r;
		fieldIsRelevant = new boolean[CredocRecord.NUMBER_OF_FIELDS];
		fieldIsRelevant[CredocRecord.DOSSIER_NUMBER]= true;
		fieldIsRelevant[CredocRecord.LC_TYPE]= true;
		fieldIsRelevant[CredocRecord.ISSUANCE_DATE]= true;
		fieldIsRelevant[CredocRecord.EXPIRY_DATE]= true;
		fieldIsRelevant[CredocRecord.LIQUIDATION_DATE]= true;
		fieldIsRelevant[CredocRecord.A_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.A_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.A_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.CB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.CB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.CB_REFERENCE ]= true;
		fieldIsRelevant[CredocRecord.NB_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.NB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.NB_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.IB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.IB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.IB_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.RF_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.RF_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.RF_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.B_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.B_ADDRESS ]= true;
		fieldIsRelevant[CredocRecord.B_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.RB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.RB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.RB_REFERENCE]= true;
	}
	
	public void defineAllFieldsRelevant() {
		for(int i=0; i<fieldIsRelevant.length; i++) fieldIsRelevant[i]=true;
	}
	
	public void defineFieldsRelevantForDds() {
		for(int i=0; i<fieldIsRelevant.length; i++) fieldIsRelevant[i]=false;
		fieldIsRelevant[CredocRecord.DOSSIER_NUMBER]= true;
		fieldIsRelevant[CredocRecord.LC_TYPE]= true;
		fieldIsRelevant[CredocRecord.ISSUANCE_DATE]= true;
		fieldIsRelevant[CredocRecord.EXPIRY_DATE]= true;
		fieldIsRelevant[CredocRecord.LIQUIDATION_DATE]= true;
		fieldIsRelevant[CredocRecord.A_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.A_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.A_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.CB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.CB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.CB_REFERENCE ]= true;
		fieldIsRelevant[CredocRecord.NB_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.NB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.NB_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.IB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.IB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.IB_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.RF_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.RF_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.RF_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.B_CIF_NUMBER ]= true;
		fieldIsRelevant[CredocRecord.B_ADDRESS ]= true;
		fieldIsRelevant[CredocRecord.B_REFERENCE]= true;
		fieldIsRelevant[CredocRecord.RB_CIF_NUMBER]= true;
		fieldIsRelevant[CredocRecord.RB_ADDRESS]= true;
		fieldIsRelevant[CredocRecord.RB_REFERENCE]= true;
	}
	protected void getChar() {
		try{
			ch = reader.read();
		} catch (Exception e) { e.printStackTrace();
		}
	}

	/**
	 * Return Value:
	 *		CredocRecord or null if end of stream is reached
	 * Precondition
	 * 	ch is the character before the first character of the record
	 *		If the record exists
	 * Postcondition
	 *		Last character read is CR (mark for endOfRecord). 
	 * 		End of some record is reached
	 */
	public CredocRecord read() {
		getChar();
		if (ch == -1) return null;
		
		CredocRecord record = new CredocRecord();
		int i = 0;
		
		if (fieldIsRelevant[i]) {
			record.setField(i++, readField());
		} else {
			skipField();
		}
		while (ch == TAB) {
			getChar();
			if (fieldIsRelevant[i]) {
				record.setField(i, readField());
			} else {
				skipField();
			}
			i++;
		}
		return record;
	}
	
	/**
	 * Return the contents of the next record field. For a field that contains
	 * no characters or only blanks return null
	 *  
	 * Postcondition: ch contains first character of next field or <cr>
	 * if the record end is reached
	 */
	protected String readField() {
		StringWriter writer = new StringWriter();
		while (ch != TAB && ch != CR) {
			writer.write(ch);
			getChar();
		}
		String field = writer.toString();
		if (field.trim().equals(""))
			return null;
		else
			return field;
	}
	
	protected void skipField() {
		while(ch!=TAB && ch!=CR) {
			getChar();
		}
	}
}