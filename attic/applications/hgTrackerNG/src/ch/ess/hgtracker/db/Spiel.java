package ch.ess.hgtracker.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="spiel")
public class Spiel implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private Date datum;
  private String ort;
  private String gegner;
  private String kampfrichter;
  private String kampfrichterGegner;
  private Integer totalNr;
  private Integer schlagPunkteGegner;
  private Integer anzahlRies;
  
  @Transient
  private String wochentag;

  @Transient
  private String artName;

  @Transient
  private boolean hasAufstellung;

  @ManyToOne
  @JoinColumn(name = "clubId", nullable = false)
  private Club club;

  @ManyToOne
  @JoinColumn(name = "artId", nullable = false)
  private Art art;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "spiel")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Punkte> punkte = new HashSet<Punkte>();
  
  
  private Integer totalNrGegner;
  private Integer schlagPunkte;

  public Integer getSchlagPunkte() {
    return schlagPunkte;
  }

  public void setSchlagPunkte(Integer schlagPunkte) {
    this.schlagPunkte = schlagPunkte;
  }

  public Integer getTotalNrGegner() {
    return totalNrGegner;
  }

  public void setTotalNrGegner(Integer totalNrGegner) {
    this.totalNrGegner = totalNrGegner;
  }

  public Set<Punkte> getPunkte() {
    return punkte;
  }

  public void setPunkte(Set<Punkte> punkte) {
    this.punkte = punkte;
  }

  public Art getArt() {
    return art;
  }

  public void setArt(Art art) {
    this.art = art;
  }

  public Club getClub() {
    return club;
  }

  public void setClub(Club club) {
    this.club = club;
  }

  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public String getGegner() {
    return gegner;
  }

  public void setGegner(String gegner) {
    this.gegner = gegner;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getKampfrichter() {
    return kampfrichter;
  }

  public void setKampfrichter(String kampfrichter) {
    this.kampfrichter = kampfrichter;
  }

  public String getKampfrichterGegner() {
    return kampfrichterGegner;
  }

  public void setKampfrichterGegner(String kampfrichterGegner) {
    this.kampfrichterGegner = kampfrichterGegner;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public Integer getSchlagPunkteGegner() {
    return schlagPunkteGegner;
  }

  public void setSchlagPunkteGegner(Integer schlagPunkteGegner) {
    this.schlagPunkteGegner = schlagPunkteGegner;
  }

  public Integer getTotalNr() {
    return totalNr;
  }

  public void setTotalNr(Integer totalNr) {
    this.totalNr = totalNr;
  }

  public String getWochentag() {
    return wochentag;
  }

  public void setWochentag(String wochentag) {
    this.wochentag = wochentag;
  }

  public String getArtName() {
    return artName;
  }

  public void setArtName(String artName) {
    this.artName = artName;
  }

  public boolean isHasAufstellung() {
    return hasAufstellung;
  }

  public void setHasAufstellung(boolean hasAufstellung) {
    this.hasAufstellung = hasAufstellung;
  }

  
  public Integer getAnzahlRies() {
    return anzahlRies;
  }

  
  public void setAnzahlRies(Integer anzahlRies) {
    this.anzahlRies = anzahlRies;
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Spiel)) {
      return false;
    }

    Spiel other = (Spiel)obj;
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }

}
