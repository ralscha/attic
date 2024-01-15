
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class SWIFTLines implements java.io.Serializable 
{
	private String _TOSN;
	private int _Lineno;
	private String _Field_Tag;
	private String _Msg_Line;

	public SWIFTLines() 
	{ 
	    clear(); 
	}

	public SWIFTLines(String _TOSN, int _Lineno) 
	{
		this._TOSN   = _TOSN;
		this._Lineno = _Lineno;
	}
	
	public SWIFTLines(String _TOSN, int _Lineno, String _Field_Tag, String _Msg_Line) 
	{
		this._TOSN      = _TOSN;
		this._Lineno    = _Lineno;
		this._Field_Tag = _Field_Tag;
		this._Msg_Line  = _Msg_Line;
	}
	
	public void setTOSN(String val) 
	{
		_TOSN = val;
	}
	
	public String getTOSN() 
	{
		return _TOSN;
	}

	public void setLineno(int val) 
	{
		_Lineno = val;
	}
	
	public int getLineno() 
	{
		return _Lineno;
	}

	public void setField_Tag(String val) 
	{
		_Field_Tag = val;
	}

	public String getField_Tag() 
	{
		return _Field_Tag;
	}

	public void setMsg_Line( String val ) 
	{
		_Msg_Line = val;
	}

	public String getMsg_Line() 
	{
		return _Msg_Line;
	}


	public void clear() 
	{
		_TOSN = null;
		_Lineno = 0;
		_Field_Tag = null;
		_Msg_Line = null;
	}


	public String toString() 
	{
		return "#SWIFTLines("  + _TOSN + " " + _Lineno + " " + _Field_Tag + " " + _Msg_Line		 + ")";
	}
} 
