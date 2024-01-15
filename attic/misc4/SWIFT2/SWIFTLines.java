
public class SWIFTLines implements java.io.Serializable {
	private int _Lineno;
	private String _Field_Tag;
	private String _Msg_Line;

	public SWIFTLines() {
	    clear(); 
	}

	public SWIFTLines(int _Lineno, String _Field_Tag, String _Msg_Line) {
		this._Lineno    = _Lineno;
		this._Field_Tag = _Field_Tag;
		this._Msg_Line  = _Msg_Line;
	}
	

	public void setLineno(int val) {
		_Lineno = val;
	}
	
	public int getLineno() {
		return _Lineno;
	}

	public void setField_Tag(String val) {
		_Field_Tag = val;
	}

	public String getField_Tag() {
		return _Field_Tag;
	}

	public void setMsg_Line( String val ) {
		_Msg_Line = val;
	}

	public String getMsg_Line() {
		return _Msg_Line;
	}


	public void clear() {
		_Lineno = 0;
		_Field_Tag = null;
		_Msg_Line = null;
	}

    public String getString() {
        StringBuffer sb = new StringBuffer();
        sb.append(_Field_Tag);
        if (_Field_Tag.length() == 2)        
            sb.append("  : ");
         else if (_Field_Tag.length() == 3)
            sb.append(" : ");
         else
            sb.append("      ");
        sb.append(_Msg_Line).append("\n");
        return sb.toString();
    }

	public String toString() {
		return "#SWIFTLines("  + _Lineno + " " + _Field_Tag + " " + _Msg_Line		 + ")";
	}
} 
