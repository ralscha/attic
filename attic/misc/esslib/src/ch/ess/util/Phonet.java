
package ch.ess.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.logging.*;



public class Phonet {

  private static Log LOG = LogFactory.getLog(Phonet.class.getPackage().getName());
  
  private static List rule1list = new ArrayList();
  private static List rule2list = new ArrayList();
  private static final int ANZ_HASH = 256;
  //private static final char TEST_CHAR = '\u0004';
  private static boolean TRACE = false;
  private static int[] phonethash1 = new int[ANZ_HASH];
  private static int[] phonethash2 = new int[ANZ_HASH];
  public static Phonet instance = new Phonet();

  private Phonet() {
    initPhonet();
  }

  static void traceInfo(String text, int n, String fehler) {

    Rule r1 = (Rule)rule1list.get(n);
    Rule r2 = (Rule)rule2list.get(n);

    System.out.print(text);
    System.out.print(" " + n);
    System.out.print("\"" + r1.getStr() + "\"" + r1.getRule() + "\"" + r2.getRule() + "\"");
    System.out.println(" " + fehler);
  }
 
  private void initPhonet() {

    load();

    for (int i = 0; i < ANZ_HASH; i++) {
      phonethash1[i] = -1;
      phonethash2[i] = -1;
    }

    Iterator it = rule1list.iterator();
    int i = 0;
    while (it.hasNext()) {
      Rule r = (Rule)it.next();
      
      char firstChar = r.getStr().charAt(0);


      if (r.getRule() != null) {
        if (phonethash1[firstChar] < 0) {
          phonethash1[firstChar] = i;
        }
      }

      i++;
    }

    it = rule2list.iterator();
    i = 0;

    while (it.hasNext()) {
      Rule r = (Rule)it.next();
      char firstChar = r.getStr().charAt(0);

      if (r.getRule() != null) {
        if (phonethash2[firstChar] < 0) {
          phonethash2[firstChar] = i;
        }
      }

      i++;
    }
  }

