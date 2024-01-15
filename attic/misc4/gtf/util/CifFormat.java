package gtf.util;

import ViolinStrings.*;
 
public class CifFormat {
	public final static int EXTERN = 0;
	public final static int INTERN = 1;
	public final static int WRONG  = -1;

	private final static COM.stevesoft.pat.Regex extCifRegex = new COM.stevesoft.pat.Regex("^(\\d{4})-[ ]*(\\d{1,7})(-(\\d)){0,1}$");
	private final static COM.stevesoft.pat.Regex intCifRegex = new COM.stevesoft.pat.Regex("^\\d{12}$");
	
	public CifFormat() {
		super();
	}
	private static String ext2int(String in) {
		extCifRegex.search(in);
		if (extCifRegex.didMatch()) {
			String ibbb  = extCifRegex.stringMatched(1);
			String stamm = extCifRegex.stringMatched(2).trim();
			String p     = extCifRegex.stringMatched(4);
					
			return (getInternString(ibbb,stamm,p));
			
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
	
	public static String getExternString(String ibbb, String stamm, String p) {
		StringBuffer sb = new StringBuffer(14);
		sb.append(ibbb).append("-");
		int stammno = Integer.parseInt(stamm);
		sb.append(Strings.rightJustify(String.valueOf(stammno),7,'0')).append("-");
		
		
		if (p == null) {
			StringBuffer temp = new StringBuffer(11);
			temp.append(Strings.rightJustify(ibbb,4,'0'));
			temp.append(Strings.rightJustify(stamm,7,'0'));
			sb.append(gtf.util.CheckSumCalculator.calc(temp.toString()));
		} else {
			sb.append(p);
		}	
			
		return (sb.toString());
		
	}
	
	public static String getInternString(String ibbb, String stamm, String p) {
		StringBuffer sb = new StringBuffer(12);
		sb.append(Strings.rightJustify(ibbb,4,'0'));
		sb.append(Strings.rightJustify(stamm,7,'0'));
			
		if (p != null)
			sb.append(p);
		else
			sb.append(gtf.util.CheckSumCalculator.calc(sb.toString()));
		return (sb.toString());
	}
	
	private static String int2ext(String in) {
		intCifRegex.search(in);
		if (intCifRegex.didMatch()) {
	
		    String ibbb  = in.substring(0,4);
		    String stamm = in.substring(4,11);
		    String p     = in.substring(11,12);
	
		    return(getExternString(ibbb,stamm,p));
			
		} else {
			return null;
		}
	}
	
	public static int isCif(String cifStr) {
	
		if (cifStr.length() == 12) {
			intCifRegex.search(cifStr);
			if (intCifRegex.didMatch())
				return INTERN;
		}
		
		extCifRegex.search(cifStr);
		if (extCifRegex.didMatch())
			return EXTERN;
		else
			return WRONG;
		
	}
	
	public static Cif makeCif(String cifStr) {
		if (cifStr.length() == 12) {
			intCifRegex.search(cifStr);
			if (intCifRegex.didMatch()) {
				Cif cif = new Cif();
				cif.setIbbb(cifStr.substring(0,4));
				cif.setStamm(cifStr.substring(4,11));
				cif.setP(cifStr.substring(11,12));
				return cif;
			}
				
		}
		
		extCifRegex.search(cifStr);
		if (extCifRegex.didMatch()) {
			Cif cif = new Cif();
			cif.setIbbb(extCifRegex.stringMatched(1));
			cif.setStamm(extCifRegex.stringMatched(2).trim());
			cif.setP(extCifRegex.stringMatched(4));
			return cif;
		}
			
		else
			return null;
	}
}