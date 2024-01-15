/* RefModeRecord.java
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
REFMODE record - reference mode (type = 15)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fRefA1   2  reference mode
          =1 for A1 mode
          =0 for R1C1 mode

  Description
   The REFMODE record specifies the reference mode
   as set in the Options Desktop command.
*/

public class RefModeRecord extends BIFFRecord {

  private boolean a1Mode;

  public RefModeRecord(boolean a1Mode) {
    super(15, 2, 70);
    
    this.a1Mode = a1Mode;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    if (a1Mode)
      los.writeShort(1);
    else
      los.writeShort(0);
  }

}