  private void load() {

    Pattern pattern = Pattern.compile("^[ ]*\"([^\"]*||\")\",[ ]*(?:\"([^\"]*)\"||NULL),[ ]*(?:\"([^\"]*)\"||NULL),");

    try {
      InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream("rules.dat"), "Cp1252");
      BufferedReader br = new BufferedReader(is);
      String line;

      while ((line = br.readLine()) != null) {
        
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {    
    
          String str = matcher.group(1);
          String rule1 = matcher.group(2);
          String rule2 = matcher.group(3);
          Rule r1 = new Rule(str, rule1);
          Rule r2 = new Rule(str, rule2);

          rule1list.add(r1);
          rule2list.add(r2);
        }
      }
    } catch (Exception e) {
      LOG.error("read file", e);
    }
  }

  public static String phonet(String wort, int modus) {
    
    if (wort == null) {
      return null;
    }


    int i, j, z, z0;
    char c, c0;
    int n, n0;
    int k, p;
    String s = null;
    int ixs = 0;
    int k0;
    int p0;
    int anz = wort.length();
    StringBuffer ziel = new StringBuffer(wort.length());

    if (modus <= 1) {
      modus = 1;
    } else {
      modus = 2;
    }

    wort = wort.toUpperCase();

    if (TRACE) {
      System.out.println("\n\nPhonetische Umwandlung fuer  :  " + wort);
      System.out.println("(nach " + modus + ". Regelsatz)");
    }

    i = 0;
    j = 0;
    z = 0;

    List ruleList;
    if (modus == 1) {
      ruleList = rule1list;
    } else {
      ruleList = rule2list;
    }

    while (i < wort.length()) {
      c = wort.charAt(i);

      if (TRACE) {
        System.out.print("\nPruefe Position "+j + ":  Wort = \"" + 
           wort.substring(i) + "\"");
        System.out.println(", Ziel = \"" + ziel.toString() + "\"");
      }

      if (modus == 1) {
        n = phonethash1[c];
      } else {
        n = phonethash2[c];
      }

      z0 = 0;

      if (n >= 0) {
        while ((n < ruleList.size()) && 
          (((Rule)ruleList.get(n)).getStr().charAt(0) == c)) {
          Rule r = (Rule)ruleList.get(n);

          if (r.getRule() == null) {
            n++;
            continue;
          }

          if (TRACE) {
            traceInfo("> Regel Nr. ", n, " wird geprueft");
          }

          k = 1;
          p = 5;
          s = r.getStr();


          ixs = 1;

          while ((ixs < s.length()) && (i + k < wort.length()) && 
                 (wort.charAt(i + k) == s.charAt(ixs)) &&
                  !Character.isDigit(s.charAt(ixs)) &&
                  ((s.charAt(ixs) != '(') && (s.charAt(ixs) != '-') && 
                   (s.charAt(ixs) != '<') && (s.charAt(ixs) != '^') && 
                   (s.charAt(ixs) != '$'))) {
            k++;
            ixs++;

          }


          if ((ixs < s.length()) && (ixs + 1 < s.length()) && (s.charAt(ixs) == '(')) {
            if (i + k < wort.length()) {
              if (Character.isLetter(wort.charAt(i + k))) {
                if (s.substring(ixs + 1).indexOf(wort.charAt(i + k)) != -1) {
                  k++;
                  while ((ixs < s.length()) && (s.charAt(ixs) != ')')) {
                    ixs++;
                  }

                  if (s.charAt(ixs) == ')') {
                    ixs++;
                  }
                }
              }
            }
          }


          if (ixs < s.length()) {
            p0 = s.charAt(ixs);
          } else {
            p0 = 0;
          }



          k0 = k;
          while ((ixs < s.length()) && (s.charAt(ixs) == '-') && (k > 1)) {
            k--;
            ixs++;
          }


          if ((ixs < s.length()) && (s.charAt(ixs) == '<')) {
            ixs++;
          }

          if ((ixs < s.length()) && Character.isDigit(s.charAt(ixs))) {
            p = Character.digit(s.charAt(ixs), 10);
            ixs++;
          }


          if ((ixs < s.length()) && (ixs + 1 < s.length()) && 
            (s.charAt(ixs) == '^') && (s.charAt(ixs + 1) == '^')) {
            ixs++;

          }

          if ((ixs == s.length()) || ((s.charAt(ixs) == '^') && 
                    ((i == 0) ||!Character.isLetter(wort.charAt(i - 1))) && 
                    ((ixs + 1 >= s.length()) || (s.charAt(ixs + 1) != '$') || 
                    ((i + k0 >= wort.length()) || (!Character.isLetter(wort.charAt(i + k0)) && 
                    (wort.charAt(i + k0) != '.'))))) || 
                    ((s.charAt(ixs) == '$') && (i > 0) && 
                    Character.isLetter(wort.charAt(i - 1)) && 
                    ((i + k0 >= wort.length()) || (!Character.isLetter(wort.charAt(i + k0)) && 
                    ((i + k0 >= wort.length()) || (wort.charAt(i + k0) != '.')))))) {


            /****  Fortsetzung suchen, falls:         ****/
            /****  k > 1  und  KEIN '-' im Suchstring ****/
            c0 = wort.charAt(i + k - 1);

            if (modus == 1) {
              n0 = phonethash1[c0];
            } else {
              n0 = phonethash2[c0];
            }

            if ((k > 1) && (n0 >= 0) && (p0 != '-') && (i + k < wort.length())) {

              while ((n0 < ruleList.size()) && 
                 (((Rule)ruleList.get(n0)).getStr().charAt(0) == c0)) {


                r = (Rule)ruleList.get(n0);

                if (r.getRule() == null) {
                  n0++;
                  continue;
                }

                if (TRACE) {
                  traceInfo("> > Forts.-Regel Nr.", n0, "wird geprueft");
                }

                /****  ganzen String prüfen  ****/
                k0 = k;
                p0 = 5;

                s = r.getStr();
                ixs = 1;

                while ((ixs < s.length()) && (i + k0 < wort.length()) && 
                      (wort.charAt(i + k0) == s.charAt(ixs)) &&
                       !Character.isDigit(s.charAt(ixs)) && 
                       (s.charAt(ixs) != '(') && (s.charAt(ixs) != '-') &&
                       (s.charAt(ixs) != '<') && (s.charAt(ixs) != '^') && 
                       (s.charAt(ixs) != '$')) {
                  k0++;
                  ixs++;
                }

                if ((ixs < s.length()) && (ixs + 1 < s.length()) && 
                  (s.charAt(ixs) == '(')) {
                  /****  Buchstabenbereich pruefen  ****/
                  if (i + k0 < wort.length()) {
                    if (Character.isLetter(wort.charAt(i + k0))) {
                      if (s.substring(ixs + 1).indexOf(wort.charAt(i + k0)) != -1) {
                        k0++;
                        while ((ixs < s.length()) && 
                          (s.charAt(ixs) != ')')) {
                          ixs++;
                        }
                        if (s.charAt(ixs) == ')') {
                          ixs++;
                        }
                      }
                    }
                  }
                }
                while ((ixs < s.length()) && (s.charAt(ixs) == '-')) {
                  /****  "k0" wird NICHT vermindert  ****/
                  /****  wg. Abfrage "if (k0 == k)"  ****/
                  ixs++;
                }
                if ((ixs < s.length()) && (s.charAt(ixs) == '<')) {
                  ixs++;
                }
                if ((ixs < s.length()) && Character.isDigit(s.charAt(ixs))) {
                  p0 = Character.digit(s.charAt(ixs), 10);
                  ixs++;
                }

                if ((ixs == s.length()) /****  *s == '^' scheidet aus  ****/
                || ((ixs < s.length()) && (s.charAt(ixs) == '$') && 
                    ((i + k0 >= wort.length()) || 
                     (!Character.isLetter(wort.charAt(i + k0)) &&
                       (wort.charAt(i + k0) != '.'))))) {
                  if (k0 == k) {
                    /****  Dies ist nur ein Teilstring  ****/
                    if (TRACE) {
                      traceInfo("> > Forts.-Regel Nr.", n0, "nicht benutzt (zu kurz)");
                    }
                    n0++;
                    continue;
                  }

                  if (p0 < p) {
                    /****  Prioritaet ist zu klein  ****/
                    if (TRACE) {
                      traceInfo("> > Forts.-Regel Nr.", n0, "nicht benutzt (Prioritaet)");
                    }
                    n0++;
                    continue;
                  }
                  /****  Regel passt; Suche abbrechen  ****/
                  break;
                }

                if (TRACE) {
                  traceInfo("> > Forts.-Regel Nr.", n0, "nicht benutzt");
                }
                n0++;
              } /****  Ende von "while"  ****/

              
              if ((p0 >= p) && ((n0 < ruleList.size()) && 
                   ((((Rule)ruleList.get(n0)).getStr().charAt(0) == c0)))) {
                if (TRACE) {
                  traceInfo("> Regel Nr.", n, "");
                  traceInfo("> nicht benutzt wg. Forts. ", n0, "");
                }
                n++;
                continue;
              }
            }

            /****  String ersetzen  ****/
            if (TRACE) {
              traceInfo("Benutzt wird Regel Nr.", n, "");
            }

            r = (Rule)ruleList.get(n);
            ixs = 0;
            s = r.getRule();

            String tmp2 = r.getStr();
            p0 = (!tmp2.trim().equals("") && (tmp2.length() > 1) && 
                  (tmp2.substring(1).indexOf('<') != -1)) ? 1 : 0;

      						
      						
            if ((p0 == 1) && (z == 0)) {

              /****  Regel mit '<' wird angewendet  ****/
              if ((j > 0) && (ixs < s.length()) && ((ziel.charAt(j - 1) == c) || 
                         (ziel.charAt(j - 1) == s.charAt(ixs)))) {
                j--;
              }
              z0 = 1;
              z++;
              k0 = 0;
              while ((ixs < s.length()) && (i + k0 < wort.length())) {
                StringBuffer tmp = new StringBuffer(wort);
                tmp.setCharAt(i + k0, s.charAt(ixs));
                wort = tmp.toString();

                k0++;
                ixs++;
              }
              if (k > k0) {
                //strcpy (wort+i+k0, wort+i+k);
                wort = wort.substring(0, i + k0) + wort.substring(i + k);

              }
              /****  neuer "aktueller Buchstabe"  ****/
              c = wort.charAt(i);
            } else {
              i = i + k - 1;
              z = 0;

              while ((ixs < s.length()) && (ixs + 1 < s.length()) &&
                   (j < anz)) {

                if ((ziel.length() == 0) || (ziel.charAt(ziel.length() - 1) /*j - 1)*/ != s.charAt(ixs))) {
                  ziel.append(s.charAt(ixs));
                  j++;
                }
                ixs++;
              }
              /****  neuer "aktueller Buchstabe"  ****/

              if (ixs < s.length()) {
                c = s.charAt(ixs);
              } else {
                c = 0;
              }

              Rule tmpr = (Rule)ruleList.get(n);
              if ((n < ruleList.size()) && (tmpr.getStr().substring(1).indexOf("^^") != -1)) {
                if (c != 0) {

                  ziel.append(c);
                  //ziel.setCharAt(j, s.charAt(ixs));
                  j++;
                }
                wort = wort.substring(i + 1);
                i = 0;
                z0 = 1;
              }
            }

            break;
          }

          n++;
        }
      }

      if (z0 == 0) {
        if ((j < anz) && (c != 0) && /*(s != null) && (ixs <= s.length()) &&*/
                        ((ziel.length() == 0) || (ziel.charAt(ziel.length() - 1) != c))) {
          /****  nur doppelte Buchstaben verdichten  ****/
          ziel.append(c);
          //ziel.setCharAt(j, c);
          j++;
        }
        i++;
        z = 0;
      }

    }    /* while */
    // ziel[j] = '\0';
    return (ziel.toString());
  }


  public static void main(String args[]) {
		
    //int i = -1;

    if (args.length < 1) {
      System.out.println("Aufruf:  phonet  <wort>       [ -trace ]");
      System.out.println();
      System.out.println("Programm fuer phonetische Stringumwandlung");
      System.out.println();
      System.out.println("Optionen:");
      System.out.println("-trace       :  Ausfuehrliches Logging ausgeben.");
      return;
    }

    if ((args.length >= 2) && (args[1].equalsIgnoreCase("-trace"))) {
      if ((args.length >= 3) && (Integer.parseInt(args[2]) > 0)) {
        //i = Integer.parseInt(args[2]);
      }
      Phonet.TRACE = true;
    }


    String text = args[0];

    System.out.println("Ausgangswort                :  \"" + text + "\"");
    String phon = Phonet.phonet(text, 1);
    System.out.println();

    System.out.println("Umwandlung nach 1. Regelsatz:  \"" + phon + "\"");

    phon = Phonet.phonet(text, 2);

    System.out.println("Umwandlung nach 2. Regelsatz:  \"" + phon + "\"");

  }


  class Rule {

    private String str;
    private String rule;

    Rule(String str, String rule) {
      if (str != null) {        
        this.str = toUpperCase(str);        
      }

      if (rule != null) {
        this.rule = toUpperCase(rule);
      }
    }

    String getStr() {
      return str;
    }

    String getRule() {
      return rule;
    }

    private String toUpperCase(String str) {
      char[] carray = str.toCharArray();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < carray.length; i++) {
        sb.append(Character.toUpperCase(carray[i]));
      }
      return sb.toString();
    }

  }


}
