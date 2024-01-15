package ch.ess.lbw.web.lieferant;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class LieferantForm extends AbstractForm {

  private String nr;
  private String kurz;
  private String name;
  private String strasse;
  private String plz;
  private String bemerkung;
  private String ort;
  private String iso;
  private String isoDatum;
  private String isoGesellschaft;
  private BigDecimal isoPunkte;
  private boolean vda;
  private String vdaBemerkung;
  private BigDecimal vdaPunkte;
  private boolean qs;
  private String qsBemerkung;
  private BigDecimal qsPunkte;
  private boolean polytec;
  private String polytecBemerkung;
  private BigDecimal polytecPunkte;
  private String iso14001Bemerkung;
  private String isoTs16949Bemerkung;
  private String oekoAuditBemerkung;
  private boolean iso14001;
  private boolean isoTs16949;
  private boolean oekoAudit;
  private String tabset = "adresse";
  
  private String alarmDatum;
  private String alarmEmpfaenger;
  private String alarmSubjekt;
  private String alarmText;
  
  private String isoAlarmId;
  private String[] werkIds;
  
  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    vda = false;
    qs = false;
    polytec = false;
    iso14001 = false;
    oekoAudit = false;
    isoTs16949 = false;
    werkIds = null;
  }

  @ValidatorField(key = "lieferant.nr", required = true)
  public void setNr(final String nr) {
    this.nr = nr;
  }

  public String getNr() {
    return nr;
  }

  @ValidatorField(key = "lieferant.kurz", required = true)
  public void setKurz(final String kurz) {
    this.kurz = kurz;
  }

  public String getKurz() {
    return kurz;
  }

  @ValidatorField(key = "lieferant.name", required = true)
  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setStrasse(final String strasse) {
    this.strasse = strasse;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setPlz(final String plz) {
    this.plz = plz;
  }

  public String getPlz() {
    return plz;
  }

  public void setBemerkung(final String bemerkung) {
    this.bemerkung = bemerkung;
  }

  public String getBemerkung() {
    return bemerkung;
  }

  public void setOrt(final String ort) {
    this.ort = ort;
  }

  public String getOrt() {
    return ort;
  }

  public void setIso(final String iso) {
    this.iso = iso;
  }

  public String getIso() {
    return iso;
  }

  public void setIsoDatum(final String isoDatum) {
    this.isoDatum = isoDatum;
  }

  public String getIsoDatum() {
    return isoDatum;
  }

  public void setIsoGesellschaft(final String isoGesellschaft) {
    this.isoGesellschaft = isoGesellschaft;
  }

  public String getIsoGesellschaft() {
    return isoGesellschaft;
  }

  public void setIsoPunkte(final BigDecimal isoPunkte) {
    this.isoPunkte = isoPunkte;
  }

  public BigDecimal getIsoPunkte() {
    return isoPunkte;
  }

  public void setVda(final boolean vda) {
    this.vda = vda;
  }

  public boolean isVda() {
    return vda;
  }

  public void setVdaBemerkung(final String vdaBemerkung) {
    this.vdaBemerkung = vdaBemerkung;
  }

  public String getVdaBemerkung() {
    return vdaBemerkung;
  }

  public void setVdaPunkte(final BigDecimal vdaPunkte) {
    this.vdaPunkte = vdaPunkte;
  }

  public BigDecimal getVdaPunkte() {
    return vdaPunkte;
  }

  public void setQs(final boolean qs) {
    this.qs = qs;
  }

  public boolean isQs() {
    return qs;
  }

  public void setQsBemerkung(final String qsBemerkung) {
    this.qsBemerkung = qsBemerkung;
  }

  public String getQsBemerkung() {
    return qsBemerkung;
  }

  public void setQsPunkte(final BigDecimal qsPunkte) {
    this.qsPunkte = qsPunkte;
  }

  public BigDecimal getQsPunkte() {
    return qsPunkte;
  }

  public void setPolytec(final boolean polytec) {
    this.polytec = polytec;
  }

  public boolean isPolytec() {
    return polytec;
  }

  public void setPolytecBemerkung(final String polytecDatum) {
    this.polytecBemerkung = polytecDatum;
  }

  public String getPolytecBemerkung() {
    return polytecBemerkung;
  }

  public void setPolytecPunkte(final BigDecimal polytecPunkte) {
    this.polytecPunkte = polytecPunkte;
  }

  public BigDecimal getPolytecPunkte() {
    return polytecPunkte;
  }

  public void setiso14001Bemerkung(final String iso14001Bemerkung) {
    this.iso14001Bemerkung = iso14001Bemerkung;
  }

  public String getiso14001Bemerkung() {
    return iso14001Bemerkung;
  }

  public void setisoTs16949Bemerkung(final String isoTs16949Bemerkung) {
    this.isoTs16949Bemerkung = isoTs16949Bemerkung;
  }

  public String getisoTs16949Bemerkung() {
    return isoTs16949Bemerkung;
  }

  public void setoekoAuditBemerkung(final String oekoAuditBemerkung) {
    this.oekoAuditBemerkung = oekoAuditBemerkung;
  }

  public String getoekoAuditBemerkung() {
    return oekoAuditBemerkung;
  }

  public boolean isIso14001() {
    return iso14001;
  }

  public void setIso14001(boolean iso14001) {
    this.iso14001 = iso14001;
  }

  public String getIso14001Bemerkung() {
    return iso14001Bemerkung;
  }

  public void setIso14001Bemerkung(String iso14001Bemerkung) {
    this.iso14001Bemerkung = iso14001Bemerkung;
  }

  public boolean isIsoTs16949() {
    return isoTs16949;
  }

  public void setIsoTs16949(boolean isoTs16949) {
    this.isoTs16949 = isoTs16949;
  }

  public String getIsoTs16949Bemerkung() {
    return isoTs16949Bemerkung;
  }

  public void setIsoTs16949Bemerkung(String isoTs16949Bemerkung) {
    this.isoTs16949Bemerkung = isoTs16949Bemerkung;
  }

  public boolean isOekoAudit() {
    return oekoAudit;
  }

  public void setOekoAudit(boolean oekoAudit) {
    this.oekoAudit = oekoAudit;
  }

  public String getOekoAuditBemerkung() {
    return oekoAuditBemerkung;
  }

  public void setOekoAuditBemerkung(String oekoAuditBemerkung) {
    this.oekoAuditBemerkung = oekoAuditBemerkung;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public String getAlarmDatum() {
    return alarmDatum;
  }

  public void setAlarmDatum(String alarmDatum) {
    this.alarmDatum = alarmDatum;
  }

  public String getAlarmEmpfaenger() {
    return alarmEmpfaenger;
  }

  public void setAlarmEmpfaenger(String alarmEmpfaenger) {
    this.alarmEmpfaenger = alarmEmpfaenger;
  }

  public String getAlarmSubjekt() {
    return alarmSubjekt;
  }

  public void setAlarmSubjekt(String alarmSubjekt) {
    this.alarmSubjekt = alarmSubjekt;
  }

  public String getAlarmText() {
    return alarmText;
  }

  public void setAlarmText(String alarmText) {
    this.alarmText = alarmText;
  }

  public String getIsoAlarmId() {
    return isoAlarmId;
  }

  public void setIsoAlarmId(String isoAlarmId) {
    this.isoAlarmId = isoAlarmId;
  }

  
  public String[] getWerkIds() {
    return werkIds;
  }

  public void setWerkIds(String[] werkIds) {
    this.werkIds = werkIds;
  }

}
