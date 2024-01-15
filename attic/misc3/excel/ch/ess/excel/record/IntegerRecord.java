/* IntegerRecord.java
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

package ch.ess.excel.record;


import ch.ess.excel.attribute.*;
import java.io.*;

/*
INTEGER record - cell with constant integer (type = 2)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   rw   2  row
  6   col     2   column
  8   rgbAttr  3  cell attributes
  11  w    2  unsigned integer value

  Description
   An INTEGER record describes a cell containing a constant
   unsigned integer in the range 0 - 65535. Negative numbers and
   numbers outside this range must be stored as NUMBER records.
*/

public class IntegerRecord extends CellRecord {

  private short value;

  public IntegerRecord(int row, int col, short value) {
    this(row, col, value, null);
  }

  public IntegerRecord(int row, int col, short value, CellAttributes attributes) {
    super(2, 9, row, col, attributes);
    this.value = value;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {        
    los.writeShort(value);        
  }

}
