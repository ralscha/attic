
import gnu.regexp.*;
import org.apache.oro.text.regex.*;

public class RETests {

	private RE regexp;
	private RE timeregexp;
	
	private RETests() {
		try {
			regexp = new RE("^\\S+@\\S+\\.(com|org|net)");
			timeregexp = new RE("(1[0-2]|[1-9]):([0-5]\\d) (am|pm)");
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private void test5() {
		try {
		
			String s = "POST http://localhost/planner/test/hh.html?kdjf=jdfkj&kj=j HTTP/1.0";
		
			RE regexp = new RE("^(GET|HEAD|POST|PUT|DELETE|LINK|UNLINK) [\\w]+://\\w+:{0,1}\\d*([^ ]+) ([\\w/.]+)$");

      long start = System.currentTimeMillis();
      for (int i = 0; i < 10000; i++) {

      String s1;
			
			REMatch match = regexp.getMatch(s);
			if (match != null) {
        s1 = match.toString(1);
				//System.out.println(match.toString(1));
				//System.out.println(match.toString(2));
				//System.out.println(match.toString(3));
			} else {
				System.out.println("no match");
			}

      }
      System.out.println((System.currentTimeMillis()-start) + " ms");

		} catch (Exception ree) {
			System.err.println(ree);
		}
	}

/*
	private void test5jakarta() {
		try {
		
			String s = "POST http://localhost/planner/test/hh.html?kdjf=jdfkj&kj=j HTTP/1.0";
		
			org.apache.regexp.RE regexp = new org.apache.regexp.RE("^(GET|HEAD|POST|PUT|DELETE|LINK|UNLINK) [\\w]+://\\w+:{0,1}\\d*([^ ]+) ([\\w/.]+)$");
			
      if ( regexp.match(s) ) {
				System.out.println(regexp.getParen(1));
				System.out.println(regexp.getParen(2));
				System.out.println(regexp.getParen(3));
			} else {
				System.out.println("no match");
			}
		} catch (Exception ree) {
			System.err.println(ree);
		}
	}
*/
  private void test5oro() {

		String s = "POST http://localhost/planner/test/hh.html?kdjf=jdfkj&kj=j HTTP/1.0";
	
    Pattern pattern;
		Perl5Compiler compiler;
		PatternMatcher matcher;

		compiler = new Perl5Compiler();
		matcher = new Perl5Matcher();

		try {
			pattern = compiler.compile("^(GET|HEAD|POST|PUT|DELETE|LINK|UNLINK) [\\w]+://\\w+:{0,1}\\d*([^ ]+) ([\\w/.]+)$");
		} catch (MalformedPatternException e) {
			System.err.println("Bad pattern.");
			System.err.println(e.getMessage());
			return;
		}  

    long start = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {

    String s1;
    if (matcher.contains(s, pattern)) {
      MatchResult result = matcher.getMatch();
			s1 = result.group(1);
			//System.out.println(result.group(1));
			//System.out.println(result.group(2));
			//System.out.println(result.group(3));
    }

    }
    System.out.println((System.currentTimeMillis()-start) + " ms");
  }
	
  private void testVar() {
    String s = "m12_tage";

    Pattern pattern;
		Perl5Compiler compiler;
		PatternMatcher matcher;

		compiler = new Perl5Compiler();
		matcher = new Perl5Matcher();

		try {
			pattern = compiler.compile("m(\\d+)_(.*)");
		} catch (MalformedPatternException e) {
			System.err.println("Bad pattern.");
			System.err.println(e.getMessage());
			return;
		}  

    if (matcher.contains(s, pattern)) {
      MatchResult result = matcher.getMatch();
      System.out.println(result.group(1));
      System.out.println(result.group(2));
    }


  }

  private void testTemp() {
    String s = "[-mitarbeiterName-], [-mitarbeiterVorname-]";

    java.util.Map m = new java.util.HashMap();
    m.put("mitarbeiterName", "Ralph");
    m.put("mitarbeiterVorname", "Schaer");

    Pattern pattern;
		Perl5Compiler compiler;
		PatternMatcher matcher;

		compiler = new Perl5Compiler();
		matcher = new Perl5Matcher();

		try {
			pattern = compiler.compile("\\[-.+?-\\]");
		} catch (MalformedPatternException e) {
			System.err.println("Bad pattern.");
			System.err.println(e.getMessage());
			return;
		}  

    PatternMatcherInput input = new PatternMatcherInput(s);
    StringBuffer sb = new StringBuffer();

    int lastEnd   = 0;

    while (matcher.contains(input, pattern)) {
      MatchResult result = matcher.getMatch();

      int begin = result.beginOffset(0);
      int end   = result.endOffset(0);
      
      String key = s.substring(begin+2, end-2);
      String value = (String)m.get(key);

      if (lastEnd > 0) {
        sb.append(s.substring(lastEnd, begin));
      }
      sb.append(value);

      lastEnd   = end;

    }

    sb.append(s.substring(lastEnd));
    

    System.out.println(sb.toString());
  }

	private void test4() {
		try {
			String s = "[Mozilla/4.0 (compatible; MSIE 5.0; Windows NT 5.0)*]";
			
			String s1 = "Mozilla/4.0  (Win98; I)";
			
			String vorher = "Mozilla/4.0 * (WinNT; I)";
			String nachher = createREExpression(vorher);
			System.out.println(nachher);
			
			RE regexp = new RE(nachher);
			
			REMatch match = regexp.getMatch(s1);
			if (match != null) {
				System.out.println("match");
				
				//System.out.println(match.toString(1));
			} else {
				System.out.println(match);
			}
		} catch (REException ree) {
			System.err.println(ree);
		}
	}
	
	private static String RESYNTAX = ".()\\$^";
	
	private String createREExpression(String str) {
		char c;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (RESYNTAX.indexOf(c) != -1) {
				sb.append("\\").append(c);
			} else if (c == '*') {
				sb.append(".*");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private boolean isValidEmail(String emailAddress) {
		if (regexp.getMatch(emailAddress) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private void test2(String parm) {
		if (isValidEmail(parm))
			System.out.println("valid address");
		else
			System.out.println("invalid address");
	}
	
	private void test(String parm) {
		
		try {
			RE regex = new RE("^\\$\\d{1,3}(,\\d{3})*$");
			
			REMatch match = regex.getMatch(parm);
			if (match != null) {
				System.out.println("match");
			} else {
				System.out.println("no match");
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		
	}

	//convert common time string to military time
	public String toMilitaryTime(String s) throws REException {
		REMatch[] match = timeregexp.getAllMatches(s);
		
		String result = s;
		
			for (int i = 0; i < match.length; i++) {
				System.out.println(i);
				String hour = match[i].toString(1);
				String min = match[i].toString(2);
				String meridian = match[i].toString(3);
				int h = Integer.parseInt(hour);
				if ( meridian.toLowerCase().equals("pm") )
					h = (h+12) % 24;
	
				
				result = (timeregexp.substituteAll(s, h + ":" + min + " " + meridian));
				

			}
		
		
		return result;
	}
	// extract submatch from original string
	private String getSubMatch(REMatch m, String s, int i) {
		if (m != null) {
			int start = m.getSubStartIndex(i);
			return s.substring(start,m.getSubEndIndex(i));
		}
		return null;
	}
	
	private void test3(String parm) { 
		
		long start = System.currentTimeMillis();
		
		try {
			System.out.println(toMilitaryTime(parm));
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.out.println(System.currentTimeMillis() - start + " ms");
	}
	
	public static void main(String[] args) {
    RETests tests = new RETests();
    //tests.test5();
    //tests.test5jakarta();
    //tests.test5oro();
    tests.testVar();
	}
}