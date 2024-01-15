
import ch.ess.google.wsdl.*;

public class Test {
  /** Demonstration program to call the Google Web APIs service.
   ** Usage:<br>
   **   <tt>java com.google.soap.search.GoogleAPIDemo search Foo</tt><br>
   **   <tt>java com.google.soap.search.GoogleAPIDemo cached http://www.google.com/</tt>
   **   <tt>java com.google.soap.search.GoogleAPIDemo spell "britnay spars"</tt>
   **/
  private final static String key = "4AmzrmIvkRbJKd+mZ9+Hw/+/BC0IoKSq"; 
   
  public static void main(String[] args) {
    // Parse the command line
    if (args.length != 2) {
      printUsageAndExit();
    }

    String directive = args[0];
    String directiveArg = args[1];

    // Report the arguments received
    System.out.println("Parameters:"); 
    System.out.println("Directive  = " + directive);
    System.out.println("Args       = " + directiveArg);

 
    // Depending on user input, do search or cache query, then print out result
    try {
      // Create a Google Search object, set our authorization key
      GoogleSearchServiceLocator service = new GoogleSearchServiceLocator();      
      GoogleSearchPort port = service.getGoogleSearchPort();      

      if (directive.equalsIgnoreCase("search")) {
        
        GoogleSearchResult r = port.doGoogleSearch(key, directiveArg, 0, 10, true, null, true, null, "latin1", "latin1");
        System.out.println("Google Search Results:");
        System.out.println("======================");
        
        System.out.println("gefunden: " + r.getEstimatedTotalResultsCount());
        System.out.println("time    : " + r.getSearchTime());
        ResultElement[] re = r.getResultElements();
        
        if (re != null) {
          for (int i = 0; i < re.length; i++) {
            System.out.print(re[i].getURL());
            System.out.print(" ");
            System.out.println(re[i].getTitle());
          }
        }
      } else if (directive.equalsIgnoreCase("cached")) {
        System.out.println("Cached page:");
        System.out.println("============");
        byte [] cachedBytes = port.doGetCachedPage(key, directiveArg);
        // Note - this conversion to String should be done with reference
        // to the encoding of the cached page, but we don't do that here.
        String cachedString = new String(cachedBytes);
        System.out.println(cachedString);
      } else if (directive.equalsIgnoreCase("spell")) {
        System.out.println("Spelling suggestion:");
        String suggestion = port.doSpellingSuggestion(key, directiveArg);
        System.out.println(suggestion);
      } else {
        printUsageAndExit();
      }
    } catch (Exception f) {
      System.out.println("The call to the Google Web APIs failed:");
      System.out.println(f.toString());
    }
  }

  private static void printUsageAndExit() {
    System.err.println("Usage: java " + 
                       Test.class.getName() +
                       " (search <query> | cached <url> | spell <phrase>)");
    System.exit(1);
  }
}
