/* CellError.java
 *
 * Copyright (c) 2001 R. Schaer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package ch.ess.excel.attribute;

/*
      0   #NULL!
      7   #DIV/0!
      15  #VALUE!
      23  #REF!
      29  #NAME?
      36  #NUM!
      42  #N/A
*/

public class CellError {
  private final int error;

  private CellError(int error) {
    this.error = error;
  }

  public String toString() {
    return String.valueOf(error);
  }

  public int getCellErrorValue() {
    return error;
  }

  public static final CellError NULL = new CellError(0);
  public static final CellError DIV0 = new CellError(7);
  public static final CellError VALUE = new CellError(15);
  public static final CellError REF = new CellError(23);
  public static final CellError NAME = new CellError(29);
  public static final CellError NUM = new CellError(36);
  public static final CellError NA = new CellError(42);
 


}