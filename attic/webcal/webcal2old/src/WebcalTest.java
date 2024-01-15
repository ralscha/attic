

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

public class WebcalTest {
  public static void main(String[] args) {
    try {
      
      Calendar cal = new GregorianCalendar(ch.ess.cal.Constants.UTC_TZ);
      cal.setTimeInMillis(1010250000000l);
      DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
      System.out.println(format.format(cal));
      
      
      
      
/*      
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
      
      Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("PST"));
      cal.setTime(new java.util.Date());
      
      System.out.println(dateFormat.format(cal.getTime()));
      
      ParsePosition pos = new ParsePosition(0);
      java.util.Date d = dateFormat.parse("2003.03.20 10:05:00 GMT+02:00", pos);
      
      System.out.println(d);
 */     
 /*
      HibernateManager.init();
      HolidayRegistry.init();
      Map result = HolidayRegistry.getMonthHolidays(Calendar.APRIL, 2003);
      for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
        Integer element = (Integer)iter.next();
        System.out.println(element);
        
        String h = (String)result.get(element);
        System.out.println(h);
        
      }
   */
/*
      TimeZone tz = TimeZone.getTimeZone("CET");
      Calendar cal = Util.convertToUTCCalendar("20020105T110000Z", tz);
            
            
      
  ;
      
      
      //erst bei get wird die stunde neu berechnet
      //System.out.println(cal.get(Calendar.HOUR));
      //System.out.println(cal.getTimeInMillis());
      
      SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
  */
          //df.setTimeZone(TimeZone.getTimeZone("CET"));            
      //System.out.println(df.format(cal2));
        
  /*    
      VEvent event = new VEvent();
      event.addFieldValue(VObject.FIELD_CATEGORIES, "MEETING");
      event.addFieldValue(VObject.FIELD_CATEGORIES, "BUSINESS");
      event.addFieldValue(VObject.FIELD_STATUS, "TENTATIVE");
      event.addFieldValue(VObject.FIELD_DTSTART, "19960401T033000Z");
      event.addFieldValue(VObject.FIELD_DTEND, "19960401T043000Z");
      event.addFieldValue(VObject.FIELD_SUMMARY, "Your Proposal Review");
      event.addFieldValue(VObject.FIELD_DESCRIPTION, "Steve and John to review newest proposal material");
      event.addFieldValue(VObject.FIELD_CLASS, "PRIVATE");
      
      
      
      VCalendar cal = new VCalendar();
      cal.addNewEvent(event);
      System.out.println(cal);
*/      
      /*      

      System.out.println(PasswordDigest.getDigestString("fredo"));

      System.exit(1);
           
      HibernateManager.init();
  
      Session sess = null;
      Transaction tx = null;
  
      try {

        
        

  
        sess = HibernateManager.open();
        
        tx = sess.beginTransaction(); 
        
        UserGroup group = new UserGroup();
        group.setName("name");
        sess.save(group);
        
               
        User newUser = new User();
        newUser.setUserGroup(group);
        newUser.setUserName("sr");
        newUser.setFirstName("first");
        newUser.setName("name");
        newUser.setLocale("deCH");
        newUser.setPasswordHash("HASH");
    
        Email newEmail = new Email();
        newEmail.setDefaultEmail(false);
        newEmail.setEmail("sr@ess.ch");
        newEmail.setUser(newUser);
        //newEmail.setSequence(1);
        
        List emails = new ArrayList();
        emails.add(newEmail);
        newUser.setEmails(emails);        
        sess.save(newUser);        
        tx.commit();
        sess.close();
*/
  /*
        sess = HibernateManager.open();
        tx = sess.beginTransaction();
        
        
        List userList = sess.find("from User as u");
        User user = (User)userList.get(0);
        List emails = user.getEmails();
        
        System.out.println("SIZE : " + emails.size());
        
        Iterator it = emails.iterator();
        while (it.hasNext()) {
          Email element = (Email)it.next();
          sess.delete(element);
        }
        
        user.setEmails(null);
        
        Email newEmail = new Email();
        newEmail.setDefaultEmail(false);
        newEmail.setEmail("srNEW@ess.ch");
        newEmail.setUser(user);
        newEmail.setSequence(1);    
        
        emails.put(new Integer(1), newEmail);
         
               
        tx.commit();
        sess.close();
        */
        
             
        /*
        Iterator it = userList.iterator();
        while (it.hasNext()) {
          User user = (User)it.next();
          Map emailsm = user.getEmails();
          Iterator it2 = emailsm.entrySet().iterator();
          while (it2.hasNext()) {
            Map.Entry element = (Map.Entry)it2.next();
            System.out.println(element.getKey());
            System.out.println(element.getValue());
          }
        }
        
        tx = sess.beginTransaction(); 
        tx.commit();
        sess.close();        
        */
        /*
        
        sess = HibernateManager.open();      
        tx = sess.beginTransaction();                        
        Appointment newAppointment = new Appointment();
        newAppointment.setStartDate(new Date());        
        Set users = new HashSet();
        users.add(newUser);
        newAppointment.setUsers(users);        
        sess.save(newAppointment);        
        tx.commit();
        sess.close();
        
        sess = HibernateManager.open();                
        tx = sess.beginTransaction();                    
        newEmail = new Email();
        newEmail.setDefaultEmail(false);        
        newEmail.setEmail("sr2@ess.ch");
        newEmail.setUser(newUser);        
        newUser.getEmails().put(new Integer(2), newEmail);
        sess.save(newEmail);        
        tx.commit();
        sess.close();
        
System.out.println("save new appointment");
        sess = HibernateManager.open();                
        tx = sess.beginTransaction();                    
        List userList = sess.find("from User as u");
        User u = (User)userList.get(0);
        System.out.println("END SELECT");
        newAppointment = new Appointment();
        newAppointment.setStartDate(new Date());
        Set usersa = new HashSet();
        usersa.add(u);        
        newAppointment.setUsers(usersa);
                
        //u.getAppointments().add(newAppointment);
        System.out.println("END GET APPOINTMENTS");
        sess.save(newAppointment);        
        tx.commit();
        sess.close();
System.out.println("END save new appointment");        
        sess = HibernateManager.open();        
        tx = sess.beginTransaction();
        userList = sess.find("from User as u");
        Set s = ((User)userList.get(0)).getAppointments();
        System.out.println(userList.size());
        System.out.println(s.size());
        
        tx.commit();
        
        sess.close();
        sess = HibernateManager.open();        
        tx = sess.beginTransaction();
        List appList = sess.find("from Appointment as app");
        s = ((Appointment)appList.get(0)).getUsers();
        System.out.println(appList.size());
        System.out.println(s.size());
        
        tx.commit();
        */
           /*                     
      } catch (HibernateException e) {
        
        if (tx != null) {
          tx.rollback();
        }
  
        throw e;   
      } finally {
        if (sess != null) {
          sess.close();
        }
      }*/
      
    } catch (Exception e) {
      e.printStackTrace();    
    }
  }
}
