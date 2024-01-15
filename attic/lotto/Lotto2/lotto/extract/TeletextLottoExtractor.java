package lotto.extract;

import lotto.*;
import lotto.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TeletextLottoExtractor extends LottoExtractor {

    public TeletextLottoExtractor() {
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
                lottoURL = new URL(AppProperties.getInstance().getProperty("teletextURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("teletextFile"))));
            }


            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if (((String)vect.elementAt(i)).equalsIgnoreCase("LOTTO")) {

                        String help = (String)vect.elementAt(i-1);
                        d = Integer.parseInt(help.substring(0,2));  // Tag
                        m = Integer.parseInt(help.substring(3,5))-1;  // Monat
                        y = Integer.parseInt(help.substring(6));  // Jahr
                        Calendar date = new GregorianCalendar(y,m,d);

                        if (!((d == nextDate.get(Calendar.DATE)) &&
                            (m == nextDate.get(Calendar.MONTH)) &&
                            (y == nextDate.get(Calendar.YEAR))))
                            throw new NewDataNotAvailableException();

                        zie = new Ziehung();
                        zie.setDate(date);

                        zahlen[0] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+1)));  // 1. Zahl
                        zahlen[1] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+2)));  // 2. Zahl
                        zahlen[2] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+3)));  // 3. Zahl
                        zahlen[3] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+4))); // 4. Zahl
                        zahlen[4] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+5))); // 5. Zahl
                        zahlen[5] = Integer.parseInt(removeNonDigits((String)vect.elementAt(i+6))); // 6. Zahl
                        
                        
                        zie.setZZ(Integer.parseInt(removeNonDigits((String)vect.elementAt(i+7)))); // Zusatzzahl
                        	
                        zie.setJoker((String)vect.elementAt(i+32)); // Joker
                        i = vect.size();
                        zie.setZahlen(zahlen);
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

	private String removeNonDigits(String digit) {
		StringBuffer sb = new StringBuffer();
		char[] chars = digit.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i]))
				sb.append(chars[i]);
		}
		return sb.toString();
	}

    public LottoGewinnquote extractLottoGewinnquote(Calendar nextDate) throws NewDataNotAvailableException {
        URL lottoURL;
        BufferedReader inStream;
        String inputLine;
        Vector vect = new Vector();
        StringTokenizer st;
        int i, j, d, m, y;
        LottoGewinnquote lottogq = null;

        try {
            if ((AppProperties.getInstance().getProperty("webAccess")).equalsIgnoreCase("true")) {
                lottoURL = new URL(AppProperties.getInstance().getProperty("teletextURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("teletextFile"))));
            }


            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if (((String)vect.elementAt(i)).equalsIgnoreCase("LOTTO")) {

                        String help = (String)vect.elementAt(i-1);
                        d = Integer.parseInt(help.substring(0,2));  // Tag
                        m = Integer.parseInt(help.substring(3,5))-1;  // Monat
                        y = Integer.parseInt(help.substring(6));  // Jahr
                        Calendar date = new GregorianCalendar(y,m,d);

                        if (!((d == nextDate.get(Calendar.DATE)) &&
                             (m == nextDate.get(Calendar.MONTH)) &&
                             (y == nextDate.get(Calendar.YEAR))))
                            throw new NewDataNotAvailableException();

                        lottogq = new LottoGewinnquote();

                        if (((String)vect.elementAt(i+9)).equalsIgnoreCase("Jackpot"))
                            lottogq.set(LottoGewinnquote.JACKPOT, 0, new Double(stringconv((String)vect.elementAt(i+10))).doubleValue());
                        else
                            lottogq.set(LottoGewinnquote.R6, Integer.parseInt(stringconv((String)vect.elementAt(i+9))), new Double(stringconv((String)vect.elementAt(i+10))).doubleValue());

                        lottogq.set(LottoGewinnquote.R5P, Integer.parseInt(stringconv((String)vect.elementAt(i+12))), new Double(stringconv((String)vect.elementAt(i+13))).doubleValue());
                        lottogq.set(LottoGewinnquote.R5, Integer.parseInt(stringconv((String)vect.elementAt(i+15))), new Double(stringconv((String)vect.elementAt(i+16))).doubleValue());
                        lottogq.set(LottoGewinnquote.R4, Integer.parseInt(stringconv((String)vect.elementAt(i+18))), new Double(stringconv((String)vect.elementAt(i+19))).doubleValue());
                        lottogq.set(LottoGewinnquote.R3, Integer.parseInt(stringconv((String)vect.elementAt(i+21))), new Double(stringconv((String)vect.elementAt(i+22))).doubleValue());
                        nextLottoGewinnsumme = new Double(stringconv((String)vect.elementAt(i+29))).doubleValue();
                        i = vect.size();
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
        String inputLine;
        Vector vect = new Vector();
        StringTokenizer st;
        int i, j, d, m, y;
        JokerGewinnquote jokergq = null;

        try {
            if ((AppProperties.getInstance().getProperty("webAccess")).equalsIgnoreCase("true")) {
                lottoURL = new URL(AppProperties.getInstance().getProperty("teletextURL"));
                inStream = new BufferedReader(new TagFilterReader(new InputStreamReader(lottoURL.openStream())));
            }
            else {
                inStream = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getInstance().getProperty("teletextFile"))));
            }


            while ((inputLine = inStream.readLine()) != null) {
                st = new StringTokenizer(inputLine.trim());
                while (st.hasMoreTokens())
                    vect.addElement(st.nextToken());
            }

            try {
                i = 0;
                while (i < vect.size()) {
                    if (((String)vect.elementAt(i)).equalsIgnoreCase("LOTTO")) {

                        String help = (String)vect.elementAt(i-1);
                        d = Integer.parseInt(help.substring(0,2));  // Tag
                        m = Integer.parseInt(help.substring(3,5))-1;  // Monat
                        y = Integer.parseInt(help.substring(6));  // Jahr
                        Calendar date = new GregorianCalendar(y,m,d);

                        if (!((d == nextDate.get(Calendar.DATE)) &&
                             (m == nextDate.get(Calendar.MONTH)) &&
                             (y == nextDate.get(Calendar.YEAR))))
                            throw new NewDataNotAvailableException();


                        jokergq = new JokerGewinnquote();
                        if (((String)vect.elementAt(i+34)).equalsIgnoreCase("Jackpot"))
                            jokergq.set(JokerGewinnquote.JACKPOT, 0, new Double(stringconv((String)vect.elementAt(i+35))).doubleValue());
                        else
                            jokergq.set(JokerGewinnquote.R6, Integer.parseInt(stringconv((String)vect.elementAt(i+34))), new Double(stringconv((String)vect.elementAt(i+35))).doubleValue());

                        jokergq.set(JokerGewinnquote.R5, Integer.parseInt(stringconv((String)vect.elementAt(i+37))), new Double(stringconv((String)vect.elementAt(i+38))).doubleValue());
                        jokergq.set(JokerGewinnquote.R4, Integer.parseInt(stringconv((String)vect.elementAt(i+40))), new Double(stringconv((String)vect.elementAt(i+41))).doubleValue());
                        jokergq.set(JokerGewinnquote.R3, Integer.parseInt(stringconv((String)vect.elementAt(i+43))), new Double(stringconv((String)vect.elementAt(i+44))).doubleValue());
                        jokergq.set(JokerGewinnquote.R2, Integer.parseInt(stringconv((String)vect.elementAt(i+46))), new Double(stringconv((String)vect.elementAt(i+47))).doubleValue());
                        nextJokerGewinnsumme = new Double(stringconv((String)vect.elementAt(i+54))).doubleValue();

                        i = vect.size();
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