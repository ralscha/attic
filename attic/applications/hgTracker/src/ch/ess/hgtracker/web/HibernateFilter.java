package ch.ess.hgtracker.web;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spiel;
import ch.ess.hgtracker.db.Spieler;

public class HibernateFilter implements Filter {

  public static SessionFactory sf;

  // starten des applikationsservers (tomcat)
  public void init(FilterConfig arg0) throws ServletException {
    Properties dbProps = new Properties();
    try {
      dbProps.load(getClass().getResourceAsStream("/hibernate.properties"));
      Configuration cfg = new Configuration();
      cfg.setProperties(dbProps);
      cfg.addClass(Club.class);
      cfg.addClass(Art.class);
      cfg.addClass(Spiel.class);
      cfg.addClass(Spieler.class);
      cfg.addClass(Punkte.class);
      sf = cfg.buildSessionFactory();
    } catch (MappingException e) {
      e.printStackTrace();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // wird bei jedem request aufgerufen, es wird eine hibernate session kreiert
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    try {
      Session session = sf.openSession();
      request.setAttribute("dbSession", session);
      chain.doFilter(request, response);
      session.close();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  // wird beim beenden des applikationsservers (tomcat) aufgerufen
  public void destroy() {
    try {
      sf.close();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

}
