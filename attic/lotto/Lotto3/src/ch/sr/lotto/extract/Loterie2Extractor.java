package ch.sr.lotto.extract;

import java.io.*;
import java.net.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.util.*;

public class Loterie2Extractor extends Extractor {

  int[] numbers = null;

  public Draw extractDraw(Calendar nextDate) {
    Draw newDraw;

    List wordList = readData(0);
    if (wordList == null) {
      return null;
    }

    try {
      int startIndex = searchStartIndex(wordList);
      if (startIndex == -1) {
        return null;
      }
  
      if (newData(Integer.parseInt((String) wordList.get(startIndex + 1)),
        getEnglishMonthNumber((String) wordList.get(startIndex + 2)),
        Integer.parseInt((String) wordList.get(startIndex + 3)), nextDate)) {
        
        newDraw = new Draw();
        newDraw.setDrawdate(nextDate.getTime());
  
        newDraw.setNum1(new Integer(numbers[0]));
        newDraw.setNum2(new Integer(numbers[1]));
        newDraw.setNum3(new Integer(numbers[2]));
        newDraw.setNum4(new Integer(numbers[3]));
        newDraw.setNum5(new Integer(numbers[4]));
        newDraw.setNum6(new Integer(numbers[5]));
  
        newDraw.setAddnum(new Integer(numbers[6])); // Zusatzzahl
        newDraw.setJoker((String) wordList.get(startIndex + 4)); // Joker
        newDraw.setExtrajoker((String) wordList.get(startIndex + 5)); // ExtraJoker
  
  
        startIndex = searchStartIndex(wordList, "numbers/digits");
        if (startIndex != -1) {
          try {
            newDraw.setNextLottoGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 2))));
            newDraw.setNextJokerGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 25))));              
            
          } catch (Exception e) {
            try {
              newDraw.setNextLottoGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 3))));
              newDraw.setNextJokerGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 27))));              
            } catch (Exception e2) {            
              e2.printStackTrace();
            }
          }
        }
        
        startIndex = searchStartLastIndex(wordList, "numbers/digits");
        if (startIndex != -1) {
          try {
            newDraw.setNextExtraJokerGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 2))));
          } catch (Exception e) {
            
            try {
              newDraw.setNextExtraJokerGainQuota(Double.parseDouble(stringConv((String) wordList.get(startIndex + 3))));
            } catch (Exception e2) {
              e2.printStackTrace();
            }
          }
        }
        
              
        
        return newDraw;
        
      } else {
        return null;
      }
    
         
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      return null;
    }
    
  }

  public LottoGainQuota extractLottoGainQuota(Calendar nextDate) {

    LottoGainQuota newlgq;

    List wordList = readData(1);
    if (wordList == null) {
      return null;
    }

    int startIndex = searchStartIndex(wordList);
    if (startIndex == -1) {
      return null;
    }

    try {
      if (newData(Integer.parseInt((String) wordList.get(startIndex + 1)),
        getEnglishMonthNumber((String) wordList.get(startIndex + 2)),
        Integer.parseInt((String) wordList.get(startIndex + 3)), nextDate)) {

        startIndex = searchStartIndex(wordList, "5+");
        if (startIndex == -1) {
          return null;
        }

        startIndex -= 2;

        newlgq = new LottoGainQuota();

        String temp = (String)wordList.get(startIndex);

        if (temp.indexOf("Jackpot") != -1) {
            
          newlgq.setJackpot(new Double(stringConv((String) wordList.get(startIndex + 1))));
          newlgq.setNum6(new Integer(0));
          newlgq.setQuota6(new Double(0.0));
        } else {
          newlgq.setJackpot(null);
          newlgq.setNum6(new Integer(stringConv((String) wordList.get(startIndex))));
          newlgq.setQuota6(new Double(stringConv((String) wordList.get(startIndex + 1))));
                    
        }

        newlgq.setNum5plus(new Integer(stringConv((String) wordList.get(startIndex + 3))));
        newlgq.setQuota5plus(new Double(stringConv((String) wordList.get(startIndex + 4))));

        newlgq.setNum5(new Integer(stringConv((String) wordList.get(startIndex + 6))));
        newlgq.setQuota5(new Double(stringConv((String) wordList.get(startIndex + 7))));

        newlgq.setNum4(new Integer(stringConv((String) wordList.get(startIndex + 9))));
        newlgq.setQuota4(new Double(stringConv((String) wordList.get(startIndex + 10))));

        newlgq.setNum3(new Integer(stringConv((String) wordList.get(startIndex + 12))));
        newlgq.setQuota3(new Double(stringConv((String) wordList.get(startIndex + 13))));


        return newlgq;
      } else
        return null;

    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      return null;
    }

  }

  public JokerGainQuota extractJokerGainQuota(Calendar nextDate) {

    JokerGainQuota newjgq;

    List wordList = readData(2);
    if (wordList == null)
      return null;

    int startIndex = searchStartIndex(wordList);
    if (startIndex == -1)
      return null;


    try {
      if (newData(Integer.parseInt((String) wordList.get(startIndex + 1)),
        getEnglishMonthNumber((String) wordList.get(startIndex + 2)),
        Integer.parseInt((String) wordList.get(startIndex + 3)), nextDate)) {

        startIndex = searchStartIndex(wordList, "numbers/digits");
        if (startIndex == -1) {
          return null;
        }

        String test = (String)wordList.get(startIndex+1);
        if    ((test.indexOf('(') != -1) 
           &&  (test.indexOf(')') != -1) ) {
          startIndex += 4;
        } else {
          startIndex += 5;
        }

        newjgq = new JokerGainQuota();

        String temp = (String)wordList.get(startIndex);
                
        if (temp.indexOf("Jackpot") != -1) {
          newjgq.setJackpot(new Double(stringConv((String) wordList.get(startIndex + 1))));
          newjgq.setNum6(new Integer(0));
          newjgq.setQuota6(new Double(0.0));
        } else {
          newjgq.setJackpot(null);
          newjgq.setNum6(new Integer(stringConv((String) wordList.get(startIndex))));
          newjgq.setQuota6(new Double(stringConv((String) wordList.get(startIndex + 1))));
                    
        }

        newjgq.setNum5(new Integer(stringConv((String) wordList.get(startIndex + 3))));
        newjgq.setQuota5(new Double(stringConv((String) wordList.get(startIndex + 4))));

        newjgq.setNum4(new Integer(stringConv((String) wordList.get(startIndex + 6))));
        newjgq.setQuota4(new Double(stringConv((String) wordList.get(startIndex + 7))));

        newjgq.setNum3(new Integer(stringConv((String) wordList.get(startIndex + 9))));
        newjgq.setQuota3(new Double(stringConv((String) wordList.get(startIndex + 10))));

        newjgq.setNum2(new Integer(stringConv((String) wordList.get(startIndex + 12))));
        newjgq.setQuota2(new Double(stringConv((String) wordList.get(startIndex + 13))));

        return newjgq;
      } else
        return null;

    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      return null;
    }

  }

  public ExtraJokerGainQuota extractExtraJokerGainQuota(Calendar nextDate) {

    ExtraJokerGainQuota newejgq;

    List wordList = readData(3);
    if (wordList == null)
      return null;

    int startIndex = searchStartIndex(wordList);
    if (startIndex == -1)
      return null;


    try {
      if (newData(Integer.parseInt((String) wordList.get(startIndex + 1)),
        getEnglishMonthNumber((String) wordList.get(startIndex + 2)),
        Integer.parseInt((String) wordList.get(startIndex + 3)), nextDate)) {

        startIndex = searchStartLastIndex(wordList, "numbers/digits");
        if (startIndex == -1) {
          return null;
        }

        startIndex -= 19;

        newejgq = new ExtraJokerGainQuota();

        String temp = (String)wordList.get(startIndex);

        if (temp.indexOf("Jackpot") != -1) {
          newejgq.setJackpot(new Double(stringConv((String) wordList.get(startIndex + 1))));
          newejgq.setNum6(new Integer(0));
          newejgq.setQuota6(new Double(0.0));
        } else {
          newejgq.setJackpot(null);
          newejgq.setNum6(new Integer(stringConv((String) wordList.get(startIndex))));
          newejgq.setQuota6(new Double(stringConv((String) wordList.get(startIndex + 1))));

        }

        newejgq.setNum5(new Integer(stringConv((String) wordList.get(startIndex + 3))));
        newejgq.setQuota5(new Double(stringConv((String) wordList.get(startIndex + 4))));

        newejgq.setNum4(new Integer(stringConv((String) wordList.get(startIndex + 6))));
        newejgq.setQuota4(new Double(stringConv((String) wordList.get(startIndex + 7))));

        newejgq.setNum3(new Integer(stringConv((String) wordList.get(startIndex + 9))));
        newejgq.setQuota3(new Double(stringConv((String) wordList.get(startIndex + 10))));

        newejgq.setNum2(new Integer(stringConv((String) wordList.get(startIndex + 12))));
        newejgq.setQuota2(new Double(stringConv((String) wordList.get(startIndex + 13))));

        return newejgq;
      } else
        return null;

    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      return null;
    }

  }

  private List readData(int t) {
    BufferedReader inReader;

    try {
      if (AppProperties.getBooleanProperty("web.access", true)) {
        URL url = null;
        switch (t) {
          case 0 :
            url = new URL(AppProperties.getStringProperty("url.swisslotto.ziehung"));
            break;
          case 1 :
            url = new URL(AppProperties.getStringProperty("url.swisslotto.lottogewinnquote"));
            break;
          case 2 :
            url = new URL(AppProperties.getStringProperty("url.swisslotto.jokergewinnquote"));
            break;
          case 3 :
            url = new URL(AppProperties.getStringProperty("url.swisslotto.extrajokergewinnquote"));
            break;            
        }
        inReader = new BufferedReader(new InputStreamReader(url.openStream()));

        if (t == 0) {
          numbers = ExtractNumbers.getNumbers(inReader);
          inReader.close();
        }
        
        inReader = new BufferedReader(new InputStreamReader(url.openStream()));
        
      } else {
        String fileName = null;
        switch (t) {
          case 0 :
            fileName = AppProperties.getStringProperty("file.swisslotto.ziehung");
            break;
          case 1 :
            fileName = AppProperties.getStringProperty("file.swisslotto.lottogewinnquote");
            break;
          case 2 :
            fileName = AppProperties.getStringProperty("file.swisslotto.jokergewinnquote");
            break;
          case 3 :
            fileName = AppProperties.getStringProperty("file.swisslotto.extrajokergewinnquote");
            break;            
        }
        inReader = new BufferedReader(new FileReader(fileName));

        if (t == 0) {
          numbers = ExtractNumbers.getNumbers(inReader);
          inReader.close();
        }

        inReader = new BufferedReader(new FileReader(fileName));

      }

      
      

      List wordList = createWordList(inReader);
      inReader.close();

       /*
      Iterator it = wordList.iterator();
      int i = 0;
      while(it.hasNext()) {
        System.out.println((i++) + " " + (String)it.next());
      }
      */
      
      
      

      return wordList;
    } catch (MalformedURLException e) {
      System.err.println(e);      
      return null;
    } catch (IOException ioe) {
      System.err.println(ioe);      
      return null;
    }
  }

  private int searchStartIndex(List list) {
    for (int i = 0; i < list.size(); i++) {
      if ((((String) list.get(i)).equalsIgnoreCase("Wednesday")) || 
          (((String) list.get(i)).equalsIgnoreCase("Saturday"))) {
        return i;
      }
    }
    return -1;
  }

  private int searchStartIndex(List list, String cstr) {
    for (int i = 0; i < list.size(); i++) {
      if (((String) list.get(i)).equalsIgnoreCase(cstr)) {
        return i;
      }
    }
    return -1;
  }

  private int searchStartLastIndex(List list, String cstr) {
    for (int i = list.size() - 1; i >= 0; i--) {
      if (((String) list.get(i)).equalsIgnoreCase(cstr)) {
        return i;
      }
    }
    return -1;
  }

  private boolean newData(int dd, int mm, int yyyy, Calendar cal) {
    //String date = cal.getTime().toString();

    if ((dd == cal.get(Calendar.DATE)) && (mm == cal.get(Calendar.MONTH)) && (yyyy == cal.get(Calendar.YEAR)))
      return true;
    else
      return false;
  }

  public static void main(String[] args) {

    Extractor ex = new Loterie2Extractor();

    Calendar cal = new GregorianCalendar(2003, Calendar.MAY, 3);
    Draw zie = ex.extractDraw(cal);
    
    
    LottoGainQuota lg = ex.extractLottoGainQuota(cal);
    JokerGainQuota jg = ex.extractJokerGainQuota(cal);
    ExtraJokerGainQuota ejg = ex.extractExtraJokerGainQuota(cal);


    System.out.println(zie);
    System.out.println(lg);
    System.out.println(jg);
    System.out.println(ejg);
    System.out.println();
  }

}
