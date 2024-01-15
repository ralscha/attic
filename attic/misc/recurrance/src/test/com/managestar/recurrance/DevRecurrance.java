package com.managestar.recurrance;

import java.text.*;
import java.util.*;

public class DevRecurrance {
  public static boolean debug = false;
  private static Calendar start, end;

  public static void main(String[] args) {
    try {

      TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

      System.err.println("" + foo(getLong()));
      System.err.println("" + foo(getString()));
      System.err.println("" + foo(getString2()));
      //System.err.println( ""+ foo( null ) );  // destined to fail, not allowed.

      String rule;
      Recurrance r = null;


      start = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
      start.set(Calendar.YEAR, 2002);
      start.set(Calendar.MONTH, Calendar.JUNE);
      start.set(Calendar.DATE, 26);
      start.set(Calendar.HOUR_OF_DAY, 19);
      start.set(Calendar.MINUTE, 54);
      start.set(Calendar.SECOND, 40);
      start.set(Calendar.MILLISECOND, 0);
    

      end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
      end.set(Calendar.YEAR, 2007);
      end.set(Calendar.MONTH, Calendar.JUNE);
      end.set(Calendar.DATE, 25);
      end.set(Calendar.HOUR_OF_DAY, 19);
      end.set(Calendar.MINUTE, 54);
      end.set(Calendar.SECOND, 40);
      end.set(Calendar.MILLISECOND, 0);


      System.out.println("start " + start.getTime());
      System.out.println("end   " + end.getTime());

      ArrayList expected = new ArrayList();

      SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
      //DateFormat df = new SimpleDateFormat();

      //RecurranceRuleXml.verbose = true;
      RecurranceRuleXml.verbose = false;

      //------- Year freq tests
      System.out.println("=============================================== YEAR FREQ");
      
      
      r = new Recurrance("FREQ=YEARLY;INTERVAL=1;BYDAY=2SU;BYMONTH=5", start, end);
      print("FREQ=YEARLY;INTERVAL=1;BYDAY=2SU;BYMONTH=5", r);
      
/*
      System.err.println(">> " + new Date().getTime());
      rule = "FREQ=MINUTELY;INTERVAL=10";
      r = new Recurrance(rule, start, null);
      print(rule, r);
      System.err.println(">> " + new Date().getTime());
*/
      rule = "FREQ=YEARLY;INTERVAL=1";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Jun 26 19:54:40 GMT 2004"));
      expected.add(df.parse("Sun Jun 26 19:54:40 GMT 2005"));
      expected.add(df.parse("Mon Jun 26 19:54:40 GMT 2006"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;COUNT=1;INTERVAL=1;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;COUNT=3;INTERVAL=2;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Jun 26 19:54:40 GMT 2004"));
      expected.add(df.parse("Mon Jun 26 19:54:40 GMT 2006"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;COUNT=3;INTERVAL=1;BYMONTHDAY=7,8,12;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sat Jun 07 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Jun 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jun 12 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //------- Month freq tests
      System.out.println("=============================================== MONTH FREQ");

      System.out.println(" one item, monthly");
      rule = "FREQ=MONTHLY;INTERVAL=1";
      r = new Recurrance(rule, start, end);
      checkSize(rule, r.getAllMatchingDates(), 60, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      checkSize(rule, r.getAllMatchingDates(), 60, r);

      System.out.println(" 5 items, every other month");
      rule = "FREQ=MONTHLY;COUNT=5;INTERVAL=2";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Aug 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Oct 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Dec 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Feb 26 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" every year in jan, march, may");
      rule = "FREQ=YEARLY;COUNT=5;INTERVAL=1;BYMONTH=1,3,5;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sun Jan 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Mar 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon May 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon Jan 26 19:54:40 GMT 2004"));
      expected.add(df.parse("Fri Mar 26 19:54:40 GMT 2004"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" Every seven months have a noon and midnight meeting");
      rule = "FREQ=MONTHLY;INTERVAL=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sun Jan 26 00:00:00 GMT 2003"));
      expected.add(df.parse("Sun Jan 26 12:00:00 GMT 2003"));
      expected.add(df.parse("Tue Aug 26 00:00:00 GMT 2003"));
      expected.add(df.parse("Tue Aug 26 12:00:00 GMT 2003"));
      expected.add(df.parse("Fri Mar 26 00:00:00 GMT 2004"));
      expected.add(df.parse("Fri Mar 26 12:00:00 GMT 2004"));
      expected.add(df.parse("Tue Oct 26 00:00:00 GMT 2004"));
      expected.add(df.parse("Tue Oct 26 12:00:00 GMT 2004"));
      expected.add(df.parse("Thu May 26 00:00:00 GMT 2005"));
      expected.add(df.parse("Thu May 26 12:00:00 GMT 2005"));
      expected.add(df.parse("Mon Dec 26 00:00:00 GMT 2005"));
      expected.add(df.parse("Mon Dec 26 12:00:00 GMT 2005"));
      expected.add(df.parse("Wed Jul 26 00:00:00 GMT 2006"));
      expected.add(df.parse("Wed Jul 26 12:00:00 GMT 2006"));
      expected.add(df.parse("Mon Feb 26 00:00:00 GMT 2007"));
      expected.add(df.parse("Mon Feb 26 12:00:00 GMT 2007"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" Every seventh month of the year have a noon and midnight meeting");
      rule = "FREQ=YEARLY;INTERVAL=1;BYMONTH=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Fri Jul 26 00:00:00 GMT 2002"));
      expected.add(df.parse("Fri Jul 26 12:00:00 GMT 2002"));
      expected.add(df.parse("Sat Jul 26 00:00:00 GMT 2003"));
      expected.add(df.parse("Sat Jul 26 12:00:00 GMT 2003"));
      expected.add(df.parse("Mon Jul 26 00:00:00 GMT 2004"));
      expected.add(df.parse("Mon Jul 26 12:00:00 GMT 2004"));
      expected.add(df.parse("Tue Jul 26 00:00:00 GMT 2005"));
      expected.add(df.parse("Tue Jul 26 12:00:00 GMT 2005"));
      expected.add(df.parse("Wed Jul 26 00:00:00 GMT 2006"));
      expected.add(df.parse("Wed Jul 26 12:00:00 GMT 2006"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" During the last threes months of the year months have a noon and midnight meeting");
      rule = "FREQ=YEARLY;INTERVAL=1;BYMONTH=10,11,12;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0";
      r = new Recurrance(rule, start, end);
      checkSize(rule, r.getAllMatchingDates(), 30, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      checkSize(rule, r.getAllMatchingDates(), 30, r);

      System.out.println("=============================================== WEEKLY FREQ");

      //------- Hourly freq tests

      System.out.println(" the 1,10,20,30,40,50th weeks on Thursday");
      rule = "FREQ=YEARLY;COUNT=20;INTERVAL=1;BYWEEKNO=1,10,20,30,40,50;BYDAY=TH";
      r = new Recurrance(rule, start, end);
      expected.clear();

      expected.add(df.parse("Thu Jul 25 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Oct 03 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Dec 12 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jan 02 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 06 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu May 15 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jul 24 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Oct 02 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Dec 11 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 01 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Mar 04 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu May 13 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Jul 22 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Sep 30 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Dec 09 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Jan 6 19:54:40 GMT 2005"));
      expected.add(df.parse("Thu Mar 10 19:54:40 GMT 2005"));
      expected.add(df.parse("Thu May 19 19:54:40 GMT 2005"));
      expected.add(df.parse("Thu Jul 28 19:54:40 GMT 2005"));
      expected.add(df.parse("Thu Oct 6 19:54:40 GMT 2005"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //      debug=true;                                               // MOVE THIS LINE AROUND TO FIND OUT MORE ABOUT THE TEST THAT IS RUN

      System.out.println(" every other week for 20 weeks");
      rule = "FREQ=WEEKLY;COUNT=20;INTERVAL=2;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jul 10 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jul 24 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Aug 07 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Aug 21 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Sep 04 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Sep 18 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Oct 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Oct 16 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Oct 30 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Nov 13 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Nov 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Dec 11 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Dec 25 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jan 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Jan 22 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Feb 05 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Feb 19 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Mar 05 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Mar 19 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println("=============================================== DAILY FREQ");

      System.out.println(" every three days at 10:30");
      rule = "FREQ=DAILY;COUNT=20;INTERVAL=3;BYHOUR=10;BYMINUTE=30;BYSECOND=0";
      r = new Recurrance(rule, start, end);
      expected.clear();

      expected.add(df.parse("Sat Jun 29 10:30:00 GMT 2002"));
      expected.add(df.parse("Tue Jul 02 10:30:00 GMT 2002"));
      expected.add(df.parse("Fri Jul 05 10:30:00 GMT 2002"));
      expected.add(df.parse("Mon Jul 08 10:30:00 GMT 2002"));
      expected.add(df.parse("Thu Jul 11 10:30:00 GMT 2002"));
      expected.add(df.parse("Sun Jul 14 10:30:00 GMT 2002"));
      expected.add(df.parse("Wed Jul 17 10:30:00 GMT 2002"));
      expected.add(df.parse("Sat Jul 20 10:30:00 GMT 2002"));
      expected.add(df.parse("Tue Jul 23 10:30:00 GMT 2002"));
      expected.add(df.parse("Fri Jul 26 10:30:00 GMT 2002"));
      expected.add(df.parse("Mon Jul 29 10:30:00 GMT 2002"));
      expected.add(df.parse("Thu Aug 01 10:30:00 GMT 2002"));
      expected.add(df.parse("Sun Aug 04 10:30:00 GMT 2002"));
      expected.add(df.parse("Wed Aug 07 10:30:00 GMT 2002"));
      expected.add(df.parse("Sat Aug 10 10:30:00 GMT 2002"));
      expected.add(df.parse("Tue Aug 13 10:30:00 GMT 2002"));
      expected.add(df.parse("Fri Aug 16 10:30:00 GMT 2002"));
      expected.add(df.parse("Mon Aug 19 10:30:00 GMT 2002"));
      expected.add(df.parse("Thu Aug 22 10:30:00 GMT 2002"));
      expected.add(df.parse("Sun Aug 25 10:30:00 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println("=============================================== BYYEARDAY");

      System.out.println(" the 1, 130,-130 and last day of year");
      rule = "FREQ=YEARLY;COUNT=20;INTERVAL=1;BYYEARDAY=1,130,-130,-1";
      r = new Recurrance(rule, start, end);
      expected.clear();

      expected.add(df.parse("Sat Aug 24 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Dec 31 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jan 01 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat May 10 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Aug 24 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Dec 31 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 01 19:54:40 GMT 2004"));
      expected.add(df.parse("Sun May 09 19:54:40 GMT 2004"));
      expected.add(df.parse("Tue Aug 24 19:54:40 GMT 2004"));
      expected.add(df.parse("Fri Dec 31 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Jan 01 19:54:40 GMT 2005"));
      expected.add(df.parse("Tue May 10 19:54:40 GMT 2005"));
      expected.add(df.parse("Wed Aug 24 19:54:40 GMT 2005"));
      expected.add(df.parse("Sat Dec 31 19:54:40 GMT 2005"));
      expected.add(df.parse("Sun Jan 01 19:54:40 GMT 2006"));
      expected.add(df.parse("Wed May 10 19:54:40 GMT 2006"));
      expected.add(df.parse("Thu Aug 24 19:54:40 GMT 2006"));
      expected.add(df.parse("Sun Dec 31 19:54:40 GMT 2006"));
      expected.add(df.parse("Mon Jan 01 19:54:40 GMT 2007"));
      expected.add(df.parse("Thu May 10 19:54:40 GMT 2007"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println("=============================================== EXAMPLES FROM RFC");

      System.out.println("every other year in january on all sundays at 8:30 and 9:30");
      rule = "FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU;BYHOUR=8,9;BYMINUTE=30";
      r = new Recurrance(rule, start, end);
      expected.clear();
      checkSize(rule, r.getAllMatchingDates(), 18, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      checkSize(rule, r.getAllMatchingDates(), 18, r);

      System.out.println("every other year in january on the last sunday of the year at 8:30 and 9:30");
      rule = "FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=-1SU;BYHOUR=8,9;BYMINUTE=30";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sun Jan 25 08:30:40 GMT 2004"));
      expected.add(df.parse("Sun Jan 25 09:30:40 GMT 2004"));
      expected.add(df.parse("Sun Jan 29 08:30:40 GMT 2006"));
      expected.add(df.parse("Sun Jan 29 09:30:40 GMT 2006"));

      /*
      expected.add( df.parse("Sun Dec 29 08:30:40 GMT 2002") );
      expected.add( df.parse("Sun Dec 29 09:30:40 GMT 2002") );
      expected.add( df.parse("Sun Dec 26 08:30:40 GMT 2004") );
      expected.add( df.parse("Sun Dec 26 09:30:40 GMT 2004") );
      expected.add( df.parse("Sun Dec 31 08:30:40 GMT 2006") );
      expected.add( df.parse("Sun Dec 31 09:30:40 GMT 2006") );
      */
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println("=============================================== EXAMPLES FREQ");
      rule = "FREQ=YEARLY;INTERVAL=1;BYMONTH=1;BYDAY=SU,MO,TU,WE,TH,FR;COUNT=20";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jan 01 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 02 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Jan 03 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Jan 05 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon Jan 06 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Jan 07 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Jan 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 09 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Jan 10 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Jan 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon Jan 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Jan 14 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Jan 15 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 16 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Jan 17 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Jan 19 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon Jan 20 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Jan 21 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Jan 22 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 23 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=WEEKLY;INTERVAL=3;WKST=SU;BYDAY=TU,TH;COUNT=30";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 16 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 18 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Aug 06 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 08 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Aug 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Sep 17 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Sep 19 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Oct 08 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Oct 10 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Oct 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Oct 31 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Nov 19 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Nov 21 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Dec 10 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Dec 12 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jan 02 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Jan 21 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jan 23 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Feb 11 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Feb 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Mar 04 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 06 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Mar 25 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 27 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue Apr 15 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Apr 17 19:54:40 GMT 2003"));
      expected.add(df.parse("Tue May 06 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu May 08 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=WEEKLY;INTERVAL=1;COUNT=10;WKST=SU;BYDAY=TU,TH";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 04 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 09 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 11 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 16 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 18 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 23 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 25 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Jul 30 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=WEEKLY;INTERVAL=2;WKST=SU;BYDAY=MO,WE,FR;COUNT=10";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Jun 28 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Jul 08 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jul 10 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Jul 12 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Jul 22 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jul 24 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Jul 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Aug 05 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Aug 07 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;COUNT=10;BYDAY=1FR";
      r = new Recurrance(rule, start, end);

      expected.clear();
      expected.add(df.parse("Fri Jul 05 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Aug 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Sep 06 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Oct 04 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Nov 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Dec 06 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Jan 03 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Feb 07 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Mar 07 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Apr 04 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;INTERVAL=2;COUNT=10;BYDAY=1SU,-1SU";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sun Jun 30 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Aug 04 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Aug 25 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Oct 06 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Oct 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Dec 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Dec 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Feb 02 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Feb 23 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Apr 06 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;COUNT=6;BYDAY=-2MO";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Mon Jul 22 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Aug 19 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Sep 23 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Oct 21 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Nov 18 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Dec 23 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;BYMONTHDAY=-3;COUNT=10";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Fri Jun 28 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Jul 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Sep 28 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Oct 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Nov 28 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Dec 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jan 29 19:54:40 GMT 2003"));
      expected.add(df.parse("Wed Feb 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Mar 29 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;COUNT=10;BYMONTHDAY=2,15";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Tue Jul 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Jul 15 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Aug 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 15 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Sep 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Sep 15 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Oct 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Oct 15 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Nov 02 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Nov 15 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;COUNT=10;BYMONTHDAY=1,-1";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sun Jun 30 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Jul 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jul 31 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Aug 31 19:54:40 GMT 2002"));
      expected.add(df.parse("Sun Sep 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Mon Sep 30 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Oct 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Oct 31 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Nov 01 19:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;INTERVAL=18;COUNT=10;BYMONTHDAY=10,11,12,13,14";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Dec 10 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Dec 11 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Dec 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Dec 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Sun Dec 14 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Jun 10 19:54:40 GMT 2005"));
      expected.add(df.parse("Sat Jun 11 19:54:40 GMT 2005"));
      expected.add(df.parse("Sun Jun 12 19:54:40 GMT 2005"));
      expected.add(df.parse("Mon Jun 13 19:54:40 GMT 2005"));
      expected.add(df.parse("Tue Jun 14 19:54:40 GMT 2005"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;INTERVAL=3;COUNT=10;BYYEARDAY=1,100,200";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Fri Jul 19 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Jan 01 19:54:40 GMT 2005"));
      expected.add(df.parse("Sun Apr 10 19:54:40 GMT 2005"));
      expected.add(df.parse("Tue Jul 19 19:54:40 GMT 2005"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;BYDAY=20MO";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Mon May 19 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon May 17 19:54:40 GMT 2004"));
      expected.add(df.parse("Mon May 16 19:54:40 GMT 2005"));
      expected.add(df.parse("Mon May 15 19:54:40 GMT 2006"));
      expected.add(df.parse("Mon May 14 19:54:40 GMT 2007"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;BYWEEKNO=20;BYDAY=MO;COUNT=10";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Mon May 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Mon May 10 19:54:40 GMT 2004"));
      expected.add(df.parse("Mon May 16 19:54:40 GMT 2005"));
      expected.add(df.parse("Mon May 15 19:54:40 GMT 2006"));
      expected.add(df.parse("Mon May 14 19:54:40 GMT 2007"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;BYMONTH=3;BYDAY=TH;COUNT=10";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Mar 06 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 20 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 27 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Mar 04 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Mar 11 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Mar 18 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Mar 25 19:54:40 GMT 2004"));
      expected.add(df.parse("Thu Mar 03 19:54:40 GMT 2005"));
      expected.add(df.parse("Thu Mar 10 19:54:40 GMT 2005"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;BYDAY=TH;BYMONTH=6,7,8;COUNT=15";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 27 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 04 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 11 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 18 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jul 25 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 01 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 08 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 15 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 22 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Aug 29 19:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 05 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jun 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jun 19 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jun 26 19:54:40 GMT 2003"));
      expected.add(df.parse("Thu Jul 03 19:54:40 GMT 2003"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //==== Tricky culling after the fact.  :S

      rule = "FREQ=MONTHLY;BYDAY=FR;BYMONTHDAY=13;COUNT=10";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Fri Sep 13 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Dec 13 19:54:40 GMT 2002"));
      expected.add(df.parse("Fri Jun 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Fri Feb 13 19:54:40 GMT 2004"));
      expected.add(df.parse("Fri Aug 13 19:54:40 GMT 2004"));
      expected.add(df.parse("Fri May 13 19:54:40 GMT 2005"));
      expected.add(df.parse("Fri Jan 13 19:54:40 GMT 2006"));
      expected.add(df.parse("Fri Oct 13 19:54:40 GMT 2006"));
      expected.add(df.parse("Fri Apr 13 19:54:40 GMT 2007"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13;COUNT=30";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Sat Jul 13 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Aug 10 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Sep 07 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Oct 12 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Nov 09 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Dec 07 19:54:40 GMT 2002"));
      expected.add(df.parse("Sat Jan 11 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Feb 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Mar 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Apr 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat May 10 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Jun 07 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Jul 12 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Aug 09 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Sep 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Oct 11 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Nov 08 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Dec 13 19:54:40 GMT 2003"));
      expected.add(df.parse("Sat Jan 10 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Feb 07 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Mar 13 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Apr 10 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat May 08 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Jun 12 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Jul 10 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Aug 07 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Sep 11 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Oct 09 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Nov 13 19:54:40 GMT 2004"));
      expected.add(df.parse("Sat Dec 11 19:54:40 GMT 2004"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      rule = "FREQ=YEARLY;INTERVAL=4;BYMONTH=11;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;COUNT=30";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Tue Nov 05 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Nov 07 19:54:40 GMT 2006"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //      debug=true;                                               // MOVE THIS LINE AROUND TO FIND OUT MORE ABOUT THE TEST THAT IS RUN

      //=== These tests will be handled in a later release
      rule = "FREQ=MONTHLY;BYDAY=TU,WE,TH;BYSETPOS=1,-1";
      //      rule = "FREQ=MONTHLY;BYDAY=TU,WE,TH";
      System.err.println("----- ");
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Tue Nov 05 19:54:40 GMT 2002"));
      expected.add(df.parse("Tue Nov 07 19:54:40 GMT 2006"));
      print(rule, r);
      //      compareList(rule, r.getAllMatchingDates(),expected,r);
      debug = false; // MOVE THIS LINE AROUND TO FIND OUT MORE ABOUT THE TEST THAT IS RUN
      System.err.println("----- ");

      //rule = "FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-2";

      rule = "FREQ=WEEKLY;INTERVAL=2;COUNT=4;BYDAY=TU,SU;WKST=MO";
      rule = "FREQ=WEEKLY;INTERVAL=2;COUNT=4;BYDAY=TU,SU;WKST=SU";

      System.out.println("=============================================== HOUR FREQ");

      //------- Hourly freq tests

      end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
      end.set(Calendar.YEAR, 2002);
      end.set(Calendar.MONTH, Calendar.JUNE);
      end.set(Calendar.DATE, 27);
      end.set(Calendar.HOUR_OF_DAY, 19);
      end.set(Calendar.MINUTE, 54);
      end.set(Calendar.SECOND, 40); 

      System.out.println(" every hour on day 177 of they year");
      rule = "FREQ=HOURLY;COUNT=50;INTERVAL=1;BYYEARDAY=177";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Wed Jun 26 19:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 20:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 21:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 22:54:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 23:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      //      System.err.println(RecurranceRuleXml.convertRRuleToXml(rule));
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" every other hour at the quarter hour markers");
      rule = "FREQ=HOURLY;COUNT=20;INTERVAL=2;BYMINUTE=0,15,30,45;";
      r = new Recurrance(rule, start, end);
      expected.clear();

      expected.add(df.parse("Wed Jun 26 21:00:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 21:15:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 21:30:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 21:45:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 23:00:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 23:15:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 23:30:40 GMT 2002"));
      expected.add(df.parse("Wed Jun 26 23:45:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 01:00:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 01:15:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 01:30:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 01:45:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:00:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:15:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:30:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:45:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:00:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:15:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:30:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:45:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(" every quarter hour starting from timestamp, but only on even hours less than 10");
      rule = "FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 27 00:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);
      r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //--- OFFSET test 1
      System.out.println(
        "Five Minutes Before: every quarter hour starting from timestamp, but only on even hours less than 10");
      rule = "FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-5MIN";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 27 00:04:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:19:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:34:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 00:49:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:04:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:19:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:34:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 02:49:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:04:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:19:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:34:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 04:49:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:04:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:19:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:34:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 06:49:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:04:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:19:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:34:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 08:49:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);

      //--- Offset test 2
      System.out.println(
        "Three Hours After every quarter hour starting from timestamp, but only on even hours less than 10");
      rule = "FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=3HOUR";
      r = new Recurrance(rule, start, end);
      expected.clear(); // JJHNOTE STILL TESTING!  mod these results and test the DATE offset
      expected.add(df.parse("Thu Jun 27 03:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 03:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 05:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 07:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 07:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 07:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 07:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 09:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 09:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 09:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 09:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 11:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 11:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 11:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 27 11:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);

      System.out.println(
        "Four Days Before every quarter hour starting from timestamp, but only on even hours less than 10");
      rule = "FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-4DATE";
      r = new Recurrance(rule, start, end);
      expected.clear();
      expected.add(df.parse("Thu Jun 23 00:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 00:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 00:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 00:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 02:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 02:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 02:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 02:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 04:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 04:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 04:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 04:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 06:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 06:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 06:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 06:54:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 08:09:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 08:24:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 08:39:40 GMT 2002"));
      expected.add(df.parse("Thu Jun 23 08:54:40 GMT 2002"));
      compareList(rule, r.getAllMatchingDates(), expected, r);

      /*
            t = 1025121280000l;
            end = 31536000000l;
            end = end * 5;
            e = new Date( t + end );
      
            System.err.println("rule only constructor");
            rule = "FREQ=HOURLY;INTERVAL=1;BYMINUTE=0,15,30,45;";
            r = new Recurrance(rule);
            System.err.println("RULE "+rule);
            System.err.println("NEXT "+r.next(new Date()));
            r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule));
            System.err.println("RULE "+RecurranceRuleXml.convertRRuleToXml(rule));
            System.err.println("NEXT "+r.next(new Date()));
            System.err.println("rule and start only constructor");
            rule = "FREQ=HOURLY;INTERVAL=1;BYMINUTE=0,15,30,45;";
            r = new Recurrance(rule, start);
            System.err.println("RULE "+rule);
            System.err.println("NEXT "+r.next(new Date()));
            r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start);
            System.err.println("RULE "+RecurranceRuleXml.convertRRuleToXml(rule));
            System.err.println("NEXT "+r.next(new Date()));
      */

      debug = true; // MOVE THIS LINE AROUND TO FIND OUT MORE ABOUT THE TEST THAT IS RUN

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***
  Compare two lists and print outors
  ***/
  private static void compareList(String rule, List a, List b, Recurrance r) {
    boolean good = true;
    if (a.size() != b.size()) {
      System.out.println("   " + rule);
      System.out.println("   LIST SIZE MIS-MATCH :" + a.size() + " expected:" + b.size());
      good = false;
    }
    for (int ctr = 0; ctr < a.size(); ctr++) {
      boolean found = false;
      long va = ((Calendar)a.get(ctr)).getTime().getTime();
      for (int bctr = 0; bctr < b.size(); bctr++) {
        long vb = ((Date)b.get(bctr)).getTime();
        if (va == vb) {
          found = true;
        }
      }
      if (!found) {
        good = false;
      }
    }
    if (!good) {
      System.out.println("   ERROR: " + rule);
      print(rule, r);
      System.err.println("\n\n\n\n");

      debug = true;
      r = new Recurrance(rule, start, end);
      debug = false;

      throw new IllegalArgumentException("DEATH!");
      //return;
    }
    System.out.println("   OK: " + rule);
  }

  /***
  Compare two lists and print outors
  ***/
  private static void checkSize(String rule, List a, int size, Recurrance r) {
    if (a.size() != size) {
      System.out.println("   " + rule);
      System.out.println("   LIST SIZE MIS-MATCH :" + a.size() + " expected:" + size);
      print(rule, r);
      throw new IllegalArgumentException("DEATH!");
    } else {
      System.out.println("   OK: " + rule);
    }
  }

  /***
  Print stuff
  ***/
  private static void print(String rule, RecurranceRule r) {
    System.out.println(r.getRule());
    System.out.println(rule);

    List list = r.getAllMatchingDates();
    System.out.println("count = " + list.size());
    for (int ctr = 0; ctr < list.size(); ctr++) {
      System.out.println("" + ((Calendar)list.get(ctr)).getTime());
    }

  }

  public static long getLong() {
    return 27;
  }
  public static String getString() {
    return "asdf";
  }
  public static String getString2() {
    return null;
  }

  public static int foo(int a) {
    return a;
  }
  public static long foo(long a) {
    return a;
  }
  public static double foo(double a) {
    return a;
  }
  public static String foo(String a) {
    System.err.println("in foo(string)");
    return a;
  }
  public static Date foo(Date a) {
    return a;
  }
}
