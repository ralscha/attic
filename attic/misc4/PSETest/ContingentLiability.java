
public class ContingentLiability extends Liability {

    private String _type;
	private String _acct_mgmt_unit;
	private String _account_number;
	private String _expiry_date;
	
	public ContingentLiability() {
	    clear();
    }	
    
	public ContingentLiability(int _gtf_number, String _gtf_proc_center) {
	    super(_gtf_number, _gtf_proc_center);
	}
	
	public ContingentLiability(int _sequence_number, String _type, int _gtf_number, 
	                           String _currency, double _amount, String _acct_mgmt_unit, 
	                           String _account_number, String _expiry_date, double _exchange_rate, 
	                           String _gtf_proc_center, String _customer_ref, String _gtf_type, 
	                           String _dossier_item, String _bu_code) {
		
		super(_sequence_number, _gtf_number, _currency, _amount, _exchange_rate, 
		      _gtf_proc_center, _customer_ref, _gtf_type, _dossier_item, _bu_code);
		
		this._type = _type;
        this._acct_mgmt_unit = _acct_mgmt_unit;
		this._account_number = _account_number;
		this._expiry_date = _expiry_date;

	}

    public boolean equals(Object obj) {
	    if (obj instanceof ContingentLiability) {
	        ContingentLiability cl = (ContingentLiability)obj;
	        
    	    return ( getType().equals(cl.getType()) && (getGtf_number() == cl.getGtf_number()) &&
    	             getCurrency().equals(cl.getCurrency()) && (getAmount() == cl.getAmount()) &&
    	             getAcct_mgmt_unit().equals(cl.getAcct_mgmt_unit()) && 
    	             getAccount_number().equals(cl.getAccount_number()) &&
    	             getExpiry_date().equals(cl.getExpiry_date()) &&
    	             getGtf_proc_center().equals(cl.getGtf_proc_center()) );
	    }
    	return false;
    }

	public String getType() {
		return _type;
	}

	public void setType(String _type) {
		this._type = _type;
	}

	public String getAcct_mgmt_unit() {
		return _acct_mgmt_unit;
	}

	public void setAcct_mgmt_unit(String _acct_mgmt_unit) {
		this._acct_mgmt_unit = _acct_mgmt_unit;
	}

	public String getAccount_number() {
		return _account_number;
	}

	public void setAccount_number(String _account_number) {
		this._account_number = _account_number;
	}

	public String getExpiry_date() {
		return _expiry_date;
	}
	
	public java.util.GregorianCalendar getGregorianExpiry_date() {
	    return (new java.util.GregorianCalendar(Integer.parseInt(_expiry_date.substring(0,4)),
                                      Integer.parseInt(_expiry_date.substring(4,6))-1,
                                      Integer.parseInt(_expiry_date.substring(6,8))));
	}
	
	public void setExpiry_date(String _expiry_date) {
		this._expiry_date = _expiry_date;
	}

	public void clear() {
	    super.clear();
	    _type = null;
		_acct_mgmt_unit = null;
		_account_number = null;
		_expiry_date = null;
	}

	public String toString() {
		return "ContLiability (" + _sequence_number + " " + _type + " " + _gtf_number + " " + _currency + " " + _amount + " " + _acct_mgmt_unit + " " + _account_number + " " + _expiry_date + " " + _exchange_rate + " " + _gtf_proc_center + " " + _customer_ref + " " + _gtf_type + " " + _dossier_item + " " + _bu_code + ")";
	}
	
	public String toExternalString() {
	    return (_sequence_number + "\t" + _type + "\t" + _gtf_number+ "\t" + _currency + "\t" + 
	            _amount + "\t" + _acct_mgmt_unit+ "\t" + _account_number + "\t" + _expiry_date + 
	            "\t" + _exchange_rate + "\t" + _gtf_proc_center + "\t" + _customer_ref + "\t" + 
	            _gtf_type + "\t" + _dossier_item + "\t" + _bu_code);
	}
	
}
