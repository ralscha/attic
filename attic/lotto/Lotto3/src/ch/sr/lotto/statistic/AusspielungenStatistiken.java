package ch.sr.lotto.statistic;

import java.util.*;

import ch.sr.lotto.db.*;

public class AusspielungenStatistiken {

  private Map aussStats;
  private int[] ausstehend;
  
  public AusspielungenStatistiken() {
    ausstehend = new int[45];
    
    aussStats = new HashMap();
    aussStats.put(AusspielungenType.ALLE.getExternal(), new AusspielungenStatistik_Alle());
    aussStats.put(AusspielungenType.AUS42.getExternal(), new AusspielungenStatistik_42());
    aussStats.put(AusspielungenType.AUS45.getExternal(), new AusspielungenStatistik_45());
    aussStats.put(AusspielungenType.AB1997.getExternal(), new AusspielungenStatistik_1997());
    aussStats.put(AusspielungenType.CURRENT_YEAR.getExternal(), new AusspielungenStatistik_CurrentYear());
    
    for (int i = 0; i < ausstehend.length; i++) {
      ausstehend[i] = 0;
    }
  }



  public void addDraw(Draw draw) {

    Iterator it = aussStats.values().iterator();
    while (it.hasNext()) {
      ((AusspielungenStatistik) it.next()).updateStatistik(draw);
    }

    for (int i = 0; i < 45; i++) {
      ausstehend[i]++;
    }

    int numbers[] = draw.getNumbers();
    for (int i = 0; i < 6; i++)
      ausstehend[numbers[i] - 1] = 0;
  }

  public AusspielungenStatistik getAusspielungenStatistik(AusspielungenType at) {
    return (AusspielungenStatistik) aussStats.get(at.getExternal());
  }

  public int[] getAusstehend() {
    return ausstehend;
  }

}
