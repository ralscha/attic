/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/spreadsheet/Sequence.java,v 1.1.1.1 2002/02/26 06:46:56 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:56 $
 */

package ch.ess.util.spreadsheet;

import ch.ess.util.spreadsheet.instruction.*;

/**
 * Insert the type's description here.
 * Creation date: (03.05.01 13:11:01)
 * @author: Daniel Ramseier
 */
public class Sequence {

  Instruction[] instructions = null;

  /**
   * Sequence constructor comment.
   */
  public Sequence(Instruction[] instructions) {

    super();

    this.instructions = instructions;
  }

  /**
   * Insert the method's description here.
   * Creation date: (03.05.01 13:15:46)
   */
  public void execute() {

    for (int i = 0; i < instructions.length; i++) {
      instructions[i].execute();
    }    
  }

  /**
   * Insert the method's description here.
   * Creation date: (08.05.01 16:53:56)
   * @return java.lang.String
   */
  public String getJs() {

    String js = "function(){";

    for (int i = 0; i < instructions.length; i++) {
      js += instructions[i].getJs() + ";";
    }

    js += "}";

    return js;
  }
}
