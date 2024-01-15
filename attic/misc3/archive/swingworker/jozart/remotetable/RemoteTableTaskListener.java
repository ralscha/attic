/*
 * RemoteTableTaskListener.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.util.EventListener;

/**
 * RemoteTableTaskListeners are notified when tasks begin and end. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public interface RemoteTableTaskListener extends EventListener {
    /** Called when a task is scheduled to start. */
    void taskStarted(RemoteTableTaskEvent event);
    /** Called when a task ends. */
    void taskEnded(RemoteTableTaskEvent event);
}
