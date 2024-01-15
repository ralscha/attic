package ch.ess.lbw.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;

@Entity
public class Lieferant extends BaseObject {
  private String nr;
  private String kurz;
  private String name;
  private String plz;
  private String ort;
  private String strasse;
  private String bemerkung;
  private String land;
  
  private String iso;
  private String isoGesellschaft;
  private String isoDatum;
  private BigDecimal isoPunkte;
  
  private Boolean vda;
  private String vdaBemerkung;
  private BigDecimal vdaPunkte;
  
  private Boolean qs;
  private String qsBemerkung;
  private BigDecimal qsPunkte;
  
  private Boolean polytec;
  private String polytecBemerkung;
  private BigDecimal polytecPunkte;
  
  private Boolean iso14001;
  private Boolean oekoAudit;
  private Boolean isoTs16949;
  
  private String iso14001Bemerkung;
  private String oekoAuditBemerkung;
  private String isoTs16949Bemerkung;
  
  private Set<Bewertung> bewertungen = new HashSet<Bewertung>();
  private Set<LieferantWerk> lieferantWerke = new HashSet<LieferantWerk>();
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "lieferant")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)    
  public Set<Bewertung> getBewertungen() {
    return bewertungen;
  }

  public void setBewertungen(Set<Bewertung> bewertungen) {
    this.bewertungen = bewertungen;
  }
  
  @Column(length = 1000)
  public String getBemerkung() {
    return bemerkung;
  }


  public String getIso() {
    return iso;
  }


  public void setIso(String iso) {
    this.iso = iso;
  }


  public Boolean getIso14001() {
    return iso14001;
  }


  public void setIso14001(Boolean iso14001) {
    this.iso14001 = iso14001;
  }


  public String getIso14001Bemerkung() {
    return iso14001Bemerkung;
  }


  public void setIso14001Bemerkung(String iso14001Bemerkung) {
    this.iso14001Bemerkung = iso14001Bemerkung;
  }


  public String getIsoDatum() {
    return isoDatum;
  }


  public void setIsoDatum(String isoDatum) {
    this.isoDatum = isoDatum;
  }


  public String getIsoGesellschaft() {
    return isoGesellschaft;
  }


  public void setIsoGesellschaft(String isoGesellschaft) {
    this.isoGesellschaft = isoGesellschaft;
  }


  public BigDecimal getIsoPunkte() {
    return isoPunkte;
  }


  public void setIsoPunkte(BigDecimal isoPunkte) {
    this.isoPunkte = isoPunkte;
  }


  public Boolean getIsoTs16949() {
    return isoTs16949;
  }


  public void setIsoTs16949(Boolean isoTs16949) {
    this.isoTs16949 = isoTs16949;
  }


  public String getIsoTs16949Bemerkung() {
    return isoTs16949Bemerkung;
  }


  public void setIsoTs16949Bemerkung(String isoTs16949Bemerkung) {
    this.isoTs16949Bemerkung = isoTs16949Bemerkung;
  }


  public String getKurz() {
    return kurz;
  }


  public void setKurz(String kurz) {
    this.kurz = kurz;
  }


  public String getLand() {
    return land;
  }


  public void setLand(String land) {
    this.land = land;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getNr() {
    return nr;
  }


  public void setNr(String nr) {
    this.nr = nr;
  }


  public Boolean getOekoAudit() {
    return oekoAudit;
  }


  public void setOekoAudit(Boolean oekoAudit) {
    this.oekoAudit = oekoAudit;
  }


  public String getOekoAuditBemerkung() {
    return oekoAuditBemerkung;
  }


  public void setOekoAuditBemerkung(String oekoAuditBemerkung) {
    this.oekoAuditBemerkung = oekoAuditBemerkung;
  }


  public String getOrt() {
    return ort;
  }


  public void setOrt(String ort) {
    this.ort = ort;
  }


  public String getPlz() {
    return plz;
  }


  public void setPlz(String plz) {
    this.plz = plz;
  }


  public Boolean getPolytec() {
    return polytec;
  }


  public void setPolytec(Boolean polytec) {
    this.polytec = polytec;
  }


  public String getPolytecBemerkung() {
    return polytecBemerkung;
  }


  public void setPolytecBemerkung(String polytecBemerkung) {
    this.polytecBemerkung = polytecBemerkung;
  }


  public BigDecimal getPolytecPunkte() {
    return polytecPunkte;
  }


  public void setPolytecPunkte(BigDecimal polytecPunkte) {
    this.polytecPunkte = polytecPunkte;
  }


  public Boolean getQs() {
    return qs;
  }


  public void setQs(Boolean qs) {
    this.qs = qs;
  }


  public String getQsBemerkung() {
    return qsBemerkung;
  }


  public void setQsBemerkung(String qsBemerkung) {
    this.qsBemerkung = qsBemerkung;
  }


  public BigDecimal getQsPunkte() {
    return qsPunkte;
  }


  public void setQsPunkte(BigDecimal qsPunkte) {
    this.qsPunkte = qsPunkte;
  }


  public String getStrasse() {
    return strasse;
  }


  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }


  public Boolean getVda() {
    return vda;
  }


  public void setVda(Boolean vda) {
    this.vda = vda;
  }


  public String getVdaBemerkung() {
    return vdaBemerkung;
  }


  public void setVdaBemerkung(String vdaBemerkung) {
    this.vdaBemerkung = vdaBemerkung;
  }


  public BigDecimal getVdaPunkte() {
    return vdaPunkte;
  }


  public void setVdaPunkte(BigDecimal vdaPunkte) {
    this.vdaPunkte = vdaPunkte;
  }


  public void setBemerkung(String bemerkung) {
    this.bemerkung = bemerkung;
  }

  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "lieferant")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<LieferantWerk> getLieferantWerke() {
    return lieferantWerke;
  }

  public void setLieferantWerke(Set<LieferantWerk> lieferantWerke) {
    this.lieferantWerke = lieferantWerke;
  }
}