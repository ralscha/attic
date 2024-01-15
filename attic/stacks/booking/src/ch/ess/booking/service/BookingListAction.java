//$Id: BookingListAction.java,v 1.23 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.service;

import static org.jboss.seam.ScopeType.SESSION;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import ch.ess.booking.entity.Booking;
import ch.ess.booking.entity.User;

@SuppressWarnings("all")
@Scope(SESSION)
@Name("bookingList")
@Restrict("#{identity.loggedIn}")
public class BookingListAction implements Serializable {

  private static final long serialVersionUID = 1L;

  @In
  private Session hibernateSession;

  @In
  private User user;

  @DataModel
  private List<Booking> bookings;
  @DataModelSelection
  private Booking booking;

  @Logger
  private Log log;

  @Factory
  @Observer("bookingConfirmed")
  public void getBookings() {
    bookings = hibernateSession.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate").setParameter("username",
        user.getUsername()).list();
  }

  public void cancel() {
    log.info("Cancel booking: #{bookingList.booking.id} for #{user.username}");
    Booking cancelled = (Booking)hibernateSession.get(Booking.class, booking.getId());
    if (cancelled != null)
      hibernateSession.delete(cancelled);
    getBookings();
    FacesMessages.instance().add("Booking cancelled for confirmation number #{bookingList.booking.id}");
  }

  public Booking getBooking() {
    return booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public List<Booking> getBookingList() {
    return bookings;
  }

}
