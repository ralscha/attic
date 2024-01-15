//$Id: HotelSearchingAction.java,v 1.20 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;
import ch.ess.booking.entity.Hotel;

@SuppressWarnings("all")
@Name("hotelSearch")
@Scope(ScopeType.SESSION)
@Restrict("#{identity.loggedIn}")
public class HotelSearchingAction {

  @In
  private Session hibernateSession;

  private String searchString;
  private int pageSize = 10;
  private int page;

  @DataModel
  private List<Hotel> hotels;

  public void find() {
    page = 0;
    queryHotels();
  }

  public void nextPage() {
    page++;
    queryHotels();
  }

  public void prevPage() {
    page--;
    queryHotels();
  }

  private void queryHotels() {
    hotels = hibernateSession
        .createQuery(
            "select h from Hotel h where lower(h.name) like #{pattern} or lower(h.city) like #{pattern} or lower(h.zip) like #{pattern} or lower(h.address) like #{pattern}")
        .setMaxResults(pageSize).setFirstResult(page * pageSize).list();
  }

  public boolean isNextPageAvailable() {
    return hotels != null && hotels.size() == pageSize;
  }

  public List<Hotel> getHotels() {
    return this.hotels;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  @Factory(value = "pattern", scope = ScopeType.EVENT)
  public String getSearchPattern() {
    return searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
  }

  public String getSearchString() {
    return searchString;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }


}