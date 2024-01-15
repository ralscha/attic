package ch.ess.cal.web.options;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Group;
import ch.ess.cal.web.event.EventForm;


@Option(id = "emailUserOptions")
public class EmailUserOptions extends AbstractOptions {

  private UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    User user = Util.getUser(request.getSession(), userDao);
    
    String[] userIds = null;
    
    EventForm eventForm = (EventForm)request.getSession().getAttribute("EventForm");
    if (eventForm != null) {
      userIds = eventForm.getUserIds();
    }

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

      if (userIds != null) {
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
    }

    for (String email : emails) {
      add(email, email);
    }

    sort();
  }

}
