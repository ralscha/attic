
public class FirmLiability extends Liability {
	private String _deb_acct_mgmt_unit;
	private String _deb_acct_number;
	private String _cre_acct_mgmt_unit;
	private String _cre_acct_number;
	private String _value_date;
	
	public FirmLiability() { 	    
	    clear();
	}
	
	public FirmLiability(int _gtf_number, String _gtf_proc_center) {
		super(_gtf_number, _gtf_proc_center);
	}
	
	public FirmLiability(int _sequence_number, int _gtf_number, String _currency, double _amount, 
	                     String _deb_acct_mgmt_unit, String _deb_acct_number, 
	                     String _cre_acct_mgmt_unit, String _cre_acct_number, 
	                     String _value_date, double _exchange_rate, String _gtf_proc_center, 
	                     String _customer_ref, String _gtf_type, String _dossier_item, 
	                     String _bu_code) {
        
        super(_sequence_number, _gtf_number, _currency, _amount, _exchange_rate,
		      _gtf_proc_center, _customer_ref, _gtf_type, _dossier_item, _bu_code);		
		this._deb_acct_mgmt_unit = _deb_acct_mgmt_unit;
		this._deb_acct_number = _deb_acct_number;
		this._cre_acct_mgmt_unit = _cre_acct_mgmt_unit;
		this._cre_acct_number = _cre_acct_number;
		this._value_date = _value_date;
	}
    
    public boolean equals(Object obj) {
	    if (obj instanceof FirmLiability) {
	        FirmLiability fl = (FirmLiability)obj;
    	    return ( (getGtf_number() == fl.getGtf_number()) &&
    	             getCurrency().equals(fl.getCurrency()) && (getAmount() == fl.getAmount()) &&
    	             getDeb_acct_mgmt_unit().equals(fl.getDeb_acct_mgmt_unit()) && 
    	             getDeb_acct_number().equals(fl.getDeb_acct_number()) &&
    	             getCre_acct_mgmt_unit().equals(fl.getCre_acct_mgmt_unit()) && 
    	             getCre_acct_number().equals(fl.getCre_acct_number()) &&
    	             getValue_date().equals(fl.getValue_date()) &&    	             
    	             getGtf_proc_center().equals(fl.getGtf_proc_center()) );
	    }
    	return false;
    }

	public String getDeb_acct_mgmt_unit() {
		return _deb_acct_mgmt_unit;
	}

	public void setDeb_acct_mgmt_unit(String _deb_acct_mgmt_unit) {
		this._deb_acct_mgmt_unit = _deb_acct_mgmt_unit;
	}

	public String getDeb_acct_number() {
		return _deb_acct_number;
	}

	public void setDeb_acct_number(String _deb_acct_number) {
		this._deb_acct_number = _deb_acct_number;
	}

	public String getCre_acct_mgmt_unit() {
		return _cre_acct_mgmt_unit;
	}

	public void setCre_acct_mgmt_unit(String _cre_acct_mgmt_unit) {
		this._cre_acct_mgmt_unit = _cre_acct_mgmt_unit;
	}

	public String getCre_acct_number() {
		return _cre_acct_number;
	}

	public void setCre_acct_number(String _cre_acct_number) {
		this._cre_acct_number = _cre_acct_number;
	}

	public String getValue_date() {
		return _value_date;
	}
	
	public java.util.GregorianCalendar getGregorianValue_date() {
	    return (new java.util.GregorianCalendar(Integer.parseInt(_value_date.substring(0,4)),
                                      Integer.parseInt(_value_date.substring(4,6))-1,
                                      Integer.parseInt(_value_date.substring(6,8))));
	}

	public void setValue_date(String _value_date) {
		this._value_date = _value_date;
	}
	
	public void clear() {
	    super.clear();
		_deb_acct_mgmt_unit = null;
		_deb_acct_number = null;
		_cre_acct_mgmt_unit = null;
		_cre_acct_number = null;
		_value_date = null;
	}

	public String toString() {
		return "FirmLiability (" + _sequence_number + " " + _gtf_number + " " + _currency + " " + _amount + " " + _deb_acct_mgmt_unit + " " + _deb_acct_number + " " + _cre_acct_mgmt_unit + " " + _cre_acct_number + " " + _value_date + " " + _exchange_rate + " " + _gtf_proc_center + " " + _customer_ref + " " + _gtf_type + " " + _dossier_item + " " + _bu_code + ")";
	}
	
	public String toExternalString() {
		return (_sequence_number + "\t" + _gtf_number + "\t" + _currency + "\t" + _amount + "\t" + 
		        _deb_acct_mgmt_unit + "\t" + _deb_acct_number + "\t" + _cre_acct_mgmt_unit + "\t" + 
		        _cre_acct_number + "\t" + _value_date + "\t" + _exchange_rate + "\t" + _gtf_proc_center + 
		        "\t" + _customer_ref + "\t" + _gtf_type + "\t" + _dossier_item + "\t" + _bu_code);
	    
	}

}
