package com.managestar.recurrance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/****

Describes a single recurrance rule.  This object is actually a facade for a number
of smaller objects that a user should never need to worry about.

****/
public class RecurranceRuleXml extends RecurranceRuleRfc {

  private static final Log LOGGER = LogFactory.getLog(RecurranceRuleXml.class);
  public static boolean verbose = true;

  String originalRule;

  /***
  Creates a recurrance object based on the rule. The rule is expected to be in recurrance.dtd xml.
  This method will convert the rule into RRULE format and then use the parent to do all the work.
  @param rule the rule description
  @param start the inclusive start of the range over which to recurr
  @param end the inclusive end of the range over which to recurr
  ***/
  RecurranceRuleXml(String inputRule, Calendar start, Calendar end) {
    super(convertXmlToRRule(inputRule.trim()), start, end);
    originalRule = inputRule;
  }

  /***
  @return the rule used to create this object as xml
  ***/
  public String getRuleAsXml() {
    return convertRRuleToXml(rule.trim());
  }

  /***
  @return xml -> rrule
  ***/
  public static String convertXmlToRRule(String input) {
    String rrule = null;
    try {
      input = input.trim();
      input = input.replace('\n', ' ');
      input = input.replace('\t', ' ');
      String temp;
      List subRuleList = new ArrayList();

      // parse freq and interval
      temp = getBlock("every", input);
      addFreqRules(subRuleList, temp);

      // parse week start
      temp = getBlock("week-start", input);
      addWeekStartRule(subRuleList, temp);

      // parse month / month names
      temp = getBlock("month-number", input);
      addMonthRules(subRuleList, temp);

      // parse +/- ranged numbers
      temp = getBlock("week-number", input);
      addIntRules(subRuleList, temp, "weekno");
      temp = getBlock("year-day", input);
      addIntRules(subRuleList, temp, "yearday");
      temp = getBlock("date", input);
      addIntRules(subRuleList, temp, "monthday");

      // parse week sentences
      temp = getBlock("week-day", input);
      addWeekRules(subRuleList, temp);

      // parse +ranged numbers
      temp = getBlock("hour", input);
      addIntRules(subRuleList, temp, "hour");
      temp = getBlock("minute", input);
      addIntRules(subRuleList, temp, "minute");
      temp = getBlock("second", input);
      addIntRules(subRuleList, temp, "second");

      temp = getBlock("max-occurrences", input);
      //System.err.println(">>>"+temp);
      addCountRules(subRuleList, temp);

      // put all the pieces together into a rule
      rrule = (String)subRuleList.get(0);
      for (int ctr = 1; ctr < subRuleList.size(); ctr++) {
        rrule += ";" + (String)subRuleList.get(ctr);
      }

    } catch (RuntimeException e) {
      LOGGER.error(input, e);
      throw e;
    }
    return rrule;
  }

