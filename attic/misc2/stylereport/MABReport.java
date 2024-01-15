package test;

import inetsoft.report.*;
import inetsoft.report.style.*;
import inetsoft.report.painter.*;
import inetsoft.report.io.*;
import inetsoft.report.lens.*;
import inetsoft.report.lens.*;
import inetsoft.report.filter.*;
import inetsoft.report.filter.style.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import inetsoft.report.*;
import inetsoft.report.lens.swing.*;
import inetsoft.report.pdf.*;
import java.io.*;
import ch.ess.pbroker.db.*;

public class MABReport {

  private Mitarbeiterbeurteilung mab;
  private Vertraege vertrag;

  public MABReport(Mitarbeiterbeurteilung mab, Vertraege v) {
    this.mab = mab;
    this.vertrag = v;
  }

  public XStyleSheet createReport() throws IOException {
  
    String[] beurteilungHeader = {"", "Leistung", "Kommentar"};
    String[][] gesamtBeurteilungData = {{"Gesamtbeurteilung", "", ""}};

    gesamtBeurteilungData[0][1] = getBewertung(mab.getGesamt());
    gesamtBeurteilungData[0][2] = suppressNull(mab.getKommentar());


    String[][] beurteilungData = {        
        {"Selbständigkeit", "", ""},
        {"Beweglichkeit/Flexibilität", "", ""},
        {"Engagement", "", ""},
        {"Kommunikationsfähigkeit", "", ""},
        {"Kritik/Konfliktfähigkeit", "", ""},
        {"Teamfähigkeit", "", ""},
        {"Kundenorientierung", "", ""},
        {"Fachkenntnisse", "", ""},
        {"Arbeitstechnik", "", ""},
        {"Entscheidungsvorbereitung und Beratungskompetenz", "", ""},
        {"Führungskompetenz", "", ""}
        };

    //Selbständigkeit
    beurteilungData[0][1] = getBewertung(mab.getSelbstaendigkeit());
    beurteilungData[0][2] = suppressNull(mab.getSelbstaendigkeitBeg());

    //Beweglichtkeit
    beurteilungData[1][1] = getBewertung(mab.getBeweglichkeit());
    beurteilungData[1][2] = suppressNull(mab.getBeweglichkeitBeg());

    //Engagement
    beurteilungData[2][1] = getBewertung(mab.getEngagement());
    beurteilungData[2][2] = suppressNull(mab.getEngagementBeg());

    //Kommunikationsfähigkeit
    beurteilungData[3][1] = getBewertung(mab.getKommunikation());
    beurteilungData[3][2] = suppressNull(mab.getKommunikationBeg());

    //Kritik/Konfliktfähigkeit
    beurteilungData[4][1] = getBewertung(mab.getKonflikt());
    beurteilungData[4][2] = suppressNull(mab.getKonfliktBeg());

    //Teamfähigkeit
    beurteilungData[5][1] = getBewertung(mab.getTeam());
    beurteilungData[5][2] = suppressNull(mab.getTeamBeg());

    //Kundenorientierung
    beurteilungData[6][1] = getBewertung(mab.getKundenorientiert());
    beurteilungData[6][2] = suppressNull(mab.getKundenorientiertBeg());

    //Fachkenntnisse
    beurteilungData[7][1] = getBewertung(mab.getFachkenntnisse());
    beurteilungData[7][2] = suppressNull(mab.getFachkenntnisseBeg());

    //Arbeitstechnik
    beurteilungData[8][1] = getBewertung(mab.getArbeitstechnik());
    beurteilungData[8][2] = suppressNull(mab.getArbeitstechnikBeg());

    //Entscheidungsvorbereitung
    beurteilungData[9][1] = getBewertung(mab.getEntscheidungsvorbereitung());
    beurteilungData[9][2] = suppressNull(mab.getEntscheidungsvorbereitungBeg());

    //Führungskompetenz
    beurteilungData[10][1] = getBewertung(mab.getEntscheidungskompetenz());
    beurteilungData[10][2] = suppressNull(mab.getEntscheidungskompetenzBeg());

/*
	private OReference erfasser;

*/
    final String[] angabenHeader = {"","", "", "", ""};
   
    String[][] person = { {"Name:", "", "", "Vorname:", ""},
                                 {"Lieferant:", "", "", "", ""},
                                 {"Vertrag Nr.:", "", "", "Zeitraum:", ""},
                                 {"Funktion:", "", "", "OE:", ""}};


    String[][] vor = { {"Name:", "", "", "Vorname:", ""},
                                 {"OE:", "", "", "", ""}};


    person[0][1] = suppressNull(vertrag.getMitarbeiterName());
    person[0][4] = suppressNull(vertrag.getMitarbeiterVorname());
    person[1][1] = suppressNull(vertrag.getLieferantName());
    person[2][1] = suppressNull(vertrag.getFullVertragsNrNummer());
    
    String von = vertrag.getDauerVon()!= null ? ch.ess.pbroker.Constants.defaultDateFormat.format(vertrag.getDauerVon()): "";
    String bis = vertrag.getDauerBis()!= null ? ch.ess.pbroker.Constants.defaultDateFormat.format(vertrag.getDauerBis()): "";
    person[2][4] = von + " - "  + bis;

    person[3][1] = suppressNull(vertrag.getArbeitsArt());
    person[3][4] = suppressNull(vertrag.getArbeitsortOE());

	
    Benutzer erfasser = mab.getErfasser();    
    KundenMitarbeiter[] km = erfasser.getKundenMitarbeiter();
    if (km != null) {
      Personalien p = km[0].getPersonalien();
      if (p != null) {
        vor[0][1] = suppressNull(p.getName());
        vor[0][4] = suppressNull(p.getVorname());       
      }
      OEs o = km[0].getOE();
      if (o != null) {
        vor[1][1] = suppressNull(o.getOE_Name());
      }
    }


    String[][] unterVor = { {"Ort:", "", "", "Datum:", ""},
                                  {"Name:", "", "", "", ""}};

    String[][] unterMit = { {"Ort:", "", "", "Datum:", ""},
                                  {"Name:", "", "", "", ""}};

    
    unterVor[0][1] = suppressNull(mab.getOrtErfasser());    
    if (mab.getSignaturErfasser() != null)
      unterVor[0][4] = ch.ess.pbroker.Constants.defaultDateFormat.format(mab.getSignaturErfasser());    
    unterVor[1][1] = suppressNull(mab.getUnterschriftErfasser());


    unterMit[0][1] = suppressNull(mab.getOrtEMA());    
    if (mab.getSignaturEMA() != null)
      unterMit[0][4] = ch.ess.pbroker.Constants.defaultDateFormat.format(mab.getSignaturEMA());    
    
    String name = suppressNull(vertrag.getMitarbeiterName()) + " ";
    name += suppressNull(vertrag.getMitarbeiterVorname());
    unterMit[1][1] = name;


    //Angaben zur Person
    TableModel tmper = new DefaultTableModel(person, angabenHeader);
    TableModelLens lensp = new TableModelLens(tmper);
    lensp.setColAutoSize(false);
    lensp.setRowAutoSize(true);
    lensp.setColWidth(0, 65);
    lensp.setColWidth(1, 200);
    lensp.setColWidth(2, 20);
    lensp.setColWidth(3, 65);
    lensp.setColWidth(4, 200);
    lensp.setHeaderRowCount(0);
    lensp.setHeaderColCount(0);

    //Angaben zum Vorgesetzten
    TableModel tmvor = new DefaultTableModel(vor, angabenHeader);
    TableModelLens lensv = new TableModelLens(tmvor);
    lensv.setColAutoSize(false);
    lensv.setRowAutoSize(true);
    lensv.setColWidth(0, 65);
    lensv.setColWidth(1, 200);
    lensv.setColWidth(2, 20);
    lensv.setColWidth(3, 65);
    lensv.setColWidth(4, 200);
    lensv.setHeaderRowCount(0);
    lensv.setHeaderColCount(0);

    //Unterschrift Vorgesetzter
    TableModel tmpuntervor = new DefaultTableModel(unterVor, angabenHeader);
    TableModelLens lensuv = new TableModelLens(tmpuntervor);
    lensuv.setColAutoSize(false);
    lensuv.setRowAutoSize(true);
    lensuv.setColWidth(0, 65);
    lensuv.setColWidth(1, 200);
    lensuv.setColWidth(2, 20);
    lensuv.setColWidth(3, 65);
    lensuv.setColWidth(4, 200);
    lensuv.setHeaderRowCount(0);
    lensuv.setHeaderColCount(0);

    //Unterschrift Mitarbeiter
    TableModel tmpuntermit = new DefaultTableModel(unterMit, angabenHeader);
    TableModelLens lensum = new TableModelLens(tmpuntermit);
    lensum.setColAutoSize(false);
    lensum.setRowAutoSize(true);
    lensum.setColWidth(0, 65);
    lensum.setColWidth(1, 200);
    lensum.setColWidth(2, 20);
    lensum.setColWidth(3, 65);
    lensum.setColWidth(4, 200);
    lensum.setHeaderRowCount(0);
    lensum.setHeaderColCount(0);


    //Beurteilung             
    TableModel model = new DefaultTableModel(gesamtBeurteilungData, beurteilungHeader);
    TableModel modelBeurteilung = new DefaultTableModel(beurteilungData, beurteilungHeader);   
    TableModelLens lens = new TableModelLens(model);
    TableModelLens lensBeurteilung = new TableModelLens(modelBeurteilung);
    
    lens.setColAutoSize(false);
    lens.setRowAutoSize(true);
    
    lens.setColWidth(0, 150);
    lens.setColWidth(1, 150);
    lens.setColWidth(2, StyleConstants.REMAINDER);      
    lens.setHeaderColCount(1);

    lensBeurteilung.setColAutoSize(false);
    lensBeurteilung.setRowAutoSize(true);    
    lensBeurteilung.setColWidth(0, 150);
    lensBeurteilung.setColWidth(1, 150);
    lensBeurteilung.setColWidth(2, StyleConstants.REMAINDER);   
    lensBeurteilung.setHeaderColCount(1);

    
    //Report erzeugen
    InputStream input = getClass().getResourceAsStream("/template/mab.xml");
    XStyleSheet report =
      (XStyleSheet) Builder.getBuilder(Builder.TEMPLATE, input).read(".");

    report.setElement("AngabenzurPerson", lensp);
    report.setElement("AngabenumVorgesetzten", lensv);

    report.setElement("GesamturteilTable", lens);
    report.setElement("BeurteilungTable", lensBeurteilung);

    report.setElement("UnterschriftVorgesetzter", lensuv);
    report.setElement("UnterschriftMitarbeiter", lensum);

    report.setElement("Hauptaufgaben", suppressNull(ch.ess.pbroker.util.Util.removeLineFeed(mab.getAufgaben())));    

    return report;
  }

  private String suppressNull(String str) {
    if (str == null)
      return "";
    else
      return str;
  }

  private String getBewertung(Integer bewertung) {
    if (bewertung == null)
      return "";

    int i = bewertung.intValue();
    switch (i) {
      case 1 : return "uebertrifft_die_Erwartungen_deutlich"; 
      case 2 : return "ueber_den_Erwartungen"; 
      case 3 : return "Entspricht_den_Erwartungen"; 
      case 4 : return "Entspricht_mehrheitlich_den_Erwartungen"; 
      case 5 : return "Nicht_den_Erwartungen_entsprechend";
      default : return "";
    }
  }


}