/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/Wait.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util;

import java.util.*;
import com.jalios.jdring.*;
import java.text.*;


/**
 * Class Wait
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class Wait {

  public static void main(String[] args) {

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    boolean error = false;
    boolean pastdate = false;

    if (args.length == 1) {
      try {
        Date d = df.parse(args[0]);
        AlarmManager mgr = new AlarmManager();

        mgr.addAlarm(d, new AlarmListener() {

          public void handleAlarm(AlarmEntry entry) {
            System.exit(0);
          }
        });
      } catch (PastDateException e) {
        pastdate = true;
      } catch (ParseException e) {
        error = true;
      }
    } else {
      error = true;
    }

    if (error) {
      System.out.println("java Wait dd.MM.yyyy HH:mm");
    } else if (pastdate) {
      System.out.println("Datum liegt in der Vergangenheit");
      System.exit(1);
    }
  }
}
