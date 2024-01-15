package ch.ess.cal.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.TemplateMailSender;
import ch.ess.base.service.TranslationService;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.BaseEvent;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.Task;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "reminderMailer", autowire = Autowire.BYNAME)
public class ReminderMailer {

  private Config appConfig;
  private EventDao eventDao;
  private MessageSource messageSource;
  private TranslationService translationService;
  private TemplateMailSender eventMailSender;
  private TemplateMailSender taskMailSender;

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setEventMailSender(final TemplateMailSender eventMailSender) {
    this.eventMailSender = eventMailSender;
  }

  public void setTaskMailSender(TemplateMailSender taskMailSender) {
    this.taskMailSender = taskMailSender;
  }

  public void setMessageSource(final MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  @Transactional
  public void send(Integer reminderId) {
    Object[] result = eventDao.getEventAndReminder(reminderId);
    if (result != null) {

      BaseEvent event = (BaseEvent)result[0];
      Reminder reminder = (Reminder)result[1];

      Locale locale = reminder.getLocale();
      TimeZone serverTimeZone = TimeZone.getDefault();

      Map<String, String> ctx = new HashMap<String, String>();
      
      ctx.put("id", event.getId().toString());
      ctx.put("subject", event.getSubject());
      
      if (event.getLocation() != null) {
        ctx.put("location", event.getLocation());
      } else {
        ctx.put("location", "");
      }

      if (event.getSensitivity() != null) {
        ctx.put("sensitivity", messageSource.getMessage(event.getSensitivity().getKey(), null, locale));
      } else {
        ctx.put("sensitivity", "");
      }
      
      if (event.getImportance() != null) {
        ctx.put("importance", messageSource.getMessage(event.getImportance().getKey(), null, locale));
      } else {
        ctx.put("importance", "");
      }

      if (!event.getEventCategories().isEmpty()) {
        EventCategory eventCategory = event.getEventCategories().iterator().next();
        ctx.put("category", translationService.getText(eventCategory.getCategory(), locale));
      } else {
        ctx.put("category", "");
      }
      
      EventListDateObjectConverter format = new EventListDateObjectConverter();
      if (event.getStartDate() != null) {
        Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), serverTimeZone);
        EventListDateObject start = new EventListDateObject(startCal, event.isAllDay());
        ctx.put("start", format.getAsString(null, start));
      } else {
        ctx.put("start", "");
      }
      
      if (event.getEndDate() != null) {
        Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), serverTimeZone);
        EventListDateObject end = new EventListDateObject(endCal, event.isAllDay());      
        ctx.put("end", format.getAsString(null, end));
      } else {
        ctx.put("end", "");
      }
      
      if (event instanceof Task) {
        Task task = (Task)event;
        if (task.getDueDate() != null) {
          Calendar dueCal = CalUtil.utcLong2UserCalendar(task.getDueDate(), serverTimeZone);
          EventListDateObject due = new EventListDateObject(dueCal, task.getAllDayDue());      
          ctx.put("due", format.getAsString(null, due));          
        } else {
          ctx.put("due", "");
        }
      }
      
      if (event.getDescription() != null) {
        ctx.put("description", event.getDescription());
      } else {
        ctx.put("description", "");
      }

      try {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));
        mailMessage.setTo(reminder.getEmail());
        
        if (event instanceof Event) {
          eventMailSender.send(locale, mailMessage, ctx);
        } else {
          taskMailSender.send(locale, mailMessage, ctx);
        }
      } catch (MailException e) {
        LogFactory.getLog(getClass()).error("send reminder mail", e);
      }

    }

  }
}
