/* ColWidthRecord.java
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


import java.io.*;

/*
COLWIDTH record - column width (type = 36)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   colFirst 1  first column in the range
  5   colLast  1  last column in the range
  6   dx   2  column width

  Description
   The COLWIDTH record sets the column width for a range of
   columns specified by colFirst and colLast.  The dx field is
   an unsigned integer specifying the column width in units of
   1/256 of a character.

*/

public class ColWidthRecord extends BIFFRecord {

  private int firstCol;
  private int lastCol;
  private int width;
  
  public ColWidthRecord(int firstCol, int lastCol, int width) {
    super(36, 4, 250);
    
    this.firstCol = firstCol;
    this.lastCol = lastCol;
    this.width = width;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeByte(firstCol);
    los.writeByte(lastCol);
    los.writeShort(width) ; 
  }

}
