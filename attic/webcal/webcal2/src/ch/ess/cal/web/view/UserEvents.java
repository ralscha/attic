package ch.ess.cal.web.view;


public class UserEvents {
  private String name;
  private Long id;
  private boolean[] hasEvents;

  public String getName() {
    return name;
  }


  public void setName(String string) {
    name = string;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long long1) {
    id = long1;
  }

  public boolean[] getHasEvents() {
    return hasEvents;
  }

  public void setHasEvents(boolean[] bs) {
    hasEvents = bs;
  }

}
