package ch.ess.lbw.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;

@Entity
public class LieferantWerk extends BaseObject {

  private Werk werk;
  private Lieferant lieferant;

  @ManyToOne
  @JoinColumn(name = "lieferantId", nullable = false)
  public Lieferant getLieferant() {
    return lieferant;
  }

  public void setLieferant(Lieferant lieferant) {
    this.lieferant = lieferant;
  }

  @ManyToOne
  @JoinColumn(name = "werkId", nullable = false)
  public Werk getWerk() {
    return werk;
  }

  public void setWerk(Werk werk) {
    this.werk = werk;
  }

}