package ch.ess.test.state;

public class Angebote {
  
  private long id;
  private long anfrageId;
  private Integer status;

  public Angebote(long id) {
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

  public long getAnfrageId() {
    return anfrageId;
  }

  public void setAnfrageId(long anfrageId) {
    this.anfrageId = anfrageId;
  }

}
