/* DeltaRecord.java
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
DELTA record - maximum iteration change (type = 16)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   numDelta 8  maximum change for iteration

  Description
   The DELTA record specifies the maximum change for an
   iterative model, as set in the Options Calculation command.
   The number is in 8-byte IEEE floating point format.
*/

public class DeltaRecord extends BIFFRecord {

  private double numDelta;
  
  public DeltaRecord(double numDelta) {
    super(16, 8, 80);
    
    this.numDelta = numDelta;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeDouble(numDelta);
  }

}
