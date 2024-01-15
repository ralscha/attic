import java.util.UUID;


public class Test {

  public static void main(String[] args) {

    //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    
    //ClubService cs = (ClubService)context.getBean("clubService");
    System.out.println(UUID.randomUUID());
    
//    List<Club> clubs = cs.getAllClubs();
//    
//    for (Club club : clubs) {
//      System.out.println(club.getHgName());
//    }
    
    
//    Properties dbProps = new Properties();
//    try {
//      dbProps.load(Test.class.getResourceAsStream("/hibernate.properties"));
//      Configuration cfg = new Configuration();
//      cfg.setProperties(dbProps);
//      cfg.addClass(Club.class);
//      cfg.addClass(Art.class);
//      cfg.addClass(Spiel.class);
//      cfg.addClass(Spieler.class);
//      cfg.addClass(Punkte.class);
//      SessionFactory sf = cfg.buildSessionFactory();
//      Session session = sf.openSession();
//      Transaction tx = session.beginTransaction();
//
//      Club neuerClub = new Club();
//      neuerClub.setHgName("HG Dieboldshausen");
//      neuerClub.setBenutzername("hgd");
//      neuerClub.setPasswort("123");
//      neuerClub.setPraesident("Bigler Hansruedi");
//      neuerClub.setStrasse("Hutmatt");
//      neuerClub.setPlz("3068");
//      neuerClub.setOrt("Utzigen");
//      neuerClub.setAdmin(Boolean.FALSE);
//      session.save(neuerClub);
//
//      tx.commit();
//      session.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (MappingException e) {
//      e.printStackTrace();
//    } catch (HibernateException e) {
//      e.printStackTrace();
//    }
  }
}