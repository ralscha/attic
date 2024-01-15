package ch.sr.lotto.extract;

import java.io.*;
import java.util.*;

import ch.sr.lotto.db.*;
import com.hothouseobjects.tags.*;

public abstract class Extractor {

  private static final String MONTHS[] = { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };

  private static final String MONTHSEN[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

  public abstract Draw extractDraw(Calendar nextDate);
  public abstract LottoGainQuota extractLottoGainQuota(Calendar nextDate);
  public abstract JokerGainQuota extractJokerGainQuota(Calendar nextDate);
  public abstract ExtraJokerGainQuota extractExtraJokerGainQuota(Calendar nextDate);

  protected List createWordList(Reader reader) throws FileNotFoundException, IOException {

    TagTiller myTiller = new TagTiller(reader);
    myTiller.runTiller();

    Tag thePage = myTiller.getTilledTags();
    LinkedText texts = thePage.getLinkedText(false);

    List wordList = new ArrayList();

    String textStr = texts.toString();

    BufferedReader brs = new BufferedReader(new StringReader(textStr));

    String line = null;

    while ((line = brs.readLine()) != null) {
      String trimmed = line.trim();
      if (!trimmed.equals("")) {
        StringTokenizer st = new StringTokenizer(trimmed);
        while(st.hasMoreTokens()) {        
          wordList.add(st.nextToken());
        }
      }
    }

    brs.close();

    return wordList;
  }

  protected String removeNonDigits(String digit) {
    StringBuffer sb = new StringBuffer();
    char[] chars = digit.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (Character.isDigit(chars[i]))
        sb.append(chars[i]);
    }
    return sb.toString();
  }

  protected String removePoint(String in) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (in.charAt(i) != '.')
        sb.append(in.charAt(i));
    }
    return sb.toString();
  }

  protected int getMonthNumber(String monthname) {
    for (int i = 0; i < MONTHS.length; i++) {
      if (monthname.equalsIgnoreCase(MONTHS[i])) {
        return i;
      }
    }
    return -1;
  }

  protected int getEnglishMonthNumber(String monthname) {
    for (int i = 0; i < MONTHSEN.length; i++) {
      if (monthname.equalsIgnoreCase(MONTHSEN[i])) {
        return i;
      }
    }
    return -1;
  }

  protected String stringConv(String in) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.'))
        sb.append(in.charAt(i));
    }
    
    String result = sb.toString();
    if (result.length() > 0) {
      if ((result.lastIndexOf('.') == (result.length() - 1))) {
        return result.substring(0, result.length() - 1);
      } else {
        return result;
      }
    } else {
      return result;
    }
  }
}