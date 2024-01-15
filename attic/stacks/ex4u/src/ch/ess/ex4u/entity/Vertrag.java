package ch.ess.ex4u.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ch.ess.ex4u.shared.VertragRPC;

@Entity
public class Vertrag extends AbstractEntity {

  public Vertrag() {
    super();
  }

  public Vertrag(VertragRPC v) {
    super();
    setId(v.getId());
    setVertragsnummer(v.getVertragsnummer());
    setVertragsname(v.getVertragsname());
    setVon(v.getVon());
    setBis(v.getBis());
  }
  
  public VertragRPC getVertragRPC() {
    VertragRPC v = new VertragRPC();
    v.setId(getId());
    v.setVertragsnummer(getVertragsnummer());
    v.setVertragsname(getVertragsname());
    v.setVon(getVon());
    v.setBis(getBis());

    for (User u : getEmas()) v.addUserId(u.getId());
    for (Zielgefaess g : getZielgefaesse()) v.addZielgefaessId(g.getId());
    for (Zeit z : getZeiten()) v.addZeitId(z.getId());
    
    return v;
  }
  
  @NotNull
  @Length(max = 50)
  private String vertragsnummer;

  @Length(max = 100)
  private String vertragsname;

  @NotNull
  private Date von;

  @NotNull
  private Date bis;

  @ManyToMany
  @JoinTable(name = "VertragEma", joinColumns = @JoinColumn(name = "vertragId"), inverseJoinColumns = @JoinColumn(name = "userId"))
  private Set<User> emas;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "vertraege")
  private Set<Zielgefaess> zielgefaesse;

  @OneToMany(mappedBy = "vertrag", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Zeit> zeiten = new HashSet<Zeit>();

  public String getVertragsnummer() {
    return vertragsnummer;
  }

  public void setVertragsnummer(String vertragsnummer) {
    this.vertragsnummer = vertragsnummer;
  }

  public String getVertragsname() {
    return vertragsname;
  }

  public void setVertragsname(String vertragsname) {
    this.vertragsname = vertragsname;
  }

  public Date getVon() {
    return von;
  }

  public void setVon(Date von) {
    this.von = von;
  }

  public Date getBis() {
    return bis;
  }

  public void setBis(Date bis) {
    this.bis = bis;
  }

  public Set<User> getEmas() {
    return emas;
  }

  public void setEmas(Set<User> emas) {
    this.emas = emas;
  }

  public void addEma(User ema) {
    if (this.emas == null)
      this.emas = new HashSet<User>();
    this.emas.add(ema);
  }

  public Set<Zeit> getZeiten() {
    return zeiten;
  }

  public void setZeiten(Set<Zeit> zeiten) {
    this.zeiten = zeiten;
  }

  public void addZeit(Zeit zeit) {
    zeit.setVertrag(this);
    this.zeiten.add(zeit);
  }

  public Set<Zielgefaess> getZielgefaesse() {
    return zielgefaesse;
  }

  public void setZielgefaesse(Set<Zielgefaess> zielgefaesse) {
    this.zielgefaesse = zielgefaesse;
  }

  public void addZielgefaess(Zielgefaess zielgefaess) {
    if (this.zielgefaesse == null)
      this.zielgefaesse = new HashSet<Zielgefaess>();
    this.zielgefaesse.add(zielgefaess);
  }
}
