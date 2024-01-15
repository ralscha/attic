package gtf.mbooker;

import java.util.Date;

public class ContingentLiability extends Liability {

	private String _type;
	private String _account_number;
	private String _expiry_date;

	public ContingentLiability() {
		clear();
	}


	public ContingentLiability(int _sequence_number, String _gtf_number, String _type,
                           	String _account_number, String _expiry_date,
                           	String _currency, double _amount, java.math.BigDecimal _exchange_rate,
                           	String _gtf_proc_center, String _customer_ref, String _gtf_type,
                           	String _dossier_item, String _bu_code,
                           	String _creator_pid, Date _timestamp) {

		super(_sequence_number, _gtf_number, _currency, _amount, _exchange_rate,
      		_gtf_proc_center, _customer_ref, _gtf_type, _dossier_item, _bu_code,
      		_creator_pid, _timestamp);

		this._type = _type;
		this._account_number = _account_number;
		this._expiry_date = _expiry_date;

	}

	public boolean equalsTo(Object obj) {

		if (obj instanceof ContingentLiability) {
			ContingentLiability cl = (ContingentLiability) obj;

			return (getType().equals(cl.getType()) && (getGtf_number().equals(cl.getGtf_number())) &&
        			getCurrency().equals(cl.getCurrency()) && (getAmount() == cl.getAmount()) &&
        			getAccount_number().equals(cl.getAccount_number()) &&
        			getExpiry_date().equals(cl.getExpiry_date()) &&
        			getGtf_proc_center().equals(cl.getGtf_proc_center()));
		}
		return false;
	}

	public String getType() {
		return _type;
	}

	public void setType(String _type) {
		this._type = _type.trim();
	}

	public String getAccount_number() {
		return _account_number;
	}

	public void setAccount_number(String _account_number) {
		this._account_number = _account_number.trim();
	}

	public String getExpiry_date() {
		return _expiry_date;
	}

	public java.util.GregorianCalendar getGregorianExpiry_date() {
		return ( new java.util.GregorianCalendar(
           		Integer.parseInt(_expiry_date.substring(0, 4)),
           		Integer.parseInt(_expiry_date.substring(4, 6)) - 1,
           		Integer.parseInt(_expiry_date.substring(6, 8))));
	}

	public void setExpiry_date(String _expiry_date) {
		this._expiry_date = _expiry_date.trim();
	}

	public void clear() {
		super.clear();
		_type = null;
		_account_number = null;
		_expiry_date = null;
	}
	
	private boolean checkDate() {
		java.util.Date date = dateFormat.parse(_expiry_date, new java.text.ParsePosition(0));
		return (date != null);
	}
	
	public boolean ok() {
		return ((super.ok()) 
					&& (_type != null) && (_type.length() == 1)
			       && (_account_number != null) && (_account_number.length() == 16)
			       && (_expiry_date != null) && checkDate());
	}

	

	public String getBookString() {
		StringBuffer sb = new StringBuffer();
		sb.append("t := MfwTransaction new: \'BookContLiabCS\' version: 1 session: (MfwSession default).");
		sb.append(LINE_SEPARATOR);
		sb.append("t addInputRecord: ((GtfClientRecordMapRegistry current newRecord: \'13011\')");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Sequence_Number put: "+_sequence_number+";");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Type put: $"+_type+";");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Gtf_Number put: "+_gtf_number+";");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Currency put: '"+_currency+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Amount put: "+form.format(_amount)+";");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Acct_Mgmt_Unit put: '"+_account_number.substring(0,4)+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Account_Number put: '"+_account_number.substring(4).trim()+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Expiry_Date put: (Date fromIntegerYYMMDD: "+
			_expiry_date.substring(6)+
			_expiry_date.substring(2,4)+
			_expiry_date.substring(0,2)+");");
		sb.append(LINE_SEPARATOR);
		
		sb.append("at: #Exchange_Rate put: "+_exchange_rate.doubleValue()+";");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Gtf_Proc_Center put: '"+_gtf_proc_center+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Customer_Ref put: '"+_customer_ref + "';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Gtf_Type put: '"+_gtf_type+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Dossier_Item put: '"+_dossier_item+"';");
		sb.append(LINE_SEPARATOR);
		sb.append("at: #Bu_Code put:'"+_bu_code+"'; yourself).");
		sb.append(LINE_SEPARATOR);
		sb.append("result := t execute.");
		sb.append(LINE_SEPARATOR);
		return sb.toString();
	}


	public String toString() {
		return "ContLiability (" + _sequence_number + " " + _type + " " + _gtf_number +
       		" " + _currency + " " + _amount + " " + 
       		_account_number + " " + _expiry_date + " " + _exchange_rate + " " +
       		_gtf_proc_center + " " + _customer_ref + " " + _gtf_type + " " +
       		_dossier_item + " " + _bu_code + ")";
	}

}