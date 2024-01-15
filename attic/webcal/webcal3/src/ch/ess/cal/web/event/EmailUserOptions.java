package ch.ess.cal.web.event;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.Util;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @spring.bean name="emailUserOptions" singleton="false" 
 */
public class EmailUserOptions extends AbstractOptions {

  private UserDao userDao;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    User user = Util.getUser(request.getSession(), userDao);
    EventForm eventForm = (EventForm)request.getSession().getAttribute("eventForm");
    String[] userIds = eventForm.getUserIds();

    Set<String> emails = new TreeSet<String>();

    Set<Email> userEmails = user.getEmails();
    for (Email email : userEmails) {
      emails.add(email.getEmail());
    }

    Set<Group> userGroups = user.getGroups();
    for (Group group : userGroups) {
      Set<Email> groupEmails = group.getEmails();
      for (Email email : groupEmails) {
        emails.add(email.getEmail());
      }

      Set<User> users = group.getUsers();
      for (User auser : users) {
        if (!auser.getId().equals(user.getId())) {
          for (String userid : userIds) {
            if (auser.getId().toString().equals(userid)) {
              for (Email email : auser.getEmails()) {
                emails.add(email.getEmail());
              }
              break;
            }
          }
        }
      }
    }

    for (String email : emails) {
      add(email, email);
    }

    sort();
  }

}
