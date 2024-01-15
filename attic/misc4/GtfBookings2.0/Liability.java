
public abstract class Liability {
	protected int    _sequence_number;
	protected int    _gtf_number;
	protected String _currency;
	protected double _amount;
	protected double _exchange_rate;
	protected String _gtf_proc_center;
	protected String _customer_ref;
	protected String _gtf_type;
	protected String _dossier_item;
	protected String _bu_code;
    private boolean _duplicate;

	
	public Liability() { 
	    clear();
	}
	
	public Liability(int _gtf_number, String _gtf_proc_center) {
		this._gtf_number = _gtf_number;
		this._gtf_proc_center = _gtf_proc_center;
		_duplicate = false;
	}
			
	public Liability(int _sequence_number, int _gtf_number, String _currency, double _amount, 
	                 double _exchange_rate, String _gtf_proc_center, String _customer_ref, 
	                 String _gtf_type, String _dossier_item, String _bu_code) {
		this._sequence_number = _sequence_number;
		this._gtf_number = _gtf_number;
		this._currency = _currency;
		this._amount = _amount;
		this._exchange_rate = _exchange_rate;
		this._gtf_proc_center = _gtf_proc_center;
		this._customer_ref = _customer_ref;
		this._gtf_type = _gtf_type;
		this._dossier_item = _dossier_item;
		this._bu_code = _bu_code;
		_duplicate = false;		
	}
          
       
    public abstract boolean equalsTo(Object obj) ;    
    public abstract boolean hasEmptyAccountNumbers() ;
    
    /* key for hashmap 
    public Integer getKey() {
        return (new Integer((1000*_gtf_number) + _gtf_proc_center.hashCode()));
    }
    
    public static Integer createKey(int gtfNumber, String branch) {
        return (new Integer((1000*gtfNumber) + branch.hashCode()));
    }*/
    
    /* for sorting */
    public boolean compare(Liability liab)
    {
        if (_gtf_number == liab.getGtf_number())
            if (_sequence_number == liab.getSequence_number())
                return (_gtf_proc_center.hashCode() < liab.getGtf_proc_center().hashCode());
            else
                return (_sequence_number < liab.getSequence_number());
        else
            return (_gtf_number == liab.getGtf_number());
            
    }
    
    
    public void setDuplicateFlag() {
        _duplicate = true;
    }

    public boolean getDuplicateFlag() {
        return _duplicate;
    }

	public int getSequence_number() {
		return _sequence_number;
	}

	public void setSequence_number(int _sequence_number) {
		this._sequence_number = _sequence_number;
	}

	public int getGtf_number() {
		return _gtf_number;
	}

	public void setGtf_number(int _gtf_number) {
		this._gtf_number = _gtf_number;
	}

	public String getCurrency() {
		return _currency;
	}

	public void setCurrency(String _currency) {
		this._currency = _currency;
	}

	public double getAmount() {
		return _amount;
	}

	public void setAmount(double _amount) {
		this._amount = _amount;
	}

	public double getExchange_rate() {
		return _exchange_rate;
	}

	public void setExchange_rate(double _exchange_rate) {
		this._exchange_rate = _exchange_rate;
	}

	public String getGtf_proc_center() {
		return _gtf_proc_center;
	}

	public void setGtf_proc_center(String _gtf_proc_center) {
		this._gtf_proc_center = _gtf_proc_center;
	}

	public String getCustomer_ref() {
		return _customer_ref;
	}

	public void setCustomer_ref(String _customer_ref) {
		this._customer_ref = _customer_ref;
	}

	public String getGtf_type() {
		return _gtf_type;
	}

	public void setGtf_type(String _gtf_type) {
		this._gtf_type = _gtf_type;
	}

	public String getDossier_item() {
		return _dossier_item;
	}

	public void setDossier_item(String _dossier_item) {
		this._dossier_item = _dossier_item;
	}

	public String getBu_code() {
		return _bu_code;
	}

	public void setBu_code(String _bu_code) {
		this._bu_code = _bu_code;
	}

	public void clear() {
		_sequence_number = 0;
		_gtf_number = 0;
		_currency = null;
		_amount = 0;
		_exchange_rate = 0;
		_gtf_proc_center = null;
		_customer_ref = null;
		_gtf_type = null;
		_dossier_item = null;
		_bu_code = null;
		_duplicate = false;
	}

	public String toString() {
		return "Liability(" + _sequence_number + " " + _gtf_number + " " + _currency + " " + 
		        _amount + " " + _exchange_rate + " " + _gtf_proc_center + " " + _customer_ref + " " + 
		        _gtf_type + " " + _dossier_item + " " + _bu_code + ")";
	}
	
}
