

import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.oro.text.regex.*;



import ch.ess.util.*;

public class LogSplitter {

  private static final String PATTERN_STRING = "\\[([^\\]]+)";

	private final static SimpleDateFormat formatter = 
						new SimpleDateFormat ("dd/MMM/yyyy:hh:mm:ss", Locale.US);
	
	private final static DecimalFormat df = new DecimalFormat("00");
	
  private Pattern pattern;
	private Perl5Compiler compiler;
	private PatternMatcher matcher;

  public LogSplitter() {
	compiler = new Perl5Compiler();
	matcher = new Perl5Matcher();

	try {
	  pattern = compiler.compile(PATTERN_STRING);
	} catch (MalformedPatternException e) {
	  System.err.println("Bad pattern.");
	  System.err.println(e.getMessage());
	}    
  }  

  public void split(String fileName, String splitFileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			Calendar gc = new GregorianCalendar();
			int mem = -1;
	  int memyear = 0;
			PrintWriter pw = null;
	  File outputFile = null;

			while((line = br.readLine()) != null) {
	  
	  	if (matcher.contains(line, pattern)) {
		  MatchResult result = matcher.getMatch();
		  String dateStr = result.group(1);

					Date d = formatter.parse(dateStr);
					if (d == null) {
						System.out.println(line);
					} else {
						gc.setTime(d);
						
						if (mem == -1) {
							mem = gc.get(Calendar.MONTH)+1;
			  memyear = gc.get(Calendar.YEAR);
			  outputFile = new File(splitFileName+memyear+df.format(mem));
							pw = new PrintWriter(new FileWriter(outputFile));
						} else if (mem != gc.get(Calendar.MONTH)+1) {
							pw.close();

			  ZipOutputFile zof = new ZipOutputFile(splitFileName+memyear+df.format(mem)+".zip");
			  zof.addFile(outputFile);
			  zof.close();
			  outputFile.delete();

							mem = gc.get(Calendar.MONTH)+1;
			  memyear = gc.get(Calendar.YEAR);
							outputFile = new File(splitFileName+memyear+df.format(mem));
							pw = new PrintWriter(new FileWriter(outputFile));
						}
						pw.println(line);
					}
				}
			}
			pw.close();

	  //copy current month file to fileName
	  Calendar rightNow = new GregorianCalendar();
	  File sourceFile = new File(splitFileName+rightNow.get(Calendar.YEAR)+df.format(rightNow.get(Calendar.MONTH)+1));
	  File targetFile = new File(fileName);

	  ZipOutputFile zof = new ZipOutputFile(fileName+System.currentTimeMillis()+"_BACK.zip");
	  zof.addFile(targetFile);
	  zof.close();

	  if (sourceFile.exists()) {
		FileUtil.copy(sourceFile, targetFile);
	  }

			zof = new ZipOutputFile(splitFileName+memyear+df.format(mem)+".zip");
			zof.addFile(outputFile);
			zof.close();
	  outputFile.delete();


		} catch (Exception e) {
			System.err.println(e);
		}
	
  }  

	public static void main(String[] args) {
		
		if (args.length == 2) {
		  new LogSplitter().split(args[0], args[1]);
		} else {
		  System.out.println("java LogSplitter <fileName> <splitFileName>");
		}
			
	}
}