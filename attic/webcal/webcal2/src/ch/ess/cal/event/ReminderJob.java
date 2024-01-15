package ch.ess.cal.event;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.cal.db.Category;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.ImportanceEnum;
import ch.ess.cal.db.Reminder;
import ch.ess.cal.db.SensitivityEnum;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.service.mail.MailException;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.TemplateMailSender;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;

public class ReminderJob implements Job {

  private static final Log LOG = LogFactory.getLog(ReminderJob.class);
  
  public void execute(JobExecutionContext context) {

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    MessageResources resources = (MessageResources)dataMap.get("resources");
    ServletContext servletContext = (ServletContext)dataMap.get("servletContext");
    
    Transaction tx = null;
    Session session = null;
    try {

      session = HibernateSession.getSession();
      tx = session.beginTransaction();
          
      Long reminderId = (Long)dataMap.get("id");
      
      Reminder reminder = Reminder.load(reminderId);
      Event event = reminder.getEvent();
      
      Map ctx = new HashMap();
      ctx.put("subject", event.getSubject());       
      ctx.put("location", event.getLocation());
      
      Locale locale = reminder.getLocale();
            
      ctx.put("category", ((Category)(event.getCategories().iterator().next())).getTranslations().get(locale));
      ctx.put("sensitivity", resources.getMessage(locale, SensitivityEnum.getKey(event.getSensitivity())));
      ctx.put("importance", resources.getMessage(locale, ImportanceEnum.getKey(event.getImportance())));
      
      ctx.put("description", event.getDescription());
          
      String pattern;
      if (event.isAllDay()) {
        pattern = "dd.MM.yyyy";
      } else {
        pattern = "dd.MM.yyyy HH:mm";
      }
      
      Calendar cal = Util.utcLong2UserCalendar(event.getStartDate(), event.getTimeZoneObj());
      ctx.put("start", Util.userCalendar2String(cal, pattern));
      
      cal = Util.utcLong2UserCalendar(event.getEndDate(), event.getTimeZoneObj());
      ctx.put("end", Util.userCalendar2String(cal, pattern));
            
      System.out.println("SEND MAIL " + reminder.getId());
      
      
      try {

        WebApplicationContext wactx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TemplateMailSender sender = (TemplateMailSender)wactx.getBean("ReminderMailSender");

        MailMessage mm = new MailMessage();
        mm.addTo(reminder.getEmail());
        sender.send(Constants.ENGLISH_LOCALE, mm, ctx);

      } catch (MailException e) {
        LOG.error("send logon warning", e);
      } catch (UnsupportedEncodingException e) {
        LOG.error("send logon warning", e);
      }
      
      
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (session != null) {
          session.close();
        }
      } catch (HibernateException e1) {
        e1.printStackTrace();
      }      
    }      
      
  }

}
