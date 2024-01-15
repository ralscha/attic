package ch.sr.lotto.statistic;

import java.util.*;

public class DrawStatistic {

  // Attributes
  private VerbundType verbund;
  private int gerade;
  private int tiefe;
  private int tiefeEndziffern;
  private int summe;
  private int endZiffernSumme;
  private int quersumme;
  private int dreierKlassen;
  private int fuenferKlassen;
  private int neunerKlassen;
  private int summeDifferenzen;

  ////////////////////////////////////////////////////////
  // Constructor

  public DrawStatistic(int zahlen[]) {
    gerade = summe = 0;

    for (int i = 0; i < 6; i++) {
      if (!isOdd(zahlen[i]))
        gerade++;

      if (zahlen[i] < 23)
        tiefe++;

      summe += zahlen[i];
    }

    verbund = sucheVerbund(zahlen);

    setSummeDifferenz(zahlen);
    setZiffern(zahlen);
    setKlassen(zahlen);
  }

  // Operations
  public VerbundType getVerbund() {
    return verbund;
  }

  public int getGerade() {
    return gerade;
  }

  public int getUngerade() {
    return (6 - gerade);
  }

  public int getTiefe() {
    return tiefe;
  }

  public int getHohe() {
    return 6 - tiefe;
  }

  public int getTiefeEndziffern() {
    return tiefeEndziffern;
  }

  public int getHoheEndziffern() {
    return 6 - tiefeEndziffern;
  }

  public int getSumme() {
    return summe;
  }

  public int getEndZiffernSumme() {
    return endZiffernSumme;
  }

  public int getQuersumme() {
    return quersumme;
  }
  public int getDreierKlassen() {
    return dreierKlassen;
  }

  public int getFuenferKlassen() {
    return fuenferKlassen;
  }

  public int getNeunerKlassen() {
    return neunerKlassen;
  }

  public int getSummeDifferenzen() {
    return summeDifferenzen;
  }

  public static void main(String[] args) {
    
    int[] z = new int[]{5,6,25,28,36,41};
    DrawStatistic ds = new DrawStatistic(z);
    
    System.out.println(ds.getDreierKlassen());
    System.out.println(ds.getFuenferKlassen());
    System.out.println(ds.getNeunerKlassen());
    
  }

  private void setKlassen(int zahlen[]) {

    dreierKlassen = fuenferKlassen = neunerKlassen = 0;

    Set set3 = new HashSet();
    Set set5 = new HashSet();
    Set set9 = new HashSet();

    for (int i = 0; i < zahlen.length; i++) {

      int div3 = zahlen[i] / 3;
      int div5 = zahlen[i] / 5;
      int div9 = zahlen[i] / 9;

      int mod3 = zahlen[i] % 3;
      int mod5 = zahlen[i] % 5;
      int mod9 = zahlen[i] % 9;

      if (mod3 > 0)
        div3++;

      if (mod5 > 0)
        div5++;

      if (mod9 > 0)
        div9++;

      set3.add(new Integer(div3));
      set5.add(new Integer(div5));
      set9.add(new Integer(div9));
    }

    dreierKlassen = set3.size();
    fuenferKlassen = set5.size();
    neunerKlassen = set9.size();
  }

  private void setSummeDifferenz(int zahlen[]) {
    summeDifferenzen = 0;

    for (int i = 0; i < zahlen.length - 1; i++)
      summeDifferenzen += (zahlen[i + 1] - zahlen[i]);

  }

  private void setZiffern(int zahlen[]) {
    List einzeln = new ArrayList();

    quersumme = 0;
    tiefeEndziffern = 0;
    endZiffernSumme = 0;

    for (int i = 0; i < zahlen.length; i++) {

      String val = String.valueOf(zahlen[i]);

      einzeln.clear();
      for (int j = 0; j < val.length(); j++)
        einzeln.add(val.substring(j, j + 1));

      Iterator it = einzeln.iterator();
      while (it.hasNext()) {
        int z = Integer.parseInt((String) it.next());
        quersumme += z;

        if (!it.hasNext()) { //Letze Zahl					
          endZiffernSumme += z;

          if (z < 5)
            tiefeEndziffern++;
        }
      }
    }
  }

  private VerbundType sucheVerbund(int zahlen[]) {
    int s = 0;

    //2 minus 1
    if ((zahlen[1] - zahlen[0]) == 1)
      s += 10000;
    //3 minus 2
    if ((zahlen[2] - zahlen[1]) == 1)
      s += 1000;
    //4 minus 3
    if ((zahlen[3] - zahlen[2]) == 1)
      s += 100;
    //5 minus 4
    if ((zahlen[4] - zahlen[3]) == 1)
      s += 10;
    //6 minus 5
    if ((zahlen[5] - zahlen[4]) == 1)
      s += 1;

    switch (s) {
      case 1 :
      case 10 :
      case 100 :
      case 1000 :
      case 10000 :
        return VerbundType.ZWILLING;

      case 11 :
      case 110 :
      case 1100 :
      case 11000 :
        return VerbundType.DRILLING;

      case 111 :
      case 1110 :
      case 11100 :
        return VerbundType.VIERLING;

      case 1111 :
      case 11110 :
        return VerbundType.FUENFLING;

      case 11111 :
        return VerbundType.SECHSLING;

      case 101 :
      case 1001 :
      case 1010 :
      case 10001 :
      case 10010 :
      case 10100 :
        return VerbundType.DOPPELZWILLING;

      case 1011 :
      case 1101 :
      case 10011 :
      case 10110 :
      case 11001 :
      case 11010 :
        return VerbundType.DRILLINGZWILLING;

      case 10101 :
        return VerbundType.DREIFACHZWILLING;

      case 10111 :
      case 11101 :
        return VerbundType.VIERLINGZWILLING;

      case 11011 :
        return VerbundType.DOPPELDRILLING;

      default :
        return VerbundType.NICHTS;
    }
  }

  private boolean isOdd(int z) {
    return (z % 2 != 0);
  }

}