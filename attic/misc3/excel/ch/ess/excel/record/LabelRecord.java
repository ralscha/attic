/* LabelRecord.java
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
LABEL record - cell with constant string (type = 4)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   rw   2  row
  6   col     2   column
  8   rgbAttr  3  cell attributes
  11  cch     1   length of the string
  12  rgch    var  the string

  Description
   A LABEL record describes a cell with a constant string.
   The string length is in the range 0 - 255.

*/

public class LabelRecord extends CellRecord {


  private byte[] label;

  public LabelRecord(int row, int col, String label) {
    this(row, col, label, null);
  }

  public LabelRecord(int row, int col, String label, CellAttributes attributes) {
    super(4, 0, row, col, attributes);


    if (label.length() > 255) {
      label = label.substring(0, 255);
    } 

    this.label = label.getBytes();
    int len = 8 + label.length();

    setLength(len);
  } 

  public void writeData(LEDataOutputStream los) throws IOException {        
    los.writeByte(label.length);
    los.write(label);        
  }

}
