package lotto.extract;

import lotto.*;
import lotto.util.*;

import java.util.*;

public abstract class LottoExtractor {

	private static final String MONTHS[] = {
        "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober",
        "November", "Dezember"
    };

    protected double nextLottoGewinnsumme, nextJokerGewinnsumme;

    public LottoExtractor() {
        nextLottoGewinnsumme = nextJokerGewinnsumme = 0;
    }

    public abstract Ziehung extractLottoNumbers(Calendar nextDate) throws NewDataNotAvailableException ;
    public abstract LottoGewinnquote extractLottoGewinnquote(Calendar nextDate) throws NewDataNotAvailableException ;
    public abstract JokerGewinnquote extractJokerGewinnquote(Calendar nextDate) throws NewDataNotAvailableException ;

    public double getNextLottoGewinnsumme() {
        return nextLottoGewinnsumme;
    }

    public double getNextJokerGewinnsumme() {
        return nextJokerGewinnsumme;
    }


    protected String removePoint(String in) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) != '.')
                sb.append(in.charAt(i));
        }
        return sb.toString();
    }

    protected int getMonth(String monthname) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (monthname.equalsIgnoreCase(MONTHS[i])) {
                return i;
            }
        }
        return -1;
    }

    protected String stringconv(String in) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.'))
                sb.append(in.charAt(i));
        }
        return sb.toString();
    }

}