
public class MessageLine implements java.io.Serializable {
	private int lineno;
	private String fieldTag;
	private String msgLine;

	public MessageLine() {
	    this(0, null, null);
	}

	public MessageLine(int no, String tag, String data) {
		lineno = no;
		fieldTag = tag;
		msgLine = data;
	}
	

	public void setLineno(int no) {
		lineno = no;
	}
	
	public int getLineno() {
		return lineno;
	}

	public void setFieldTag(String tag) {
		fieldTag = tag;
	}

	public String getFieldTag() {
		return fieldTag;
	}

	public void setMsgLine(String data) {
		msgLine = data;
	}

	public String getMsgLine() {
		return msgLine;
	}

    public String getString() {
        StringBuffer sb = new StringBuffer();
        sb.append(fieldTag);
        if (fieldTag.length() == 2)        
            sb.append("  : ");
         else if (fieldTag.length() == 3)
            sb.append(" : ");
         else
            sb.append("      ");
        sb.append(msgLine).append("\n");
        return sb.toString();
    }

	public String toString() {
		return "#MessageLine(" + lineno + " " + fieldTag + " " + msgLine + ")";
	}
} 
