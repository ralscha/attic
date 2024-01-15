/* VerticalPageBreaksRecord.java
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
VERTICAL PAGE BREAKS record - column page breaks (type = 26)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   cbrk    2   number of page breaks
  6   rgcol    var array of columns

  Description
   The VERTICAL PAGE BREAKS record contains a list of explicit
   column page breaks.  The cbrk field contains the number of
   page breaks.  rgcol is an array of 2-byte integers specifying
   columns.  Excel sets a page break before each column in the
   list.  The columns must be sorted in increasing order.
*/

public class VerticalPageBreaksRecord extends BIFFRecord {

  private int[] colsArray;
  
  public VerticalPageBreaksRecord(int[] colsArray) {
    super(26, 150);
    
    this.colsArray = colsArray;
    setLength(2 + colsArray.length*2);
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(colsArray.length);
    for (int i = 0; i < colsArray.length; i++) {
      los.writeShort(colsArray[i]);
    }
  }

}
