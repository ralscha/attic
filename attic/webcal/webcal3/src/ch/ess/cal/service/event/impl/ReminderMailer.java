package ch.ess.cal.service.event.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import ch.ess.cal.Util;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.TemplateMailSender;
import ch.ess.cal.service.impl.AppConfig;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.event.EventListDateObject;
import ch.ess.cal.web.event.EventListDateObjectFormatter;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/04/04 11:31:10 $ 
 * 
 * @spring.bean id="reminderMailer" 
 */
public class ReminderMailer {

  private Config appConfig;
  private EventDao eventDao;
  private MessageSource messageSource;
  private TranslationService translationService;
  private TemplateMailSender eventMailSender;

  /**
   * @spring.property reflocal="appConfig"
   */
  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }

  /**    
   * @spring.property reflocal="eventDao"
   */
  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  /**    
   * @spring.property ref="eventMailSender"
   */
  public void setEventMailSender(final TemplateMailSender eventMailSender) {
    this.eventMailSender = eventMailSender;
  }

  /**    
   * @spring.property ref="messageSource"
   */
  public void setMessageSource(final MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  public void send(Integer reminderId) {
    Object[] result = eventDao.getEventAndReminder(reminderId);
    if (result != null) {

      Event event = (Event)result[0];
      Reminder reminder = (Reminder)result[1];

      Locale locale = reminder.getLocale();
      TimeZone serverTimeZone = TimeZone.getDefault();

      Map<String, String> ctx = new HashMap<String, String>();
      ctx.put("subject", event.getSubject());
      ctx.put("location", event.getLocation());

      ctx.put("sensitivity", messageSource.getMessage(event.getSensitivity().getKey(), null, locale));
      ctx.put("importance", messageSource.getMessage(event.getImportance().getKey(), null, locale));

      EventCategory eventCategory = event.getEventCategories().iterator().next();
      ctx.put("category", translationService.getText(eventCategory.getCategory(), locale));

      Calendar startCal = Util.utcLong2UserCalendar(event.getStartDate(), serverTimeZone);
      Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), serverTimeZone);

      EventListDateObject start = new EventListDateObject(startCal, event.isAllDay());
      EventListDateObject end = new EventListDateObject(endCal, event.isAllDay());
      EventListDateObjectFormatter format = new EventListDateObjectFormatter();
      ctx.put("start", format.format(start));
      ctx.put("end", format.format(end));

      ctx.put("description", event.getDescription());

      try {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(appConfig.getStringProperty(AppConfig.MAIL_SENDER));
        mailMessage.setTo(reminder.getEmail());
        eventMailSender.send(locale, mailMessage, ctx);
      } catch (MailException e) {
        LogFactory.getLog(getClass()).error("send reminder mail", e);
      }

    }

  }
}
