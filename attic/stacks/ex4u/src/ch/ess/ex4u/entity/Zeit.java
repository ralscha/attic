package ch.ess.ex4u.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.sgwt.annotations.Title;
import ch.ess.sgwt.annotations.Type;
import ch.ess.sgwt.annotations.ValueMap;
import ch.ess.sgwt.annotations.Type.Types;
import ch.ess.sgwt.annotations.ValueMap.Value;

@Entity
public class Zeit extends AbstractEntity {

  public Zeit() {
    super();
  }

  public Zeit(ZeitRPC z) {
    super();
    setId(z.getId());
    setPeriode(z.getPeriode());
    setVonDatum(z.getVonDatum());
    setBisDatum(z.getBisDatum());
    setVonZeit(z.getVonZeit());
    setBisZeit(z.getBisZeit());
    setStunden(z.getStunden());
    setSpesen(z.getSpesen());
    setBemerkung(z.getBemerkung());
    setGenehmigt(z.isGenehmigt());
  }

  public ZeitRPC getZeitRPC() {
    ZeitRPC z = new ZeitRPC();
    z.setId(getId());
    z.setPeriode(getPeriode());
    z.setVonDatum(getVonDatum());
    z.setBisDatum(getBisDatum());
    z.setVonZeit(getVonZeit());
    z.setBisZeit(getBisZeit());
    z.setStunden(getStunden());
    z.setSpesen(getSpesen());
    z.setBemerkung(getBemerkung());
    z.setGenehmigt(isGenehmigt());
    
    z.setVertragId(getVertrag().getId());
    z.setUserId(getUser().getId());
    z.setZielgefaessId(getZielgefaess().getId());
    return z;
  }

  @NotNull
  @Title("Periode")
  @ValueMap({@Value(ID = "d", text = "Tag"), @Value(ID = "w", text = "Woche"), @Value(ID = "m", text = "Monat")})
  private String periode;

  @NotNull
  @Title("Datum von")
  @Type(Types.date)
  private Date vonDatum;

  @Title("Datum bis")
  @Type(Types.date)
  private Date bisDatum;

  @Title("Beginn")
  @Type(Types.time)
  private Date vonZeit;

  @Title("Ende")
  @Type(Types.time)
  private Date bisZeit;

  @NotNull
  @Title("Anzahl Stunden")
  @Type(Types.float_)
  private double stunden;

  @Title("Spesen")
  @Type(Types.float_)
  private double spesen;

  @Title("Bemerkungen")
  @Length(max = 80)
  private String bemerkung;

  @Title("Genehmigt")
  private boolean genehmigt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vertragId", nullable = true)
  private Vertrag vertrag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "zielgefaessId", nullable = true)
  private Zielgefaess zielgefaess;


  public String getPeriode() {
    return periode;
  }

  public void setPeriode(String periode) {
    this.periode = periode;
  }

  public Date getVonDatum() {
    return vonDatum;
  }

  public void setVonDatum(Date vonDatum) {
    this.vonDatum = vonDatum;
  }

  public Date getBisDatum() {
    return bisDatum;
  }

  public void setBisDatum(Date bisDatum) {
    this.bisDatum = bisDatum;
  }

  public Date getVonZeit() {
    return vonZeit;
  }

  public void setVonZeit(Date vonZeit) {
    this.vonZeit = vonZeit;
  }

  public Date getBisZeit() {
    return bisZeit;
  }

  public void setBisZeit(Date bisZeit) {
    this.bisZeit = bisZeit;
  }

  public double getStunden() {
    return stunden;
  }

  public void setStunden(double stunden) {
    this.stunden = stunden;
  }

  public Double getSpesen() {
    return spesen;
  }

  public void setSpesen(double spesen) {
    this.spesen = spesen;
  }

  public String getBemerkung() {
    return bemerkung;
  }

  public void setBemerkung(String bemerkung) {
    this.bemerkung = bemerkung;
  }

  public boolean isGenehmigt() {
    return genehmigt;
  }

  public void setGenehmigt(boolean genehmigt) {
    this.genehmigt = genehmigt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Vertrag getVertrag() {
    return vertrag;
  }

  public void setVertrag(Vertrag vertrag) {
    this.vertrag = vertrag;
  }

  public Zielgefaess getZielgefaess() {
    return zielgefaess;
  }

  public void setZielgefaess(Zielgefaess zielgefaess) {
    this.zielgefaess = zielgefaess;
  }

}