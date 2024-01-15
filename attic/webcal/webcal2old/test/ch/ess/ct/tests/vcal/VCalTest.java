package ch.ess.ct.tests.vcal;

import java.util.Iterator;
import java.util.List;

import com.managestar.recurrance.Recurrance;

import junit.framework.TestCase;
import ch.ess.cal.vcal.Field;
import ch.ess.cal.vcal.VCalendar;
import ch.ess.cal.vcal.VCalendarUtil;
import ch.ess.cal.vcal.VEvent;
import ch.ess.cal.vcal.VTodo;
import ch.ess.cal.vcal.parser.ParseException;
import ch.ess.cal.vcal.ruleparser.TokenMgrError;
import ch.ess.cal.vcal.ruleparser.VCalRuleParser;

public class VCalTest extends TestCase {

  public VCalTest(String arg0) {
    super(arg0);
  }
  
  public void testEvent() {
    
    try {
      String s = "BEGIN:VCALENDAR\r\n"+
                 "VERSION:1.0\r\n"+
                 "END:VCALENDAR\r\n";  
      
      VCalendar vcal = VCalendarUtil.parseVCalendar(s);
      assertTrue(vcal.getAllEvents().isEmpty());
      assertTrue(vcal.getAllTodo().isEmpty());
      
           
      s = "BEGIN:VCALENDAR\r\n"+
          "VERSION:1.0\r\n"+
          "BEGIN:VEVENT\r\n"+
          "CATEGORIES:MEETING\r\n"+
          "STATUS:TENTATIVE\r\n"+
          "DTSTART:19960401T033000Z\r\n"+
          "DTEND:19960401T043000Z\r\n"+
          "SUMMARY:Your Proposal Review\r\n"+
          "DESCRIPTION:Steve and John to review newest proposal material\r\n"+
          "CLASS:PRIVATE\r\n"+
          "END:VEVENT\r\n"+
          "END:VCALENDAR\r\n";      

      vcal = VCalendarUtil.parseVCalendar(s);
      assertTrue(vcal.getAllTodo().isEmpty());
      
      List events = vcal.getAllEvents();
      assertEquals(1, events.size());
      VEvent event = (VEvent)events.get(0);
      
      Iterator it = event.getFieldValueList("CATEGORIES");
      assertTrue(it.hasNext());
      
      Field f = (Field)it.next();
      assertEquals(f.getFieldValue(), "MEETING");
      
      
      f = event.getField("STATUS");
      assertNotNull(f);      
      assertEquals(f.getFieldValue(), "TENTATIVE");
      
      f = event.getField("DTSTART");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T033000Z");
      
      f = event.getField("DTEND");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T043000Z");
      
      f = event.getField("SUMMARY");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "Your Proposal Review");
      
      f = event.getField("DESCRIPTION");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "Steve and John to review newest proposal material");
      
