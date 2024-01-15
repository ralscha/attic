package ch.sr.lotto.producer;

import java.util.*;

import ch.sr.lotto.statistic.*;

public class TipMaker {

  public java.util.List createTips(AusspielungenStatistiken ausspielungen, int numberOfTips) {
    java.util.List tipsList = new ArrayList();

    Random r = new Random();

    int punkte[] = new int[45];
    int zufall;
    int i, x, summe;
    int auswahl[] = new int[6];
    boolean nichtgefunden;
    boolean nochmal;
    boolean wahl[] = new boolean[45];
    int versuche;

    int hgerauswahl;
    int summenauswahl;
    VerbundType verbundauswahl;

    Map verbundMap;
    Map summeMap;
    int hger[];
    int ausstehend[];
    int haeuf[];

    int verbundtotal = 0;
    int summentotal = 0;
    int hgertotal = 0;
    int ausstehendtotal = 0;
    int haeufigkeittotal = 0;
    int punkttotal = 0;

    for (i = 0; i < 45; i++)
      punkte[i] = 0;

    ausstehend = ausspielungen.getAusstehend();
    haeuf = ausspielungen.getAusspielungenStatistik(AusspielungenType.ALLE).getHaeufigkeit();

    summeMap = ausspielungen.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungSumme();
    verbundMap =
      ausspielungen.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungVerbund();
    hger =
      ausspielungen
        .getAusspielungenStatistik(AusspielungenType.ALLE)
        .getVerteilungHaeufigkeitGerade();

    int haeufigkeit[] = new int[haeuf.length];
    for (x = 0; x < haeuf.length; x++)
      haeufigkeit[x] = haeuf[x];

    //Hochrechnen der Haeufigkeit Zahlen 41 bis 45
    int anzalle =
      ausspielungen.getAusspielungenStatistik(AusspielungenType.ALLE).getAnzahlAusspielungen();
    int anzaus42 =
      ausspielungen.getAusspielungenStatistik(AusspielungenType.AUS42).getAnzahlAusspielungen();
    int anzaus45 =
      ausspielungen.getAusspielungenStatistik(AusspielungenType.AUS45).getAnzahlAusspielungen();

    haeufigkeit[40] = haeufigkeit[40] * anzalle / anzaus42;
    haeufigkeit[41] = haeufigkeit[41] * anzalle / anzaus42;
    haeufigkeit[42] = haeufigkeit[42] * anzalle / anzaus42;
    haeufigkeit[43] = haeufigkeit[43] * anzalle / anzaus45;
    haeufigkeit[44] = haeufigkeit[44] * anzalle / anzaus45;

    //Total ausrechnen
    for (i = 0; i < 45; i++) {
      ausstehendtotal += ausstehend[i];
      haeufigkeittotal += haeufigkeit[i];
      punkte[i] = ausstehend[i] * 10 + haeufigkeit[i];
      //punkte[i] = ausstehend[i]*5 + haeufigkeit[i];
      punkttotal += punkte[i];
    }

    summentotal = verbundtotal = hgertotal = anzalle;
    VerbundType vb[] = VerbundType.getTypesAsArray();

    Set keyset = summeMap.keySet();
    Integer[] summen = new Integer[keyset.size()];
    Object[] objarr = keyset.toArray();
    for (int y = 0; y < keyset.size(); y++) {
      summen[y] = (Integer) objarr[y];
    }
    Arrays.sort(summen);

    for (int n = 0; n < numberOfTips; n++) {
      nochmal = false;
      do {
        // Auswahl gerade ungerade
        zufall = Math.abs((r.nextInt() % hgertotal) + 1);
        x = 0;
        summe = hger[x];
        while (summe < zufall)
          summe += hger[++x];
        hgerauswahl = x;

        // Auswahl der Summe
        zufall = Math.abs((r.nextInt() % summentotal) + 1);
        x = 0;
        summe = ((Integer) summeMap.get(summen[x])).intValue();
        while (summe < zufall)
          summe += ((Integer) summeMap.get(summen[++x])).intValue();
        summenauswahl = summen[x].intValue();

        // Auswahl des Verbunds
        zufall = Math.abs((r.nextInt() % verbundtotal) + 1);
        x = 0;
        summe = ((Integer) verbundMap.get(vb[x].getExternal())).intValue();
        while (summe < zufall) {
          summe += ((Integer) verbundMap.get(vb[++x].getExternal())).intValue();
        }
        verbundauswahl = vb[x];

        versuche = 0;
        // Ziehung der Lottozahlen
        do {
          nichtgefunden = false;
          for (i = 0; i < 45; i++)
            wahl[i] = false;

          for (i = 0; i < 6; i++) {
            do {
              zufall = Math.abs((r.nextInt() % punkttotal) + 1);
              x = 0;
              summe = punkte[x];
              while (summe < zufall)
                summe += punkte[++x];

            }
            while (wahl[x]);
            wahl[x] = true;
          }

          x = 0;
          for (i = 0; i < 45; i++)
            if (wahl[i])
              auswahl[x++] = i + 1;

          DrawStatistic dr = new DrawStatistic(auswahl);

          //Testen
          if (dr.getGerade() != hgerauswahl)
            nichtgefunden = true;
          if (dr.getVerbund().equals(verbundauswahl))
            nichtgefunden = true;
          if ((summenauswahl - dr.getSumme() > 10) || (summenauswahl - dr.getSumme() < -10))
            nichtgefunden = true;

          if (nichtgefunden) {
            versuche++;
            if (versuche >= 100) {
              nochmal = true;
              nichtgefunden = false;
            }
          } else
            nochmal = false;

        }
        while (nichtgefunden);

      }
      while (nochmal);

      int[] tmp = new int[6];
      for (int j = 0; j < 6; j++) {
        tmp[j] = auswahl[j];
      }
      tipsList.add(tmp);

    } // Hauptschleife

    return tipsList;

  }

}