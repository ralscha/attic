/*
 * RemoteTableTaskEvent.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.util.EventObject;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

/**
 * Represents a RemoteTable task. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableTaskEvent extends EventObject {

    /** Specifies connect task. */
    public static final int CONNECT    = 0;

    /** Specifies disconnect task. */
    public static final int DISCONNECT = 1;

    /** Specifies update task. */
    public static final int UPDATE     = 2;

//NOTE: Change to 'final' if using Java 2 compilers.
    private /*final*/ int type;
    private /*final*/ FutureResult result;

    /**
     * Creates task with specified source and type.
     * <p>
     * The <I>type</I> should be one of: CONNECT, DISCONNECT, UPDATE.
     */
    public RemoteTableTaskEvent(Object source, int type) {
        super(source);
        this.type = type;
        this.result = null;
    }

    /**
     * Creates task with specified source, type, and future result.
     * <p>
     * The <I>type</I> should be one of: CONNECT, DISCONNECT, UPDATE.
     */
    public RemoteTableTaskEvent(Object source, int type, FutureResult result) {
        super(source);
        this.type = type;
        this.result = result;
    }

    /** Returns type. */
    public int getType() {
        return type;
    }

    /** Returns future result. */
    public FutureResult getFutureResult() {
        return result;
    }
}

