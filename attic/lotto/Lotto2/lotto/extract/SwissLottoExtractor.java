package lotto.extract;

import lotto.*;
import lotto.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class SwissLottoExtractor extends LottoExtractor {

    public SwissLottoExtractor() {
        super();
    }

    public Ziehung extractLottoNumbers(Calendar nextDate) throws NewDataNotAvailableException {
        URL lottoURL;
        BufferedReader inStream;
        String inputLine;
        Vector vect = new Vector();
        StringTokenizer st;
        int i, d, m, y;
        int zahlen[] = new int[6];
        Ziehung zie = null;

        try {
            if ((AppProperties.getInstance().getProperty("webAccess")).equalsIgnoreCase("true")) {
                lottoURL = new URL(AppProperties.getInstance().getProperty("lottoURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("lottoFile"))));
            }

            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if ((((String)vect.elementAt(i)).equalsIgnoreCase("Mittwoch")) ||
                      (((String)vect.elementAt(i)).equalsIgnoreCase("Samstag"))) {
                        if ((i+18) < vect.size()) {
                            d = Integer.parseInt(removePoint((String)vect.elementAt(i+1)));  // Tag
                            m = getMonth((String)vect.elementAt(i+2));  // Monat
                            y = Integer.parseInt((String)vect.elementAt(i+3));  // Jahr
                            Calendar date = new GregorianCalendar(y,m,d);

                            if (!((d == nextDate.get(Calendar.DATE)) &&
                                (m == nextDate.get(Calendar.MONTH)) &&
                                (y == nextDate.get(Calendar.YEAR))))
                                throw new NewDataNotAvailableException();

                            zie = new Ziehung();
                            zie.setDate(date);

                            zahlen[0] = Integer.parseInt((String)vect.elementAt(i+5));  // 1. Zahl                        		
                            zahlen[1] = Integer.parseInt((String)vect.elementAt(i+7));  // 2. Zahl
                            zahlen[2] = Integer.parseInt((String)vect.elementAt(i+9));  // 3. Zahl
                            zahlen[3] = Integer.parseInt((String)vect.elementAt(i+11)); // 4. Zahl
                            zahlen[4] = Integer.parseInt((String)vect.elementAt(i+13)); // 5. Zahl
                            zahlen[5] = Integer.parseInt((String)vect.elementAt(i+15)); // 6. Zahl
                            zie.setZZ(Integer.parseInt((String)vect.elementAt(i+16))); // Zusatzzahl
                            zie.setJoker((String)vect.elementAt(i+18)); // Joker
                            i = vect.size();
                            zie.setZahlen(zahlen);
                        }
                    }
                    i++;
                }
            } catch (NumberFormatException nfe) {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoNumbers : " + nfe);
                return null;
            } finally {
                inStream.close();
            }
            return zie;

        } catch (MalformedURLException e) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoNumbers : " + e);
            return null;
        } catch (IOException ioe) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoNumbers : " + ioe);
           return null;
        }
    }

    public LottoGewinnquote extractLottoGewinnquote(Calendar nextDate) throws NewDataNotAvailableException {
        URL lottoURL;
        BufferedReader inStream;
        String inputLine, help;
        Vector vect = new Vector();
        Vector okvect = new Vector();
        StringTokenizer st;
        int i, j, d, m, y;
        LottoGewinnquote lottogq = null;

        try {
            if ((AppProperties.getInstance().getProperty("webAccess")).equalsIgnoreCase("true")) {
                lottoURL = new URL(AppProperties.getInstance().getProperty("lottoGewinnquoteURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("lottoGewinnquoteFile"))));
            }

            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if ((((String)vect.elementAt(i)).equalsIgnoreCase("Mittwoch")) ||
                      (((String)vect.elementAt(i)).equalsIgnoreCase("Samstag"))) {

                        if ((i+11) < vect.size()) {
                            for (j = 0; j < 11; j++) {
                                if (((String)vect.elementAt(i+j)).equalsIgnoreCase("nicht"))
                                    throw new NewDataNotAvailableException();
                            }

                            for (j = i; j < vect.size(); j++) {
                                try {
                                    help = (String)vect.elementAt(j);
                                    if (help.equalsIgnoreCase("Jackpot"))
                                        okvect.addElement(help);
                                    else if (getMonth(help) != -1)
                                        okvect.addElement(help);
                                    else {
                                        //Versuchen wir das mal umzuwandeln
                                        new Double(stringconv(help)).doubleValue();
                                        //Hat geklappt
                                        okvect.addElement(help);
                                    }
                                } catch (NumberFormatException nfe) { }
                            }

                            if (okvect.size() >= 18) {
                                d = Integer.parseInt(removePoint((String)okvect.elementAt(0)));  // Tag
                                m = getMonth((String)okvect.elementAt(1));  // Monat
                                y = Integer.parseInt((String)okvect.elementAt(2));  // Jahr
                                Calendar helpdate = new GregorianCalendar(y,m,d);

                                if (!((d == nextDate.get(Calendar.DATE)) &&
                                     (m == nextDate.get(Calendar.MONTH)) &&
                                     (y == nextDate.get(Calendar.YEAR))))
                                    throw new NewDataNotAvailableException();

                                lottogq = new LottoGewinnquote();
                                if (((String)okvect.elementAt(4)).equalsIgnoreCase("Jackpot"))
                                    lottogq.set(LottoGewinnquote.JACKPOT, 0, new Double(stringconv((String)okvect.elementAt(5))).doubleValue());
                                else
                                    lottogq.set(LottoGewinnquote.R6, Integer.parseInt((String)okvect.elementAt(4)), new Double(stringconv((String)okvect.elementAt(5))).doubleValue());

                                lottogq.set(LottoGewinnquote.R5P, Integer.parseInt((String)okvect.elementAt(7)), new Double(stringconv((String)okvect.elementAt(8))).doubleValue());
                                lottogq.set(LottoGewinnquote.R5, Integer.parseInt((String)okvect.elementAt(10)), new Double(stringconv((String)okvect.elementAt(11))).doubleValue());
                                lottogq.set(LottoGewinnquote.R4, Integer.parseInt((String)okvect.elementAt(13)), new Double(stringconv((String)okvect.elementAt(14))).doubleValue());
                                lottogq.set(LottoGewinnquote.R3, Integer.parseInt((String)okvect.elementAt(16)), new Double(stringconv((String)okvect.elementAt(17))).doubleValue());
                                nextLottoGewinnsumme = new Double(stringconv((String)okvect.elementAt(18))).doubleValue();
                                i = vect.size();
                            }
                        }
                    }
                    i++;
                }
            } catch (NumberFormatException nfe) {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoGewinnquote : " + nfe);
                return null;
            } finally {
                inStream.close();
            }
            return lottogq;

        } catch (MalformedURLException e) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoGewinnquote : " + e);
            return null;
        } catch (IOException ioe) {
           DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractLottoGewinnquote : " + ioe);
           return null;
        }
    }


    public JokerGewinnquote extractJokerGewinnquote(Calendar nextDate) throws NewDataNotAvailableException {
        URL lottoURL;
        BufferedReader inStream;
        String inputLine, help;
        Vector vect = new Vector();
        Vector okvect = new Vector();
        StringTokenizer st;
        int i, j, d, m, y;
        JokerGewinnquote jokergq = null;

        try {
            if ((AppProperties.getInstance().getProperty("webAccess")).equalsIgnoreCase("true")) {
                lottoURL = new URL(AppProperties.getInstance().getProperty("jokerGewinnquoteURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("jokerGewinnquoteFile"))));
            }

            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if ((((String)vect.elementAt(i)).equalsIgnoreCase("Mittwoch")) ||
                      (((String)vect.elementAt(i)).equalsIgnoreCase("Samstag"))) {
                        if ((i+11) < vect.size()) {
                            for (j = 0; j < 11; j++) {
                                if (((String)vect.elementAt(i+j)).equalsIgnoreCase("nicht"))
                                    throw new NewDataNotAvailableException();
                            }

                            for (j = i; j < vect.size(); j++) {
                                try {
                                    help = (String)vect.elementAt(j);
                                    if (help.equalsIgnoreCase("Jackpot"))
                                        okvect.addElement(help);
                                    else if (getMonth(help) != -1)
                                        okvect.addElement(help);
                                    else {
                                        //Versuchen wir das mal umzuwandeln
                                        new Double(stringconv(help)).doubleValue();
                                        //Hat geklappt
                                        okvect.addElement(help);
                                    }
                                } catch (NumberFormatException nfe) { }
                            }

                            if (okvect.size() >= 18) {

                                d = Integer.parseInt(removePoint((String)okvect.elementAt(0)));  // Tag
                                m = getMonth((String)okvect.elementAt(1));  // Monat
                                y = Integer.parseInt((String)okvect.elementAt(2));  // Jahr
                                Calendar helpdate = new GregorianCalendar(y,m,d);

                                if (!((d == nextDate.get(Calendar.DATE)) &&
                                     (m == nextDate.get(Calendar.MONTH)) &&
                                     (y == nextDate.get(Calendar.YEAR))))
                                    throw new NewDataNotAvailableException();

                                jokergq = new JokerGewinnquote();
                                if (((String)okvect.elementAt(4)).equalsIgnoreCase("Jackpot"))
                                    jokergq.set(JokerGewinnquote.JACKPOT, 0, new Double(stringconv((String)okvect.elementAt(5))).doubleValue());
                                else
                                    jokergq.set(JokerGewinnquote.R6, Integer.parseInt((String)okvect.elementAt(4)), new Double(stringconv((String)okvect.elementAt(5))).doubleValue());

                                jokergq.set(JokerGewinnquote.R5, Integer.parseInt((String)okvect.elementAt(7)), new Double(stringconv((String)okvect.elementAt(8))).doubleValue());
                                jokergq.set(JokerGewinnquote.R4, Integer.parseInt((String)okvect.elementAt(10)), new Double(stringconv((String)okvect.elementAt(11))).doubleValue());
                                jokergq.set(JokerGewinnquote.R3, Integer.parseInt((String)okvect.elementAt(13)), new Double(stringconv((String)okvect.elementAt(14))).doubleValue());
                                jokergq.set(JokerGewinnquote.R2, Integer.parseInt((String)okvect.elementAt(16)), new Double(stringconv((String)okvect.elementAt(17))).doubleValue());
                                nextJokerGewinnsumme = new Double(stringconv((String)okvect.elementAt(18))).doubleValue();

                                i = vect.size();
                            }
                        }
                    }
                    i++;
                }
            } catch (NumberFormatException nfe) {
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractJokerGewinnquote : " + nfe);
                return null;
            } finally {
                inStream.close();
            }
            return jokergq;

        } catch (MalformedURLException e) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractJokerGewinnquote : " + e);
            return null;
        } catch (IOException ioe) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "extractJokerGewinnquote : " + ioe);
            return null;
        }
    }

}