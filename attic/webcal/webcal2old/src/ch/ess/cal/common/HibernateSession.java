package ch.ess.cal.common;

import java.sql.*;

import javax.naming.*;

import net.sf.hibernate.*;

public class HibernateSession {

  public static final ThreadLocal session = new ThreadLocal();

  public static Session currentSession() throws NamingException, HibernateException {

    Session s = (Session)session.get();
    if (s == null) {
      SessionFactory sf = (SessionFactory)new InitialContext().lookup("SessionFactory");
      s = sf.openSession();
      session.set(s);
    }
    return s;
  }

  public static void closeSession() throws HibernateException, SQLException {

    Session s = (Session)session.get();
    session.set(null);
    if (s != null)
      s.close();
  }
}
