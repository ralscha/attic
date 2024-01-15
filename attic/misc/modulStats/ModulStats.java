import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.oro.text.regex.*;


public class ModulStats {


  private static final String PATTERN_STRING = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})[^\\[]*\\[([^\\]]+)\\] \"(GET|POST) ([^ ]*) HTTP[^\"]*\" (\\d*) (\\d*|-) \"([^\"]*)\" \"([^\"]*)\"";

  private final static SimpleDateFormat formatter =
    new SimpleDateFormat ("dd/MMM/yyyy:hh:mm:ss", Locale.US);

  private final static DecimalFormat df = new DecimalFormat("00");

  private Pattern pattern;
  private Perl5Compiler compiler;
  private PatternMatcher matcher;

  private Map statMap;

  public ModulStats() {
    statMap = new TreeMap();

    compiler = new Perl5Compiler();
    matcher = new Perl5Matcher();

    try {
      pattern = compiler.compile(PATTERN_STRING);
    } catch (MalformedPatternException e) {
      System.err.println("Bad pattern.");
      System.err.println(e.getMessage());
    }
  }

  private void addStat(String url) {
    Integer no = (Integer)statMap.get(url);
    if (no != null) {
      statMap.put(url, new Integer(no.intValue()+1));
    } else {
      statMap.put(url, new Integer(1));      
    }
  }

  private void print(PrintWriter pw) {    
    Set entrySet = statMap.entrySet();
    Iterator it = entrySet.iterator();
    while(it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      pw.print("\"");
      pw.print(entry.getKey());
      pw.print("\"");
      pw.print(";");
      pw.println(entry.getValue());
    }
  }

  public void go(String fileName, String outFile) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String line;
      Calendar gc = new GregorianCalendar();

      while ((line = br.readLine()) != null) {
        try {
          if (matcher.contains(line, pattern)) {
            MatchResult result = matcher.getMatch();
                      
            String url = result.group(4);
            int pos = url.indexOf("?");
            if (pos > 0) 
              url = url.substring(0, pos);
            else {
              pos = url.indexOf(";jsessionid");
              if (pos > 0) 
                url = url.substring(0, pos);
            }

            if (!((url.endsWith(".gif")) || (url.endsWith(".jpg")))) {

              int code = Integer.parseInt(result.group(5));
          
              if (code < 400) {
                addStat(url);
              } else {
                //System.out.println(code);
              }
            }
          
          } else {
            System.out.println(line);
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
    if (args.length == 2) {
      new ModulStats().go(args[0], args[1]);
    } else {
      System.out.println("java ModulStats <fileName> <output>");
    }
    System.out.println(System.currentTimeMillis()-start);

  }
}