  /***
  @return rrule -> xml
  ***/
  public static String convertRRuleToXml(String input) {
    String[] rules = input.split(";");

    String freqUnit = null;
    String interval = null;

    String bySecond = null;
    String byMinute = null;
    String byHour = null;
    String byDay = null;
    String byMonthDay = null;
    String byYearDay = null;
    String byWeekNo = null;
    String byMonth = null;
    String count = null;
    String weekStart = null;

    //String type = null;
    String result = null;

    boolean needOn = false;

    input = input.trim();

    for (int ctr = 0; ctr < rules.length; ctr++) {

      LOGGER.debug("CHECKING " + rules[ctr]);

      if (rules[ctr].startsWith("FREQ")) {
        freqUnit = getValue(rules[ctr]);
        if ("YEARLY".equals(freqUnit)) {
          freqUnit = "year";
        }
        if ("MONTHLY".equals(freqUnit)) {
          freqUnit = "month";
        }
        if ("WEEKLY".equals(freqUnit)) {
          freqUnit = "week";
        }
        if ("DAILY".equals(freqUnit)) {
          freqUnit = "day";
        }
        if ("HOURLY".equals(freqUnit)) {
          freqUnit = "hour";
        }
        if ("MINUTELY".equals(freqUnit)) {
          freqUnit = "minute";
        }
        if ("SECONDLY".equals(freqUnit)) {
          freqUnit = "second";
        }
      } else if (rules[ctr].startsWith("INTERVAL")) {
        interval = getValue(rules[ctr]);
      } else if (rules[ctr].startsWith("BYSECOND")) {
        needOn = true;
        bySecond = getValue(rules[ctr]);
      } else if (rules[ctr].startsWith("BYMINUTE")) {
        needOn = true;
        byMinute = getValue(rules[ctr]);
      } else if (rules[ctr].startsWith("BYHOUR")) {
        needOn = true;
        byHour = getValue(rules[ctr]);
      } else if (rules[ctr].startsWith("BYMONTHDAY")) {
        needOn = true;
        byMonthDay = convertNumberList(getValue(rules[ctr]));
      } else if (rules[ctr].startsWith("BYYEARDAY")) {
        needOn = true;
        byYearDay = convertNumberList(getValue(rules[ctr]));
      } else if (rules[ctr].startsWith("BYWEEKNO")) {
        needOn = true;
        byWeekNo = convertNumberList(getValue(rules[ctr]));
      } else if (rules[ctr].startsWith("BYMONTH")) {
        needOn = true;
        byMonth = convertNumberList(getValue(rules[ctr]));
      } else if (rules[ctr].startsWith("BYDAY")) {
        needOn = true;
        byDay = convertDayList(getValue(rules[ctr]));
      } else if (rules[ctr].startsWith("COUNT")) {
        count = getValue(rules[ctr]);
      } else if (rules[ctr].startsWith("WKST")) {
        weekStart = makeFullDayName(getValue(rules[ctr]));
      }
    }

    if (freqUnit == null) {
      throw new IllegalArgumentException("ERROR: Your input was not a valid RRULE string! (FREQUENCY was missing)");
    }

    result = "<recurrance>\n";
    if (interval == null) {
      interval = "1";
    }
    result += "  <every>" + interval.trim() + " " + freqUnit.trim();
    if (interval.length() > 1 || !interval.startsWith("1")) {
      result += "s";
    }
    result += "</every>\n";

    if (weekStart != null) {
      result += "  <week-start>" + weekStart + "</week-start>\n";
    }

    if (needOn) {
      result += "  <on>\n";
      if (byMonth != null) {
        result += "    <month-number>" + byMonth.trim() + "</month-number>\n";
      }
      if (byWeekNo != null) {
        result += "    <week-number>" + byWeekNo.trim() + "</week-number>\n";
      }
      if (byYearDay != null) {
        result += "    <year-day>" + byYearDay.trim() + "</year-day>\n";
      }
      if (byMonthDay != null) {
        result += "    <date>" + byMonthDay.trim() + "</date>\n";
      }
      if (byDay != null) {
        result += "    <week-day>" + byDay.trim() + "</week-day>\n";
      }
      if (byHour != null) {
        result += "    <hour>" + byHour.trim() + "</hour>\n";
      }
      if (byMinute != null) {
        result += "    <minute>" + byMinute.trim() + "</minute>\n";
      }
      if (bySecond != null) {
        result += "    <second>" + bySecond.trim() + "</second>\n";
      }
      result += "  </on>\n";
    }
    if (count != null) {
      result += "  <max-occurrences>" + count.trim() + "<max-occurrences>\n";
    }
    result += "</recurrance>\n";

    return result;
  }

  //=========================== UTILTIES

  /***
  get the contents of an xml tag with the wildly trusting assumption that there will only be one
  instance of that tag present in the block.
  @param target the tag to find
  @param fullText the text to search
  @return the contents of the tag
  ***/
  public static String getBlock(String target, String fullText) {
    String temp = null;
    try {
      int first;
      int last;

      if (target.equals("every")) {
        first = fullText.indexOf(target);
        last = fullText.lastIndexOf(target);
      } else if (target.equals("week-start")) {
        first = fullText.indexOf(target);
        last = fullText.lastIndexOf(target);
      } else {
        int beginNotes = fullText.indexOf("<on>");
        first = fullText.indexOf(target, beginNotes);
        last = fullText.lastIndexOf(target);
      }

      // not in this command
      if (first == -1 && last == -1) {
        return null;
      }

      // mismatched tags!
      if ((first == -1 && last > -1) || (first > -1 && last == -1)) {
        return null;
      }

      // remove the xml artifacts
      //      System.err.println("==f"+(first+target.length()+1));
      //      System.err.println("==l"+(last-1));

      temp = fullText.substring(first + target.length() + 1, last - 1);

      //      System.err.println("=2="+temp);

      if (-1 != temp.indexOf(">")) {
        temp = temp.substring(temp.indexOf(">") + 1);
      }
      if (-1 != temp.indexOf("<")) {
        temp = temp.substring(0, temp.indexOf("<"));
      }

      //      System.err.println("=3="+temp);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: unable to parse " + target);
    }

    return temp;
  }

