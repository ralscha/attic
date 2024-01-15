import java.util.*;
import gnu.regexp.*;
import java.io.*;
import org.apache.oro.text.regex.*;

public class TestRead {


	public static void main(String args[]) {
		/*
		try {
			RE regex = new RE("^[ ]*\"([^\"]*||\")\",[ ]*(?:\"([^\"]*)\"||NULL),[ ]*(?:\"([^\"]*)\"||NULL),");

			BufferedReader br = new BufferedReader(new FileReader("d:\\download\\phonet\\rules.dat"));
			String line;

			while ((line = br.readLine()) != null) {
			
				REMatch match = regex.getMatch(line);
				if (match != null) {
					for (int i = 1; i <= regex.getNumSubs(); i++)
						System.out.println(i + ":" + match.toString(i));
				} else {
					System.out.println("no match");
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		*/

		/*
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
		
		try {
			COM.stevesoft.pat.Regex regex = new COM.stevesoft.pat.Regex("\"([^\"]*)\",[ ]*(\"([^\"]*)\"|NULL),[ ]*(\"([^\"]*)\"|NULL),");

			BufferedReader br = new BufferedReader(new FileReader("d:\\download\\phonet\\rules.dat"));
			String line;

			while ((line = br.readLine()) != null) {
				if (regex.didMatch()) {
					System.out.println(regex.stringMatched(1));
				} else {
					System.out.println("no match");
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		*/
		
		
		Pattern pattern;
		Perl5Compiler compiler;
		PatternMatcher matcher;
		MatchResult result;
		
		compiler = new Perl5Compiler();
		matcher  = new Perl5Matcher();
		


		try {
			pattern = compiler.compile("^<option value=\"([A-Z]{2})\"( selected)*>(.*)$");
		} catch(MalformedPatternException e) {
			System.err.println("Bad pattern.");
			System.err.println(e.getMessage());
			return;
		}
		
		
		try {
			BufferedReader br = new BufferedReader(new java.io.FileReader("C:\\WINDOWS\\Desktop\\country_de.properties.orig"));
	    PrintWriter pw = new PrintWriter(new FileWriter("C:\\WINDOWS\\Desktop\\country_de.properties"));
			String line;

			while ((line = br.readLine()) != null) {
				
				if (matcher.contains(line, pattern)) {
					result = matcher.getMatch();
          pw.print(result.group(1));
          pw.print("=");
          pw.println(result.group(3));
				} else {
          System.out.println(line);
				}
			}

      br.close();
      pw.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}
	
}


	