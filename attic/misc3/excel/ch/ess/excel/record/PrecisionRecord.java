/* PrecisionRecord.java
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
PRECISION record - precision (type = 14)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fFullPrec   2   document precision
          =1 for full precision
          =0 for precision as displayed

  Description
   The PRECISION record specifies the precision
   as set in the Options Calculation command.
*/

public class PrecisionRecord extends BIFFRecord {

  private boolean fullPrecision;

  public PrecisionRecord(boolean fullPrecision) {
    super(14, 2, 60);
    
    this.fullPrecision = fullPrecision;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    if (fullPrecision)
      los.writeShort(1);
    else
      los.writeShort(0);
  }

}
