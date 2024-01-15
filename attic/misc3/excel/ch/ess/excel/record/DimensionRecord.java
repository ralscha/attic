/* DimensionRecord.java
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
DIMENSIONS record - cell table size (type = 0)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   rwMic    2  first defined row on the document
  6   rwMac    2  last defined row on the document,plus 1
  8   colMic   2  first defined column on the document
  10  colMac   2  last defined column on the document, plus 1

  Description
   The DIMENSIONS record contains the minimum and maximum bounds
   of the document. It tells us very quickly the approximate
   size of the document.

   Note that both the rwMac and colMac fields are 1 greater
   than the actual last row and column.  For example, for
   a worksheet that exists between cells B3 and D6, we would
   have rwMic = 2, colMic = 1, rwMac = 6, colMac = 4.
*/

public class DimensionRecord extends BIFFRecord {


  private int firstRow;
  private int lastRow;
  private int firstCol;
  private int lastCol;
  
  public DimensionRecord(int firstRow, int lastRow, int firstCol, int lastCol) {
    super(0, 8, 310);

    this.firstRow = firstRow;
    this.lastRow  = lastRow;
    this.firstCol = firstCol;
    this.lastCol  = lastCol;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    
    los.writeShort(firstRow);
    los.writeShort(lastRow);
    los.writeShort(firstCol);
    los.writeShort(lastCol);
        
  }

}
