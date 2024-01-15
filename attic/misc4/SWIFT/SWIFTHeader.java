import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class SWIFTHeader implements java.io.Serializable 
{

	private String _TOSN;
	private java.sql.Date _Send_Date;
	private String _Address_Sender;
	private String _Session_Number;
	private String _Sequence_Number;
	private String _Address_Receiver;
	private String _Proc_Center;
	private String _Message_Type;
	private String _Duplicate;
	private String _Priority;
	private java.sql.Date _Receive_Date;
	private java.sql.Time _Receive_Time;

	public SWIFTHeader() 
	{ 
	    clear(); 
	}

	public SWIFTHeader(String _TOSN)
	{
		this._TOSN = _TOSN;
	}

	public SWIFTHeader(String _TOSN, java.sql.Date _Send_Date, String _Address_Sender, String _Session_Number, String _Sequence_Number, String _Address_Receiver, String _Proc_Center, String _Message_Type, String _Duplicate, String _Priority, java.sql.Date _Receive_Date, java.sql.Time _Receive_Time)
	{
		this._TOSN = _TOSN;
		this._Send_Date = _Send_Date;
		this._Address_Sender = _Address_Sender;
		this._Session_Number = _Session_Number;
		this._Sequence_Number = _Sequence_Number;
		this._Address_Receiver = _Address_Receiver;
		this._Proc_Center = _Proc_Center;
		this._Message_Type = _Message_Type;
		this._Duplicate = _Duplicate;
		this._Priority = _Priority;
		this._Receive_Date = _Receive_Date;
		this._Receive_Time = _Receive_Time;
	}

	public void setTOSN(String val) 
	{
		_TOSN = val;
	}
	
	public String getTOSN() 
	{
		return _TOSN;
	}

	public void setSend_Date(java.sql.Date val) 
	{
		_Send_Date = val;
	}
	
	public java.sql.Date getSend_Date() 
	{
		return _Send_Date;
	}

	public void setAddress_Sender(String val) 
	{
		_Address_Sender = val;
	}
	
	public String getAddress_Sender() 
	{
		return _Address_Sender;
	}

	public void setSession_Number(String val) 
	{
		_Session_Number = val;
	}
	
	public String getSession_Number() 
	{
		return _Session_Number;
	}

	public void setSequence_Number(String val) 
	{
		_Sequence_Number = val;
	}
	
	public String getSequence_Number() 
	{
		return _Sequence_Number;
	}

	public void setAddress_Receiver(String val) 
	{
		_Address_Receiver = val;
	}
	
	public String getAddress_Receiver() 
	{
		return _Address_Receiver;
	}

	public void setProc_Center(String val) 
	{
		_Proc_Center = val;
	}
	
	public String getProc_Center() 
	{
		return _Proc_Center;
	}

	public void setMessage_Type(String val) 
	{
		_Message_Type = val;
	}
	
	public String getMessage_Type() 
	{
		return _Message_Type;
	}

	public void setDuplicate(String val) 
	{
		_Duplicate = val;
	}
	
	public String getDuplicate() 
	{
		return _Duplicate;
	}

	public void setPriority(String val) 
	{
		_Priority = val;
	}
	
	public String getPriority() 
	{
		return _Priority;
	}

	public void setReceive_Date(java.sql.Date val) 
	{
		_Receive_Date = val;
	}
	
	public java.sql.Date getReceive_Date() 
	{
		return _Receive_Date;
	}

	public void setReceive_Time(java.sql.Time val) 
	{
		_Receive_Time = val;
	}
	
	public java.sql.Time getReceive_Time() 
	{
		return _Receive_Time;
	}

	public void clear() 
	{
		_TOSN = null;
		_Send_Date = null;
		_Address_Sender = null;
		_Session_Number = null;
		_Sequence_Number = null;
		_Address_Receiver = null;
		_Proc_Center = null;
		_Message_Type = null;
		_Duplicate = null;
		_Priority = null;
		_Receive_Date = null;
		_Receive_Time = null;
	}


	public String toString() 
	{
		return "#SWIFTHeader("  + _TOSN + " " + _Send_Date + " " + _Address_Sender + " " + _Session_Number + " " + _Sequence_Number + " " + _Address_Receiver + " " + _Proc_Center + " " + _Message_Type + " " + _Duplicate + " " + _Priority + " " + _Receive_Date + " " + _Receive_Time		 + ")";
	}
}
