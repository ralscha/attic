package gtf.mbooker;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;

public abstract class Liability {
	protected int _sequence_number;
	protected String _gtf_number;
	protected String _currency;
	protected double _amount;
	protected java.math.BigDecimal _exchange_rate;
	protected String _gtf_proc_center;
	protected String _customer_ref;
	protected String _gtf_type;
	protected String _dossier_item;
	protected String _bu_code;
	protected String _creator_pid;
	protected Date _timestamp;

	protected final static String LINE_SEPARATOR = System.getProperty("line.separator");

	protected final static SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
	protected final static DecimalFormat form = new DecimalFormat("#0.000");

	public Liability() {
		clear();
	}


	public Liability(int _sequence_number, String _gtf_number, String _currency,
                 	double _amount, java.math.BigDecimal _exchange_rate, String _gtf_proc_center,
                 	String _customer_ref, String _gtf_type, String _dossier_item, String _bu_code, 
                 	String _creator_pid, Date _timestamp) {
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
		this._creator_pid = _creator_pid;
		this._timestamp = _timestamp;
	}


	public abstract boolean equalsTo(Object obj);


	/* for sorting */
	public boolean compare(Liability liab) {
		if (_gtf_number.equals(liab.getGtf_number()))
			if (_sequence_number == liab.getSequence_number())
				return (_gtf_proc_center.compareTo(liab.getGtf_proc_center()) == -1);
			else
				return (_sequence_number < liab.getSequence_number());
		else
			return (_gtf_number.equals(liab.getGtf_number()));

	}

	public int getSequence_number() {
		return _sequence_number;
	}

	public void setSequence_number(int _sequence_number) {
		this._sequence_number = _sequence_number;
	}

	public String getGtf_number() {
		return _gtf_number;
	}

	public void setGtf_number(String _gtf_number) {
		this._gtf_number = _gtf_number.trim();
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

	public java.math.BigDecimal getExchange_rate() {
		return _exchange_rate;
	}

	public void setExchange_rate(java.math.BigDecimal _exchange_rate) {
		this._exchange_rate = _exchange_rate;
	}

	public String getGtf_proc_center() {
		return _gtf_proc_center;
	}

	public void setGtf_proc_center(String _gtf_proc_center) {
		this._gtf_proc_center = _gtf_proc_center.trim();
	}

	public String getCustomer_ref() {
		return _customer_ref;
	}

	public void setCustomer_ref(String _customer_ref) {
		this._customer_ref = _customer_ref.trim();
	}

	public String getGtf_type() {
		return _gtf_type;
	}

	public void setGtf_type(String _gtf_type) {
		this._gtf_type = _gtf_type.trim();
	}

	public String getDossier_item() {
		return _dossier_item;
	}

	public void setDossier_item(String _dossier_item) {
		this._dossier_item = _dossier_item.trim();
	}

	public String getBu_code() {
		return _bu_code;
	}

	public void setBu_code(String _bu_code) {
		this._bu_code = _bu_code.trim();
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public void setCreator(String pid) {
		_creator_pid = pid;
	}
	
	public String getCreator() {
		return _creator_pid;
	}
	
	public void clear() {
		_sequence_number = 0;
		_gtf_number = null;
		_currency = null;
		_amount = 0;
		_exchange_rate = null;
		_gtf_proc_center = null;
		_customer_ref = "";
		_gtf_type = null;
		_dossier_item = "";
		_bu_code = null;
		_creator_pid = common.util.AppProperties.getStringProperty("user");
		_timestamp = new Date();
	}

	public boolean ok() {
		return ((_sequence_number > 0) 
				&& (_gtf_number != null) && (!_gtf_number.equals(""))
			     && (_currency != null) && (_currency.length() == 3)	
			     && (_amount > 0.0)
			     && (_exchange_rate != null) 
			     && (_gtf_proc_center != null) && (_gtf_proc_center.length() == 4)
			     && (_gtf_type != null) && (!_gtf_type.equals(""))
			     && (_bu_code != null)) && (!_bu_code.equals(""));
	}

	public String toString() {
		return "Liability(" + _sequence_number + " " + _gtf_number + " " + _currency +
       		" " + _amount + " " + _exchange_rate + " " + _gtf_proc_center + " " +
       		_customer_ref + " " + _gtf_type + " " + _dossier_item + " " + _bu_code + ")";
	}

}