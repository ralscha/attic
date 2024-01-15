import java.util.*;
import COM.odi.util.*;

public class SWIFTHeader  implements java.io.Serializable {

	private String _TOSN;	
	private int _Send_Date_y, _Send_Date_m, _Send_Date_d;	
	private String _Address_Sender;
	private String _Session_Number;
	private String _Sequence_Number;
	private String _Address_Receiver;
	private String _Proc_Center;
	private String _Message_Type;
	private String _Duplicate;
	private String _Priority;
	private int _Receive_Date_y, _Receive_Date_m, _Receive_Date_d, _Receive_Date_hh, _Receive_Date_mm;
	
    private OSVectorList _Message;

	public SWIFTHeader() {
	    clear(); 	    
	}

	public SWIFTHeader(String _TOSN, Calendar _Send_Date, String _Address_Sender, String _Session_Number, String _Sequence_Number, String _Address_Receiver, String _Proc_Center, String _Message_Type, String _Duplicate, String _Priority, Calendar _Receive_Date, List message) {
                
		this._TOSN = _TOSN;
		
		this._Send_Date_y = _Send_Date.get(Calendar.YEAR);
		this._Send_Date_m = _Send_Date.get(Calendar.MONTH);
		this._Send_Date_d = _Send_Date.get(Calendar.DATE);
		
		
		this._Address_Sender = _Address_Sender;
		this._Session_Number = _Session_Number;
		this._Sequence_Number = _Sequence_Number;
		this._Address_Receiver = _Address_Receiver;
		this._Proc_Center = _Proc_Center;
		this._Message_Type = _Message_Type;
		this._Duplicate = _Duplicate;
		this._Priority = _Priority;
		
		this._Receive_Date_y = _Receive_Date.get(Calendar.YEAR);
		this._Receive_Date_m = _Receive_Date.get(Calendar.MONTH);
		this._Receive_Date_d = _Receive_Date.get(Calendar.DATE);
		this._Receive_Date_hh = _Receive_Date.get(Calendar.HOUR);
		this._Receive_Date_mm = _Receive_Date.get(Calendar.MINUTE);
		
	    _Message = new OSVectorList();
	    Iterator it = message.iterator();
	    while (it.hasNext()) 
	        _Message.add(it.next());

	}

    public String getKey() {
        return _TOSN;
    }

    public int hashCode() {
        return _TOSN.hashCode();
    }
    
	public void setTOSN(String val) {
		_TOSN = val;
	}
	
	public String getTOSN() {
		return _TOSN;
	}

	public void setSend_Date(Calendar val) {
		this._Send_Date_y = val.get(Calendar.YEAR);
		this._Send_Date_m = val.get(Calendar.MONTH);
		this._Send_Date_d = val.get(Calendar.DATE);
		
	}
	
	public Calendar getSend_Date() {
	    return (new GregorianCalendar(_Send_Date_y, _Send_Date_m, _Send_Date_d));
	}

	public void setAddress_Sender(String val) {
		_Address_Sender = val;
	}
	
	public String getAddress_Sender() {
		return _Address_Sender;
	}

	public void setSession_Number(String val) {
		_Session_Number = val;
	}
	
	public String getSession_Number() {
		return _Session_Number;
	}

	public void setSequence_Number(String val) {
		_Sequence_Number = val;
	}
	
	public String getSequence_Number() {
		return _Sequence_Number;
	}

    public void setAddress_Receiver(String val) {
		_Address_Receiver = val;
	}
	
	public String getAddress_Receiver() {
		return _Address_Receiver;
	}

	public void setProc_Center(String val) {
		_Proc_Center = val;
	}
	
	public String getProc_Center() {
    	return _Proc_Center;
	}

	public void setMessage_Type(String val) {
		_Message_Type = val;
	}
	
	public String getMessage_Type() {
		return _Message_Type;
	}

	public void setDuplicate(String val) {
		_Duplicate = val;
	}
	
	public String getDuplicate() {
		return _Duplicate;
	}

	public void setPriority(String val) {
		_Priority = val;
	}
	
	public String getPriority() {
		return _Priority;
	}

	public void setReceive_Date(Calendar val) {
		this._Receive_Date_y  = val.get(Calendar.YEAR);
		this._Receive_Date_m  = val.get(Calendar.MONTH);
		this._Receive_Date_d  = val.get(Calendar.DATE);
		this._Receive_Date_hh = val.get(Calendar.HOUR);
		this._Receive_Date_mm = val.get(Calendar.MINUTE);
		
	}
	
	public Calendar getReceive_Date() {
		return (new GregorianCalendar(_Receive_Date_y, _Receive_Date_m, _Receive_Date_d,
		                              _Receive_Date_hh, _Receive_Date_mm, 0));
	}
	
	public void setMessage(List message) {
	    _Message = new OSVectorList();
	    Iterator it = message.iterator();
	    while (it.hasNext()) 
	        _Message.add(it.next());
	}
	
	public List getMessage() {
	    return (OSVectorList)_Message.clone();
	}

	public void clear() {
		_TOSN = null;
		_Send_Date_y = _Send_Date_m = _Send_Date_d = 0;
		_Address_Sender = null;
		_Session_Number = null;
		_Sequence_Number = null;
		_Address_Receiver = null;
		_Proc_Center = null;
		_Message_Type = null;
		_Duplicate = null;
		_Priority = null;
		_Receive_Date_y = _Receive_Date_m = _Receive_Date_d = _Receive_Date_hh = _Receive_Date_mm = 0;
		_Message = null;
	}


	public String toString() {
		return "#SWIFTHeader("  + _TOSN + " " + _Send_Date_y+"."+_Send_Date_m+"."+_Send_Date_d + " " + _Address_Sender + " " + _Session_Number + " " + _Sequence_Number + " " + _Address_Receiver + " " + _Proc_Center + " " + _Message_Type + " " + _Duplicate + " " + _Priority + " " + _Receive_Date_y+"."+_Receive_Date_m+"."+_Receive_Date_d+" "+_Receive_Date_hh+":"+_Receive_Date_mm+")";
	}
}
