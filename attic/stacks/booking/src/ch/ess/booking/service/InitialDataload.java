package ch.ess.booking.service;

import java.math.BigDecimal;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.security.Identity;
import ch.ess.booking.entity.Hotel;
import ch.ess.booking.entity.User;

@Startup
@Scope(ScopeType.APPLICATION)
@Name("initialDataload")
public class InitialDataload {

  @In
  private Session hibernateSession;

  @Create
  @Transactional
  public void init() {

    Identity.setSecurityEnabled(false);

    Criteria criteria = hibernateSession.createCriteria(User.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      User user = new User();
      user.setUsername("gavin");
      user.setPassword("foobar");
      user.setName("Gavin King");
      hibernateSession.save(user);

      user = new User();
      user.setUsername("demo");
      user.setPassword("demo");
      user.setName("Demo User");
      hibernateSession.save(user);
    }

    criteria = hibernateSession.createCriteria(Hotel.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      hibernateSession.save(createHotel(120, "Marriott Courtyard", "Tower Place, Buckhead", "Atlanta", "GA", "30305", "USA"));

      hibernateSession.save(createHotel(180, "Doubletree", "Tower Place, Buckhead", "Atlanta", "GA", "30305", "USA"));
      hibernateSession.save(createHotel(450, "W Hotel", "Union Square, Manhattan", "NY", "NY", "10011", "USA"));
      hibernateSession.save(createHotel(450, "W Hotel", "Lexington Ave, Manhattan", "NY", "NY", "10011", "USA"));
      hibernateSession.save(createHotel(100, "Hotel Rouge", "1315 16th Street NW", "Washington", "DC", "20036", "USA"));
      hibernateSession.save(createHotel(120, "70 Park Avenue Hotel", "70 Park Avenue", "NY", "NY", "10011", "USA"));
      hibernateSession.save(createHotel(130, "Conrad Miami", "1395 Brickell Ave", "Miami", "FL", "33131", "USA"));
      hibernateSession.save(createHotel(160, "Sea Horse Inn", "2106 N Clairemont Ave", "Eau Claire", "WI", "54703", "USA"));
      hibernateSession.save(createHotel(180, "Super 8 Eau Claire Campus Area", "1151 W Macarthur Ave", "Eau Claire", "WI", "54701", "USA"));
      hibernateSession.save(createHotel(190, "Marriot Downtown", "55 Fourth Street", "San Francisco", "CA", "94103", "USA"));
      hibernateSession.save(createHotel(200, "Hilton Diagonal Mar", "Passeig del Taulat 262-264", "Barcelona", "Catalunya", "08019",
          "Spain"));
      hibernateSession.save(createHotel(210, "Hilton Tel Aviv", "Independence Park", "Tel Aviv", "", "63405", "Israel"));
      hibernateSession.save(createHotel(240, "InterContinental Tokyo Bay", "Takeshiba Pier", "Tokyo", "", "105", "Japan"));
      hibernateSession.save(createHotel(130, "Hotel Beaulac", " Esplanade Léopold-Robert 2", "Neuchatel", "", "2000", "Switzerland"));
      hibernateSession.save(createHotel(140, "Conrad Treasury Place", "William & George Streets", "Brisbane", "QLD", "4001", "Australia"));
      hibernateSession.save(createHotel(230, "Ritz Carlton", "1228 Sherbrooke St", "West Montreal", "Quebec", "H3G1H6", "Canada"));
      hibernateSession.save(createHotel(460, "Ritz Carlton", "Peachtree Rd, Buckhead", "Atlanta", "GA", "30326", "USA"));
      hibernateSession.save(createHotel(220, "Swissotel", "68 Market Street", "Sydney", "NSW", "2000", "Australia"));
      hibernateSession.save(createHotel(250, "Meliá White House", "Albany Street", "Regents Park London", "", "NW13UP", "Great Britain"));
      hibernateSession.save(createHotel(210, "Hotel Allegro", "171 West Randolph Street", "Chicago", "IL", "60601", "USA"));

    }

    Identity.setSecurityEnabled(true);

  }

  private Hotel createHotel(int price, String name, String address, String city, String state, String zip, String country) {
    Hotel hotel = new Hotel();
    hotel.setPrice(new BigDecimal(price));
    hotel.setName(name);
    hotel.setAddress(address);
    hotel.setCity(city);
    hotel.setState(state);
    hotel.setState(state);
    hotel.setZip(zip);
    hotel.setCountry(country);
    return hotel;
  }

}
