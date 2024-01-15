/* HorizontalPageBreaksRecord.java
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
HORIZONTAL PAGE BREAKS record - row page breaks (type = 27)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   cbrk    2   number of page breaks
  6   rgrw    var  array of rows

  Description
   The HORIZONTAL PAGE BREAKS record contains a list of explicit
   row page breaks.  The cbrk field contains the number of page
   breaks.  rgrw is an array of 2-byte integers specifying
   rows.  Excel sets a page break before each row in the list.
   The rows must be sorted in increasing order.
*/

public class HorizontalPageBreaksRecord extends BIFFRecord {

  private int[] rowsArray;
  
  public HorizontalPageBreaksRecord(int[] rowsArray) {
    super(27, 140);
    
    this.rowsArray = rowsArray;
    setLength(2 + rowsArray.length*2);
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(rowsArray.length);
    for (int i = 0; i < rowsArray.length; i++) {
      los.writeShort(rowsArray[i]);
    }
  }

}