  /***
  take the text in temp and make a FREQ and INTERVAL rule.  Append those rules to the list
  ***/
  public static void addFreqRules(List subRuleList, String temp) {
    try {

      String number = temp.substring(0, temp.indexOf(" "));
      String unit = temp.substring(temp.lastIndexOf(" "));

      unit = unit.toUpperCase();
      if (unit.endsWith("S")) {
        unit = unit.substring(0, unit.length() - 1);
      }

      unit += "LY";
      unit = unit.trim();

      if (unit.equals("DAYLY")) {
        unit = "DAILY";
      }

      subRuleList.add("FREQ=" + unit.trim());
      subRuleList.add("INTERVAL=" + number.trim());

    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: The input string did not contain valid recurrance xml! ");
    }
  }

  /***
  take the text in temp and make a FREQ and INTERVAL rule.  Append those rules to the list
  ***/
  public static void addMonthRules(List subRuleList, String subRule) {
    try {

      if (subRule == null) {
        return;
      }

      StringTokenizer stk = new StringTokenizer(subRule, ",");
      String result = "BYMONTH=";

      while (stk.hasMoreTokens()) {
        String temp = stk.nextToken().toUpperCase();
        if ("JANUARY".equals(temp)) {
          temp = "1";
        }
        if ("FEBRUARY".equals(temp)) {
          temp = "2";
        }
        if ("MARCH".equals(temp)) {
          temp = "3";
        }
        if ("APRIL".equals(temp)) {
          temp = "4";
        }
        if ("MAY".equals(temp)) {
          temp = "5";
        }
        if ("JUNE".equals(temp)) {
          temp = "6";
        }
        if ("JULY".equals(temp)) {
          temp = "7";
        }
        if ("AUGUST".equals(temp)) {
          temp = "8";
        }
        if ("SEPTEMBER".equals(temp)) {
          temp = "9";
        }
        if ("OCTOBER".equals(temp)) {
          temp = "10";
        }
        if ("NOVEMBER".equals(temp)) {
          temp = "11";
        }
        if ("DECEMBER".equals(temp)) {
          temp = "12";
        }

        result += convertNumber(temp);
        if (stk.hasMoreTokens()) {
          result += ",";
        }
      }

      subRuleList.add(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: unable to parse 'month' tag");
    }
  }

  /***
  Makes a list of numbers, properly handling. 'to the last' and 'numberth' stuff that may be included.
  ***/
  public static void addIntRules(List subRuleList, String command, String subRuleType) {
    if (command == null) {
      return;
    }

    try {

      if (subRuleType == null) {
        return;
      }

      StringTokenizer stk = new StringTokenizer(command, ",");
      String result = "BY" + subRuleType.toUpperCase() + "=";

      while (stk.hasMoreTokens()) {
        String temp = stk.nextToken().toUpperCase();

        // if there is any text after the number, assume it is 'the last'
        if (-1 != temp.indexOf(" ")) {
          temp = "-" + temp;
        }
        temp = convertNumber(temp);

        result += temp;
        if (stk.hasMoreTokens()) {
          result += ",";
        }
      }

      subRuleList.add(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: unable to parse '" + command + "' tag");
    }
  }

  /***
  Parse the wordy description of a weekday into a tighter, more machine readable one
  ***/
  public static void addWeekRules(List subRuleList, String command) {
    try {

      if (command == null) {
        return;
      }

      StringTokenizer stk = new StringTokenizer(command, ",");
      String result = "BYDAY=";

      while (stk.hasMoreTokens()) {
        String temp = stk.nextToken().toUpperCase();

        if (verbose) {
          String[] parts = temp.split(" ");

          // just the day
          if (parts.length == 1) {
            //System.err.println("--1 " + parts[0]);
            temp = convertWeekDay(parts[0]);
          }
          // a number and the day
          if (parts.length == 2) {
            //System.err.println("--2");
            temp = convertNumber(parts[0]) + convertWeekDay(parts[1]);
          }
          // a negated number and the day
          if (parts.length > 2) {
            //System.err.println("--3");
            temp = "-" + convertNumber(parts[0]) + convertWeekDay(parts[parts.length - 1]);
          }
        }

        result += temp;
        if (stk.hasMoreTokens()) {
          result += ",";
        }
      }

      subRuleList.add(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: unable to parse 'weekday' tag");
    }
  }

  public static void addCountRules(List subRuleList, String command) {
    try {

      if (command == null) {
        return;
      }

      subRuleList.add("COUNT=" + command);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("  ERROR: unable to parse 'weekday' tag");
    }
  }

  public static void addWeekStartRule(List subRuleList, String command) {
    if (command == null) {
      return;
    }

    String temp = convertWeekDay(command);
    subRuleList.add("WKST=" + temp);
  }

  /***
  Converst a fully named week day name into shortened name
  ***/
  public static String convertWeekDay(String name) {
    return name.toUpperCase().substring(0, 2);
  }

  /***
  Get rid of any numbereth junk
  ***/
  public static String convertNumber(String number) {
    for (int ctr = 0; ctr < number.length(); ctr++) {
      if (!(Character.isDigit(number.charAt(ctr)) || number.charAt(ctr) == '-' || number.charAt(ctr) == '+')) {
        return number.substring(0, ctr);
      }
    }
    return number;
  }

  /***
  @return anything after the =
  ***/
  public static String getValue(String input) {
    return input.substring(input.lastIndexOf("=") + 1);
  }

  /***
  Take a list of RRULE numbers as a string a make a string list of xml printed numbers
  ***/
  public static String convertNumberList(String list) {
    String[] numArray = list.split(",");
    String result = "";

    for (int ctr = 0; ctr < numArray.length; ctr++) {
      boolean needNeg = false;

      if (verbose) {
        if (numArray[ctr].startsWith("-")) {
          needNeg = true;
          numArray[ctr] = numArray[ctr].substring(1);
        }
        numArray[ctr] = addEth(numArray[ctr]);
        if (needNeg) {
          numArray[ctr] = numArray[ctr] + " to the last";
        }
      }

      result += numArray[ctr];
      if (ctr + 1 < numArray.length) {
        result += ",";
      }
    }

    return result;
  }

  /***
  Adds user friendly junk to the number
  ***/
  public static String addEth(String input) {
    if (verbose) {
      if (input.endsWith("13")) {
        return input + "th";
      }
      if (input.endsWith("12")) {
        return input + "th";
      }
      if (input.endsWith("11")) {
        return input + "th";
      }
      if (input.endsWith("0")) {
        return input + "th";
      }
      if (input.endsWith("9")) {
        return input + "th";
      }
      if (input.endsWith("8")) {
        return input + "th";
      }
      if (input.endsWith("7")) {
        return input + "th";
      }
      if (input.endsWith("6")) {
        return input + "th";
      }
      if (input.endsWith("5")) {
        return input + "th";
      }
      if (input.endsWith("4")) {
        return input + "th";
      }
      if (input.endsWith("3")) {
        return input + "rd";
      }
      if (input.endsWith("2")) {
        return input + "nd";
      }
      if (input.endsWith("1")) {
        return input + "st";
      }
    }
    return input;
  }

  /***
  Take a list of RRULE week days as a string and make a string list of xml printed week days
  ***/
  public static String convertDayList(String list) {
    String[] numArray = list.split(",");
    String result = "";

    for (int ctr = 0; ctr < numArray.length; ctr++) {
      boolean needNeg = false;

      if (verbose) {
        String temp = numArray[ctr].substring(numArray[ctr].length() - 2);
        temp = makeFullDayName(temp).trim();

        // this is only true if there is a number attached to the date
        if (temp.length() != numArray[ctr].length()) {
          numArray[ctr] = numArray[ctr].substring(0, numArray[ctr].length() - 2);

          // make the name wordy
          if (numArray[ctr].startsWith("-")) {
            needNeg = true;
            numArray[ctr] = numArray[ctr].substring(1);
          }
          numArray[ctr] = addEth(numArray[ctr]);
          if (needNeg) {
            numArray[ctr] = numArray[ctr] + " to the last " + temp;
          } else {
            numArray[ctr] += " " + temp;
          }
        } else {
          numArray[ctr] = temp;
        }
        numArray[ctr] = numArray[ctr].toLowerCase();
      }

      result += numArray[ctr].trim();
      if (ctr + 1 < numArray.length) {
        result += ",";
      }
    }

    return result.trim();
  }

  /***
  @param input the string to convert
  @return a full day name out of a two letter day name
  ***/
  public static String makeFullDayName(String input) {
    if (verbose) {
      if ("SU".equals(input)) {
        return "sunday";
      }
      if ("MO".equals(input)) {
        return "monday";
      }
      if ("TU".equals(input)) {
        return "tuesday";
      }
      if ("WE".equals(input)) {
        return "wednesday";
      }
      if ("TH".equals(input)) {
        return "thursday";
      }
      if ("FR".equals(input)) {
        return "friday";
      }
      if ("SA".equals(input)) {
        return "saturday";
      }
    }
    return input;

  }

  public String getRule() {
    return "[" + originalRule + "\n" + rule + "]";
  }

}
