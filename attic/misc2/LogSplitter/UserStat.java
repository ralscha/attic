

import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.oro.text.regex.*;
import com.objectmatter.bsf.*;
import ch.ess.pbroker.db.*;
import ch.ess.util.pool.*;

public class UserStat {
	
  private static final String PATTERN_STRING = "^(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}) user (\\d+) logged (in|off)";
	public final static SimpleDateFormat dateFormat = 
						new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);

  private Pattern pattern;
	private Perl5Compiler compiler;
	private PatternMatcher matcher;
  private String fileName;
  private String outputFileName;
  private String schema;

  public UserStat(String schema, String file, String output) {
		compiler = new Perl5Compiler();
		matcher = new Perl5Matcher();

		try {
		  pattern = compiler.compile(PATTERN_STRING);
		} catch (MalformedPatternException e) {
		  System.err.println("Bad pattern.");
		  System.err.println(e.getMessage());
		}  

	fileName = file;
	outputFileName = output;
	this.schema = schema;
  }  

  public void doIt() {

	PoolManager.initPool(schema);
	Database db = PoolManager.requestDatabase();
	
	Map itemMap = new HashMap();

	try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
	  PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
			String line;
			while((line = br.readLine()) != null) {
				if (matcher.contains(line, pattern)) {
		  MatchResult result = matcher.getMatch();
		  String dateStr = result.group(1);
		  String userIdStr = result.group(2);
		  String logged = result.group(3);

		  try {
			Date date = dateFormat.parse(dateStr);
			int userId = Integer.parseInt(userIdStr);            
			int typ = -1;

			Benutzer benutzer = (Benutzer)db.lookup(Benutzer.class, userId);
			Integer userIdInt = null;
			if (benutzer != null) {

			  userIdInt = benutzer.getBenutzerId();

	            KundenMitarbeiter[] m1 = benutzer.getKundenMitarbeiter();
	            LieferantenMitarbeiter[] m2 = benutzer.getLieferantenMitarbeiter();
	            PBrokerAGMitarbeiter[] m3 = benutzer.getPBrokerAGMitarbeiter();
	            ExterneMitarbeiter[] m4 = benutzer.getExterneMitarbeiter();

	            if (m1 != null) {
	              Kunden kunde = m1[0].getKunde();
	              if (kunde != null) {
				  typ = StatItem.KUNDE_TYP;
	              }		              
	            }
	            if (m2 != null) {
	              Lieferanten lieferant = m2[0].getLieferant();
	              if (lieferant != null) {
				  typ = StatItem.LIEFERANT_TYP;
	              }
	            }
	            if (m3 != null) {
	              PBrokerAG pbroker = m3[0].getPBrokerAG();
	              if (pbroker != null) {
				  typ = StatItem.PBROKER_TYP;
	              }  		            
	            }
	            if (m4 != null) {
				typ = StatItem.EMA_TYP;
	            }


	            boolean loggedIn = logged.equals("in");
	
	            StatItem si = (StatItem)itemMap.get(userIdInt);
			  
			  //OK
			  if ((si == null) && loggedIn) {
				si = new StatItem();
				si.setNo(userId);
				si.setTyp(typ);
				si.setInDate(date);
				itemMap.put(userIdInt, si);
			  } else if ((si != null) && !loggedIn) {
				si.setOutDate(date);
				si.write(pw);
				itemMap.remove(userIdInt);
			  } else if ((si != null) && loggedIn) {
				//Benutzer hat sich vorher nicht abgemeldet
				si.setOutDate(date);
				si.write(pw);                
				si.setInDate(date);

			  } else {
				//System.out.println("Aber Hallo: " + userId);
			  }

			} else {
			  //System.out.println("benutzer : " + benutzer + " not found");
			}

		  } catch (ParseException pe) {
			System.err.println(pe);
		  } catch (NumberFormatException nfe) {
			System.err.println(nfe);
		  }

				}
			}
	  br.close();
	  pw.close();

	} catch (IOException ioe) {
	  System.err.println(ioe);
	} finally {
	  PoolManager.returnDatabase(db);
	}
  }  
	


  public static void main(String[] args) {
	if (args.length == 3) {
	  //UserStat us = new UserStat("pbroker.schema", "usertest.log", "out.log");
	  UserStat us = new UserStat(args[0], args[1], args[2]);
	  us.doIt();
	} else {
	  System.out.println("java UserStat <schema> <logfile> <outputfile>");
	}
  }    
}