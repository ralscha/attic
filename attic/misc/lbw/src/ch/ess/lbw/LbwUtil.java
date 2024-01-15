package ch.ess.lbw;

import java.math.BigDecimal;

import ch.ess.base.Constants;
import ch.ess.lbw.model.Bewertung;
import ch.ess.lbw.model.Lieferant;

public class LbwUtil {
  public static void updateNoten(Bewertung bewertung) {
    BigDecimal note = Constants.ZERO;
    
    if (bewertung.getPreis() != null) {
      note = note.add(bewertung.getPreis().multiply(new BigDecimal(5)));
    }
    
    if (bewertung.getService() != null) {
      note = note.add(bewertung.getService().multiply(new BigDecimal(10)));
    }
    
    if (bewertung.getTeilequalitaet() != null) {
      BigDecimal tmp = bewertung.getTeilequalitaet().multiply(new BigDecimal(50));
      note = note.add(tmp.divide(new BigDecimal("16.66"), 2, BigDecimal.ROUND_HALF_UP));
    }
    
    BigDecimal qaudit = Constants.ZERO;
    Lieferant lieferant = bewertung.getLieferant();
    if (lieferant.getIsoPunkte() != null) {
      qaudit = qaudit.add(lieferant.getIsoPunkte());
    }
    if (lieferant.getVdaPunkte() != null) {
      qaudit = qaudit.add(lieferant.getVdaPunkte());
    }
    if (lieferant.getQsPunkte() != null) {
      qaudit = qaudit.add(lieferant.getQsPunkte());
    }
    
    if (qaudit.compareTo(Constants.ZERO) != 0) {
      note = note.add(qaudit.multiply(new BigDecimal(10)));
    }
    
    if (note.compareTo(new BigDecimal(0)) != 0) {
      note = note.divide(new BigDecimal(6), 0, BigDecimal.ROUND_HALF_UP);
      bewertung.setManuelleBewertung(note);
    } else {
      bewertung.setManuelleBewertung(note);
    }
    
    BigDecimal sap = Constants.ZERO;
    if (bewertung.getSapLiefertreue() != null) {
      sap = sap.add(bewertung.getSapLiefertreue());
    }
    
    if (bewertung.getSapMengentreue() != null) {
      sap = sap.add(bewertung.getSapMengentreue());
    }
    
    if (sap.compareTo(Constants.ZERO) != 0) {
      sap = sap.divide(new BigDecimal(8), 0, BigDecimal.ROUND_HALF_UP);
    }
        
    bewertung.setGesamtNote(sap.add(note));      
            
  }

}
