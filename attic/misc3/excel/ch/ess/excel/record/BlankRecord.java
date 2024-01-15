/* BlankRecord.java
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
BLANK record - blank cell (type = 1)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   rw   2  row
  6   col     2   column
  8   rgbAttr  3  cell attributes

  Description
   A BLANK record describes a cell with no formula or
   value.
*/

public class BlankRecord extends CellRecord {

  public BlankRecord(int row, int col) {
    this(row, col, null);
  }

  public BlankRecord(int row, int col, CellAttributes attributes) {
    super(1, 7, row, col, attributes);
  } 

  public void writeData(LEDataOutputStream los) throws IOException {        
    //nothing        
  }

}
