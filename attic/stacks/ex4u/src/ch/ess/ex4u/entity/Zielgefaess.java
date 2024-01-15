package ch.ess.ex4u.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ch.ess.ex4u.shared.ZielgefaessRPC;
import ch.ess.sgwt.annotations.Title;

@Entity
public class Zielgefaess extends AbstractEntity {

  public Zielgefaess() {
    super();
  }

  public Zielgefaess(ZielgefaessRPC z) {
    super();
    setId(z.getId());
    setBeschreibung(z.getBeschreibung());
    setNummer(z.getNummer());
  }
  
  public ZielgefaessRPC getZielgefaessRPC() {
    ZielgefaessRPC g = new ZielgefaessRPC();
    g.setId(getId());
    g.setBeschreibung(getBeschreibung());
    g.setNummer(getNummer());
    
    for (Vertrag v : getVertraege()) g.addVertragId(v.getId());
    for (Zeit z : getZeiten()) g.addZeitId(z.getId());
    return g;
  }

  @Length(max = 50)
  @Title("Nummer")
  private String nummer;

  @NotNull
  @Title("Beschreibung")
  private String beschreibung;

  @ManyToMany
  @JoinTable(name = "VertragZielgefaess", joinColumns = @JoinColumn(name = "zielgefaessId"), inverseJoinColumns = @JoinColumn(name = "vertragId"))
  private Set<Vertrag> vertraege;

  @OneToMany(mappedBy = "zielgefaess", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Zeit> zeiten = new HashSet<Zeit>();

  public String getNummer() {
    return nummer;
  }

  public void setNummer(String nummer) {
    this.nummer = nummer;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String name) {
    this.beschreibung = name;
  }

  public Set<Zeit> getZeiten() {
    return zeiten;
  }

  public void setZeiten(Set<Zeit> zeiten) {
    this.zeiten = zeiten;
  }

  public void addZeit(Zeit zeit) {
    //zeit.setZielgefaess(this);
    this.zeiten.add(zeit);
  }

  public Set<Vertrag> getVertraege() {
    return vertraege;
  }

  public void setVertraege(Set<Vertrag> vertraege) {
    this.vertraege = vertraege;
  }

  public void addVertrag(Vertrag vertrag) {
    if (this.vertraege == null)
      this.vertraege = new HashSet<Vertrag>();
    this.vertraege.add(vertrag);
  }

}