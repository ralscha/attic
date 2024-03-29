
package mvs;

import com.objectmatter.bsf.* ;

public class MVSUtil {

	public static String convertText(String text)  {
		StringBuffer sb = new StringBuffer(text.length());
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '�') sb.append("ss");
			else if (c == '�') sb.append('"');
			else if (c == '�') sb.append('"');
			else if (c == '�') sb.append('"');
			else if ((c == '�') && ((i == 0) || (i == text.length()-1))) sb.append('"');
			else if ((c == '\'') && ((i == 0) || (i == text.length()-1))) sb.append('"');			
			else
				sb.append(c);	
		}		
		return(sb.toString());
		
	}

  //retrieves all dbExceptions chained to each other
  public static void dbMsg(BODBException dbEx) {
    System.out.println("Database exception(s) thrown:");
    BODBException exHolder = dbEx ;
    while (exHolder != null) {
      System.err.println(exHolder.getCode() + "  " + exHolder.getMessage());
      exHolder = dbEx.getNext();
      dbEx = exHolder ;
    }
  }

}