      f = event.getField("CLASS");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "PRIVATE");
      
      

      s = "BEGIN:VCALENDAR\r\n"+
      "VERSION:1.0\r\n"+
      "BEGIN:VTODO\r\n"+
      "SUMMARY:John to pay for lunch\r\n"+
      "DUE:19960401T083000Z\r\n"+
      "STATUS:NEEDS ACTION\r\n"+
      "END:VTODO\r\n"+
      "END:VCALENDAR\r\n";
      
      vcal = VCalendarUtil.parseVCalendar(s);
      assertTrue(vcal.getAllEvents().isEmpty());
      
      List todo = vcal.getAllTodo();
      assertEquals(1, todo.size());
      VTodo vtodo = (VTodo)todo.get(0);
      
      f = vtodo.getField("SUMMARY");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "John to pay for lunch");      
      
      f = vtodo.getField("DUE");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T083000Z");      
      
      f = vtodo.getField("STATUS");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "NEEDS ACTION");      
      
      

      s = "BEGIN:VCALENDAR\r\n"+
      "VERSION:1.0\r\n"+
      "BEGIN:VEVENT\r\n"+
      "CATEGORIES:MEETING\r\n"+
      "STATUS:NEEDS ACTION\r\n"+
      "DTSTART:19960401T073000Z\r\n"+
      "DTEND:19960401T083000Z\r\n"+
      "SUMMARY:Steve39s Proposal Review\r\n"+
      "DESCRIPTION:Steve and John to review newest proposal material\r\n"+
      "CLASS:PRIVATE\r\n"+
      "END:VEVENT\r\n"+
      "BEGIN:VTODO\r\n"+
      "SUMMARY:John to pay for lunch\r\n"+
      "DUE:19960401T083000Z\r\n"+
      "STATUS:NEEDS ACTION\r\n"+
      "END:VTODO\r\n"+
      "END:VCALENDAR\r\n";
      

      vcal = VCalendarUtil.parseVCalendar(s);
      assertFalse(vcal.getAllEvents().isEmpty());
      assertFalse(vcal.getAllTodo().isEmpty());

      events = vcal.getAllEvents();
      assertEquals(1, events.size());
      event = (VEvent)events.get(0);
            
      it = event.getFieldValueList("CATEGORIES");
      assertTrue(it.hasNext());
      
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "MEETING");
      
      
      f = event.getField("STATUS");
      assertNotNull(f);      
      assertEquals(f.getFieldValue(), "NEEDS ACTION");
      
      f = event.getField("DTSTART");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T073000Z");
      
      f = event.getField("DTEND");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T083000Z");
      
      f = event.getField("SUMMARY");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "Steve39s Proposal Review");
      
      f = event.getField("DESCRIPTION");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "Steve and John to review newest proposal material");
      
      f = event.getField("CLASS");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "PRIVATE");

      
      todo = vcal.getAllTodo();
      assertEquals(1, todo.size());
      vtodo = (VTodo)todo.get(0);
      
      f = vtodo.getField("SUMMARY");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "John to pay for lunch");      
      
      f = vtodo.getField("DUE");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "19960401T083000Z");      
      
      f = vtodo.getField("STATUS");
      assertNotNull(f);
      assertEquals(f.getFieldValue(), "NEEDS ACTION");      
      
      
      s = "BEGIN:VCALENDAR\r\n"+
          "VERSION:1.0\r\n"+
          "BEGIN:VEVENT\r\n"+
          "X-OSKI:1234\r\n"+
          "X-JASON:HONG\r\n"+
          "CATEGORIES:APPOINTMENT;BUSINESS;\r\n"+
          "ATTACH;VALUE=CONTENT-ID:<jsmith.part3.960817T083000.xyzMail@host1.com>\r\n"+
          "ATTACH;VALUE=URL:xyzCorp.com/pub/reports/r-960812\r\n"+
          "DESCRIPTION:This is my description.\r\n"+
          "TRANSP:1\r\n"+
          "UID:19960401-080045-4000F192713-0052\r\n"+
          "END:VEVENT\r\n"+
          "END:VCALENDAR\r\n";
          
      vcal = VCalendarUtil.parseVCalendar(s);
      assertTrue(vcal.getAllTodo().isEmpty());
      
      events = vcal.getAllEvents();
      assertEquals(1, events.size());
      event = (VEvent)events.get(0);
      
      it = event.getFieldValueList("CATEGORIES");
      assertTrue(it.hasNext());
      
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "APPOINTMENT");
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "BUSINESS");
      
      
      f = event.getField("X-OSKI");
      assertEquals(f.getFieldValue(), "1234");
          
      f = event.getField("X-JASON");
      assertEquals(f.getFieldValue(), "HONG");

      f = event.getField("DESCRIPTION");
      assertEquals(f.getFieldValue(), "This is my description.");
      
      f = event.getField("TRANSP");
      assertEquals(f.getFieldValue(), "1");

      f = event.getField("UID");
      assertEquals(f.getFieldValue(), "19960401-080045-4000F192713-0052");
      
      it = event.getFieldValueList("ATTACH");
       
      f = (Field)it.next();
      assertEquals(f.toString(), "ATTACH;VALUE=CONTENT-ID:<jsmith.part3.960817T083000.xyzMail@host1.com>");

      f = (Field)it.next();
      assertEquals(f.toString(), "ATTACH;VALUE=URL:xyzCorp.com/pub/reports/r-960812");
      
      
      s = "BEGIN:VCALENDAR\r\n"+
      "VERSION:1.0\r\n"+
      "BEGIN:VEVENT\r\n"+
      "CATEGORIES:APPOINTMENT;BUSINESS;\r\n"+
      "CLASS:PUBLIC\r\n"+
      "DCREATED:19981030T202346\r\n"+
      "COMPLETED:19981030T202346\r\n"+
      "DESCRIPTION:This is my description.\r\n"+
      "DUE:19981030T202346\r\n"+
      "DTEND:19981030T202346\r\n"+
      "EXDATE:19981030T202346;19981030T202346;\r\n"+
      "LAST-MODIFIED:19981030T202346\r\n"+
      "LOCATION:306 Soda\r\n"+
      "RNUM:36\r\n"+
      "PRIORITY:1\r\n"+
      "RELATED-TO:<jsmith.part7.19960817T083000.xyzMail@host3.com>\r\n"+
      "RDATE:19981030T202346;19960302T010000;19960303T010000;\r\n"+
      "RRULE:W2 TU TH\r\n"+
      "RESOURCES:VCR;EASEL;CATERING;CHAIRS\r\n"+
      "SEQUENCE:5\r\n"+
      "DTSTART:19981030T202346\r\n"+
      "STATUS:TENTATIVE\r\n"+
      "SUMMARY:This is a really short summary\r\n"+
      "TRANSP:1\r\n"+
      "UID:19960401-080045-4000F192713-0052\r\n"+
      "END:VEVENT\r\n"+
      "END:VCALENDAR\r\n";
      
      
      vcal = VCalendarUtil.parseVCalendar(s);
      assertTrue(vcal.getAllTodo().isEmpty());
      
      events = vcal.getAllEvents();
      assertEquals(1, events.size());
      event = (VEvent)events.get(0);
      
      it = event.getFieldValueList("CATEGORIES");
      assertTrue(it.hasNext());
      
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "APPOINTMENT");
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "BUSINESS");

      f = event.getField("CLASS");
      assertEquals(f.getFieldValue(), "PUBLIC");

      f = event.getField("DCREATED");
      assertEquals(f.getFieldValue(), "19981030T202346");
      
      f = event.getField("COMPLETED");
      assertEquals(f.getFieldValue(), "19981030T202346");
      
      f = event.getField("DESCRIPTION");
      assertEquals(f.getFieldValue(), "This is my description.");

      f = event.getField("DUE");
      assertEquals(f.getFieldValue(), "19981030T202346");

      f = event.getField("DTEND");
      assertEquals(f.getFieldValue(), "19981030T202346");

      it = event.getFieldValueList("EXDATE");
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "19981030T202346");
      assertEquals(f.getFieldValue(), "19981030T202346");
      

      f = event.getField("LAST-MODIFIED");
      assertEquals(f.getFieldValue(), "19981030T202346");
      
      f = event.getField("LOCATION");
      assertEquals(f.getFieldValue(), "306 Soda");


      f = event.getField("RNUM");
      assertEquals(f.getFieldValue(), "36");


      f = event.getField("PRIORITY");
      assertEquals(f.getFieldValue(), "1");

      f = event.getField("SUMMARY");
      assertEquals(f.getFieldValue(), "This is a really short summary");

      f = event.getField("TRANSP");
      assertEquals(f.getFieldValue(), "1");


      f = event.getField("UID");
      assertEquals(f.getFieldValue(), "19960401-080045-4000F192713-0052");

      f = event.getField("STATUS");
      assertEquals(f.getFieldValue(), "TENTATIVE");

      f = event.getField("DTSTART");
      assertEquals(f.getFieldValue(), "19981030T202346");

      f = event.getField("SEQUENCE");
      assertEquals(f.getFieldValue(), "5");

      f = event.getField("RRULE");
      assertEquals(f.getFieldValue(), "W2 TU TH");


      it = event.getFieldValueList("RDATE");
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "19981030T202346");
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "19960302T010000");
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "19960303T010000");


      f = event.getField("RELATED-TO");
      assertEquals(f.getFieldValue(), "<jsmith.part7.19960817T083000.xyzMail@host3.com>");

      it = event.getFieldValueList("RESOURCES");
      f = (Field)it.next();      
      assertEquals(f.getFieldValue(), "VCR");
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "EASEL");
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "CATERING");
      f = (Field)it.next();
      assertEquals(f.getFieldValue(), "CHAIRS");


      try {
        String rule = VCalRuleParser.getICalRule("D1 #10");
        Recurrance r = new Recurrance(rule); 
        
        rule = VCalRuleParser.getICalRule("D2 #0"); 
        r = new Recurrance(rule); 
        
        rule = VCalRuleParser.getICalRule("D10 #5"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("W1 #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("W2 #0"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("W1 TU TH #5"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("W2 MO WE FR"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MP1 1+ FR #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MP1 1+ FR"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MP2 1+ SU 1- SU #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MP6 2+ MO TU WE TH FR #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MP1 2- MO #6"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD1 3- #0"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD1 2 15 #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD1 1 LD #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD1 1 1- #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD18 10 11 12 13 14 15 #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("MD1 2- #5"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("YM1 6 7 #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("YM2 1 2 3 #10"); 
        r = new Recurrance(rule); 

        rule = VCalRuleParser.getICalRule("YD3 1 100 200 #10"); 
        r = new Recurrance(rule); 

        r.getAllMatchingDates();
      } catch (ch.ess.cal.vcal.ruleparser.ParseException e) {        
        e.printStackTrace();
        fail(e.toString());
      } catch (TokenMgrError e) {        
        e.printStackTrace();
        fail(e.toString());
      }

      
      
    } catch (ParseException pe) {
      fail(pe.toString());
    }
  }

}
