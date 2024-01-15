
public class FirmLiability extends Liability {
	private String _deb_acct_number;
	private String _cre_acct_number;
	private String _value_date;

	public FirmLiability() {
		clear();
	}

	public FirmLiability(int _sequence_number, String _gtf_number, String _currency,
                     	double _amount, String _deb_acct_number,
                     	String _cre_acct_number, String _value_date,
                     	double _exchange_rate, String _gtf_proc_center, String _customer_ref,
                     	String _gtf_type, String _dossier_item, String _bu_code) {

		super(_sequence_number, _gtf_number, _currency, _amount, _exchange_rate,
      		_gtf_proc_center, _customer_ref, _gtf_type, _dossier_item, _bu_code);
		this._deb_acct_number = _deb_acct_number;
		this._cre_acct_number = _cre_acct_number;
		this._value_date = _value_date;
	}

	public boolean equalsTo(Object obj) {
		if (obj instanceof FirmLiability) {
			FirmLiability fl = (FirmLiability) obj;
			return ((getGtf_number().equals(fl.getGtf_number())) &&
        			getCurrency().equals(fl.getCurrency()) && (getAmount() == fl.getAmount()) &&
        			getDeb_acct_number().equals(fl.getDeb_acct_number()) &&
        			getCre_acct_number().equals(fl.getCre_acct_number()) &&
        			getValue_date().equals(fl.getValue_date()) &&
        			getGtf_proc_center().equals(fl.getGtf_proc_center()));
		}
		return false;
	}

	public String getDeb_acct_number() {
		return _deb_acct_number;
	}

	public void setDeb_acct_number(String _deb_acct_number) {
		this._deb_acct_number = _deb_acct_number.trim();
	}


	public String getCre_acct_number() {
		return _cre_acct_number;
	}

	public void setCre_acct_number(String _cre_acct_number) {
		this._cre_acct_number = _cre_acct_number.trim();
	}

	public String getValue_date() {
		return _value_date;
	}

	public java.util.GregorianCalendar getGregorianValue_date() {
		return ( new java.util.GregorianCalendar( Integer.parseInt(_value_date.substring(0, 4)),
         		Integer.parseInt(_value_date.substring(4, 6)) - 1,
         		Integer.parseInt(_value_date.substring(6, 8))));
	}

	public void setValue_date(String _value_date) {
		this._value_date = _value_date.trim();
	}

	public void clear() {
		super.clear();
		_deb_acct_number = null;
		_cre_acct_number = null;
		_value_date = null;
	}
	
	private boolean checkDate() {
		java.util.Date date = dateFormat.parse(_value_date, new java.text.ParsePosition(0));
		return (date != null);
	}
	
	public boolean ok() {
		return (super.ok() 
					&& (_deb_acct_number != null) && (_deb_acct_number.length() == 16)
			       && (_cre_acct_number != null) && (_cre_acct_number.length() == 16)
			       && (_value_date != null) && checkDate());
	}


	public String getBookString() {
		StringBuffer sb = new StringBuffer();
		sb.append("t := MfwTransaction new: \'BookFirmLiabCS\' version: 1 session: (MfwSession default).\r\n");
		sb.append("t addInputRecord: ((GtfClientRecordMapRegistry current newRecord: \'13012\')\r\n");
		sb.append("at: #Sequence_Number put: "+_sequence_number+";\r\n");
		sb.append("at: #Gtf_Number put: "+_gtf_number+";\r\n");
		sb.append("at: #Currency put: '"+_currency+"';\r\n");
		sb.append("at: #Amount put: "+_amount+";\r\n");
		sb.append("at: #Deb_Acct_Mgmt_Unit put: '"+_deb_acct_number.substring(0,4)+"';\r\n");
		sb.append("at: #Deb_Account_Number put: '"+_deb_acct_number.substring(4).trim()+"';\r\n");
		sb.append("at: #Cre_Acct_Mgmt_Unit put: '"+_cre_acct_number.substring(0,4)+"';\r\n");
		sb.append("at: #Cre_Account_Number put: '"+_cre_acct_number.substring(4)+"';\r\n");
		sb.append("at: #Value_Date put: (Date fromIntegerYYMMDD: "+
			_value_date.substring(6)+
			_value_date.substring(2,4)+
			_value_date.substring(0,2)+");\r\n");
		sb.append("at: #Exchange_Rate put: "+_exchange_rate+";\r\n");
		sb.append("at: #Gtf_Proc_Center put: '"+_gtf_proc_center+"';\r\n");
		sb.append("at: #Customer_Ref put: '"+_customer_ref + "';\r\n");
		sb.append("at: #Gtf_Type put: '"+_gtf_type+"';\r\n");
		sb.append("at: #Dossier_Item put: '"+_dossier_item+"';\r\n");
		sb.append("at: #Bu_Code put:'"+_bu_code+"'; yourself).\r\n");
		sb.append("result := t execute.\r\n");
		return sb.toString();
	}

	public String toString() {
		return "FirmLiability (" + _sequence_number + " " + _gtf_number + " " + _currency +
       		" " + _amount + " " +  _deb_acct_number + " " +
       		 _cre_acct_number + " " + _value_date + " " +
       		_exchange_rate + " " + _gtf_proc_center + " " + _customer_ref + " " +
       		_gtf_type + " " + _dossier_item + " " + _bu_code + ")";
	}

}