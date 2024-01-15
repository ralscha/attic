/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/Command.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util;

/**
 * A generic interface for implementing the Command pattern
 *      with a single-argument method. Use Runnamble for no-argument
 *      methods.
 * @author Allen I. Holub
 */
public interface Command {
  void execute(Object argument);
}
