package ch.ess.test.state;

public class Anfragen {
  
  private long id;
  private Integer status;

  public Anfragen(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }



}
