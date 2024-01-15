package ch.ess.hgtracker.db;

import java.io.IOException;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {

  public static void main(String[] args) {
    Properties dbProps = new Properties();
    try {
      dbProps.load(Test.class.getResourceAsStream("/hibernate.properties"));
      Configuration cfg = new Configuration();
      cfg.setProperties(dbProps);
      cfg.addClass(Club.class);
      cfg.addClass(Art.class);
      cfg.addClass(Spiel.class);
      cfg.addClass(Spieler.class);
      cfg.addClass(Punkte.class);
      SessionFactory sf = cfg.buildSessionFactory();
      Session session = sf.openSession();
      Transaction tx = session.beginTransaction();

      Club neuerClub = new Club();
      neuerClub.setHgName("HG Dieboldshausen");
      neuerClub.setBenutzername("hgd");
      neuerClub.setPasswort("123");
      neuerClub.setPraesident("Bigler Hansruedi");
      neuerClub.setStrasse("Hutmatt");
      neuerClub.setPlz("3068");
      neuerClub.setOrt("Utzigen");
      neuerClub.setAdmin(Boolean.FALSE);
      session.save(neuerClub);

      tx.commit();
      session.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (MappingException e) {
      e.printStackTrace();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }
}