import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.oro.text.regex.*;


public class FilterIP {


  private static final String PATTERN_STRING = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})[^\\[]*\\[([^\\]]+)\\] \"(GET|POST) ([^ ]*) HTTP[^\"]*\" (\\d*) (\\d*|-) \"([^\"]*)\" \"([^\"]*)\"";

  private final static SimpleDateFormat formatter =
    new SimpleDateFormat ("dd/MMM/yyyy:hh:mm:ss", Locale.US);

  private final static DecimalFormat df = new DecimalFormat("00");

  private Pattern pattern;
  private Perl5Compiler compiler;
  private PatternMatcher matcher;

  private List ipList;

  public FilterIP() {
    ipList = new ArrayList();

    compiler = new Perl5Compiler();
    matcher = new Perl5Matcher();

    try {
      pattern = compiler.compile(PATTERN_STRING);
    } catch (MalformedPatternException e) {
      System.err.println("Bad pattern.");
      System.err.println(e.getMessage());
    }
  }

  private void print(PrintWriter pw) {  
    Iterator it = ipList.iterator();
    while(it.hasNext()) {
      pw.println((String)it.next());
    }  
  }

  public void go(String fileName, String ip, String outFile) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String line;

      while ((line = br.readLine()) != null) {
        try {
          if (matcher.contains(line, pattern)) {
            MatchResult result = matcher.getMatch();
      
            String url = result.group(4);

            if (!((url.endsWith(".js")) || (url.endsWith(".gif")) || (url.endsWith(".html")) || (url.endsWith(".css")) || (url.endsWith(".jpg")))) {
              if ((url.indexOf("/stepnavigation.jsp") == -1) && (url.indexOf("/logo") == -1)) {
                if (result.group(1).equals(ip)) 
                  ipList.add(line);
              }
            }
          
          } 
        } catch (NumberFormatException nfe) {
          System.err.println(nfe);
        }
      } 

      PrintWriter pw = new PrintWriter(new FileWriter(outFile));
      print(pw);
      pw.close();

    } catch (Exception e) {
      System.err.println(e);
    }

  }

  public static void main(String[] args) {

    long start = System.currentTimeMillis();
    if (args.length == 3) {
      new FilterIP().go(args[0], args[1], args[2]);
    } else {
      System.out.println("java FilterIP <fileName> <ip> <output>");
    }
    System.out.println(System.currentTimeMillis()-start);

  }
}
