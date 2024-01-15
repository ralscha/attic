/*--

 Copyright (C) 2000 Brett McLaughlin. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
    this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions, the disclaimer that follows these conditions,
    and/or other materials provided with the distribution.

 3. Products derived from this software may not be called "Java and XML", nor may
    "Java and XML" appear in their name, without prior written permission from
    Brett McLaughlin (brett@newInstance.com).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 JDOM PROJECT  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software was originally created by Brett McLaughlin <brett@newInstance.com>.
 For more  information on "Java and XML", please see <http://www.oreilly.com/catalog/javaxml/>
 or <http://www.newInstance.com>.

 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * <b><code>Scheduler</code></b> is a class that allows
 *   addition, removal, and retrieval of a list of events, sorted
 *   by their occurrence time.
 *
 * @author Brett McLaughlin
 * @version 1.0
 */
public class Scheduler {

    /** List of event names (for sorting) */
    private static Vector events = null;

    /** Event details (name, time) */
    private static Hashtable eventDetails = null;

    /** Flag to indicate if events are sorted */
    private static boolean eventsSorted;

    /**
	 * <p>
	 * This will initialize the storage.
	 * </p>
	 */
    public Scheduler() {
        events = new Vector();
        eventDetails = new Hashtable();
        eventsSorted = true;
    }

    /**
	 * <p>
	 * This will add the requested event.
	 * </p>
	 *
	 * @param eventName <code>String</code> name of event to add.
	 * @param eventTime <code>Date</code> of event.
	 * @return <code>boolean</code> - indication of if event was added.
	 */
    public boolean addEvent(String eventName, Date eventTime) {
        // Add this event to the list of events
        if (!events.contains(eventName)) {
            events.addElement(eventName);
            eventDetails.put(eventName, eventTime);
            eventsSorted = false;

            // Start thread on server sorting
            SortEventsThread sorter = new SortEventsThread();
            sorter.start();
        }

        return true;
    }

    /**
	 * <p>
	 * This will remove the requested event.
	 * </p>
	 *
	 * @param eventName <code>String</code> name of event to remove.
	 * @return <code>boolean</code> - indication of if event was removed.
	 */
    public synchronized boolean removeEvent(String eventName) {
        events.remove(eventName);
        eventDetails.remove(eventName);

        return true;
    }

    /**
     * <p>
     * This will return the current listing of events.
     * </p>
     *
     * @return <code>Vector</code> - list of events.
     */
    public Vector getListOfEvents() {
        Vector list = new Vector();

        // Create a Date Formatter
        SimpleDateFormat fmt =
            new SimpleDateFormat("hh:mm a MM/dd/yyyy");

        // Add each event to the list
        for (int i=0; i<events.size(); i++) {
            String eventName = (String)events.elementAt(i);
            list.addElement("Event \"" + eventName +
                            "\" scheduled for " +
                            fmt.format(
                                (Date)eventDetails.get(eventName)));
        }

        return list;
    }

    /**
     * <p>
     * Sort the events in the current list.
     * <p>
     */
    private synchronized void sortEvents() {
        if (eventsSorted) {
            return;
        }

        // Create array of events as they are (unsorted)
        String[] eventNames = new String[events.size()];
        events.copyInto(eventNames);

        // Bubble sort these
        String tmpName;
        Date date1, date2;
        for (int i=0; i<eventNames.length - 1; i++) {
            for (int j=0; j<eventNames.length - i - 1; j++) {
                // Compare the dates for these events
                date1 = (Date)eventDetails.get(eventNames[j]);
                date2 = (Date)eventDetails.get(eventNames[j+1]);
                if (date1.compareTo(date2) > 0) {

                    // Swap if needed
                    tmpName = eventNames[j];
                    eventNames[j] = eventNames[j+1];
                    eventNames[j+1] = tmpName;

                }
            }
        }

        // Put into new Vector (ordered)
        Vector sortedEvents = new Vector();
        for (int i=0; i<eventNames.length; i++) {
            sortedEvents.addElement(eventNames[i]);
        }

        // Update the global events
        events = sortedEvents;
        eventsSorted = true;

    }

    /**
     * <p>
     * This inner class handles starting the sorting as
     *   a <code>Thread</code>.
     */
    class SortEventsThread extends Thread {

        /**
         * <p>
         * Start the sorting.
         * </p>
         */
        public void run() {
            sortEvents();
        }
    }

}
