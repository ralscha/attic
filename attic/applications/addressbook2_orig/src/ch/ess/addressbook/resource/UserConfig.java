package ch.ess.addressbook.resource;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.Type;
import ch.ess.addressbook.db.User;
import ch.ess.addressbook.db.UserConfiguration;
import ch.ess.addressbook.web.userconfig.TimeEnum;
import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public final class UserConfig extends Config {

  public static final String LOGIN_REMEMBER_NO = "login.remember.no";
  public static final String LOGIN_REMEMBER_UNIT = "login.remember.unit";
  public static final String LOGIN_REMEMBER_SECONDS = "login.remember.seconds";
  public static final String NO_ROWS = "no.rows";

  private Long userId;

  private UserConfig(User user) throws HibernateException {
    this.userId = user.getId();
    initialize();
  }

  public static UserConfig getUserConfig(User user) throws HibernateException {
    UserConfig config = new UserConfig(user);
    return config;
  }

  public void removeProperty(String key) throws HibernateException {

    HibernateSession.currentSession().delete(
      "from UserConfiguration as conf where conf.user = ? and conf.name = ?",
      new Object[] { userId, key },
      new Type[] { Hibernate.LONG, Hibernate.STRING });
    remove(key);
  }

  public void storeProperty(String key, String value) throws HibernateException {

    List result =
      HibernateSession.currentSession().find(
        "from UserConfiguration as conf where conf.user = ? and conf.name = ?",
        new Object[] { userId, key },
        new Type[] { Hibernate.LONG, Hibernate.STRING });

    if (!result.isEmpty()) {
      UserConfiguration oldConf = (UserConfiguration)result.get(0);
      oldConf.setPropValue(value);
    } else {

      User user = User.load(userId);

      UserConfiguration newConf = new UserConfiguration();
      newConf.setName(key);
      newConf.setPropValue(value);
      newConf.setUser(user);

      newConf.persist();
    }

    setProperty(key, value);
  }

  protected void load() throws HibernateException {

    List result =
      HibernateSession.currentSession().find(
        "from UserConfiguration as conf where conf.user = ?",
        userId,
        Hibernate.LONG);
    Iterator it = result.iterator();
    while (it.hasNext()) {
      UserConfiguration conf = (UserConfiguration)it.next();
      setProperty(conf.getName(), conf.getPropValue());
    }

    if (isEmpty()) {
      storeProperty(LOGIN_REMEMBER_NO, 1);
      storeProperty(LOGIN_REMEMBER_UNIT, TimeEnum.DAY.getName());
      storeProperty(LOGIN_REMEMBER_SECONDS, 86400);
    }

  }
}
