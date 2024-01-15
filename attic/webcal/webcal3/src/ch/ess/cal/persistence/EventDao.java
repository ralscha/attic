package ch.ess.cal.persistence;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.math.IntRange;

import ch.ess.cal.model.Event;
import ch.ess.cal.model.User;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface EventDao extends Dao<Event> {

  //List find(String subject, String category);

  List<Event> getUserNormalEvents(User user, Calendar first, Calendar last);

  List<Event> getUserRecurEvents(User user, Calendar first, Calendar last);

  //List getTodayUserNormalEvents(User user, Calendar today);

  //List getTodayUserRecurEvents(User user, Calendar today);

  //List getUserRecurEvents(User user, long start, long end);

  //List getUserNormalEvents(User user, long start, long end);

  List getEventsWithReminders(long now);

  List<Event> findUserEvents(User user, String subject, String categoryId, String month, String year);

  IntRange getMinMaxYear();

  Object[] getEventAndReminder(Integer reminderId);
}
