package lotto;

import java.util.Calendar;

class LottoStatsCYear extends LottoStats {

    protected boolean inRange(int nr, int jahr) {
        Calendar cal = Calendar.getInstance();

        if (jahr >= cal.get(Calendar.YEAR))
            return true;
        else
            return false;
    }
    
    
}
