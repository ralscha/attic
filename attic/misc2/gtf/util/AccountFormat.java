package gtf.util;

import ViolinStrings.*;
 
public class AccountFormat {
	public final static int EXTERN = 0;
	public final static int INTERN = 1;
	public final static int WRONG  = -1;
	
	private final static COM.stevesoft.pat.Regex extAccountRegex = new COM.stevesoft.pat.Regex("^(\\d{4})-[ ]*(\\d{1,7})-(\\d{2})(-(\\d{1,3})){0,1}$");
	private final static COM.stevesoft.pat.Regex intAccountRegex = new COM.stevesoft.pat.Regex("^\\d{16}$");
	
	public AccountFormat() {
		super();
	}

	private static String ext2int(String in) {
		extAccountRegex.search(in);
		if (extAccountRegex.didMatch()) {
			String ibbb  = extAccountRegex.stringMatched(1);
			String stamm = extAccountRegex.stringMatched(2).trim();
			String p     = extAccountRegex.stringMatched(3);
			String last  = extAccountRegex.stringMatched(5);
	
			return (getInternString(ibbb, stamm, p, last));
					
		} else {
			return null;
		}
	}
	
	public static String format(String in, int type) {
		switch(type) {
			case INTERN : return ext2int(in);
			case EXTERN : return int2ext(in);
			default            : return null;
		}
	}
	
	public static String getExternString(String ibbb, String stamm, String p, String last) {
		
		StringBuffer sb = new StringBuffer(19);
		sb.append(ibbb).append("-");
		int stammno = Integer.parseInt(stamm);
		sb.append(Strings.rightJustify(String.valueOf(stammno),7,'0')).append("-");
		sb.append(p);
	
		if (last != null) {
			int lastno = Integer.parseInt(last);
			if (lastno != 0)
				sb.append("-").append(lastno);
		}
		return (sb.toString());
	}

	public static String getInternString(String ibbb, String stamm, String p, String last) {
	
		StringBuffer sb = new StringBuffer(16);
		sb.append(Strings.rightJustify(ibbb,4,'0'));
		sb.append(Strings.rightJustify(stamm,7,'0'));
		sb.append(p);
		if (last != null)
			sb.append(Strings.rightJustify(last,3,'0'));
		else
			sb.append("000");
		return (sb.toString());	
	}
	
	private static String int2ext(String in) {
		intAccountRegex.search(in);
		if (intAccountRegex.didMatch()) {	
		    String ibbb  = in.substring(0,4);
		    String stamm = in.substring(4,11);
		    String p     = in.substring(11,13);
		    String last  = in.substring(13,16);	
		    return(getExternString(ibbb, stamm, p, last));			
		} else {
			return null;
		}		
	}
	
	public static Account makeAccount(String accountStr) {
		
		if (accountStr.length() == 16) {
			intAccountRegex.search(accountStr);
			if (intAccountRegex.didMatch()) {
				Account acct = new Account();
				acct.setIbbb(accountStr.substring(0,4));
				acct.setStamm(accountStr.substring(4,11));
				acct.setP(accountStr.substring(11,13));
				acct.setLast(accountStr.substring(13,16));
				return acct;
			}
				
		}
		
		extAccountRegex.search(accountStr);
		if (extAccountRegex.didMatch()) {
			Account acct = new Account();
			acct.setIbbb(extAccountRegex.stringMatched(1));
			acct.setStamm(extAccountRegex.stringMatched(2).trim());
			acct.setP(extAccountRegex.stringMatched(3));
			acct.setLast(extAccountRegex.stringMatched(5));
			return acct;
		}
			
		else
			return null;
	}
		
	public static int isAccount(String accountStr) {
		
		if (accountStr.length() == 16) {
			intAccountRegex.search(accountStr);
			if (intAccountRegex.didMatch())
				return INTERN;
		}
		
		extAccountRegex.search(accountStr);
		if (extAccountRegex.didMatch())
			return EXTERN;
		else
			return WRONG;
	}
}