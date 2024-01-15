/* RightMarginRecord.java
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
RIGHT MARGIN record - (type = 39)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   num     8   right margin

  Description
   The RIGHT MARGIN record specifies the right margin in inches
   when a document is printed.  The num field is in 8-byte IEEE
   floating point format.
*/

public class RightMarginRecord extends BIFFRecord {

  private double margin;
  
  public RightMarginRecord(double margin) {
    super(39, 8, 220);
    
    this.margin = margin;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeDouble(margin);
  }

}
