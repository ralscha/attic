/* CalcModeRecord.java
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
import ch.ess.excel.attribute.*;

/*
CALCMODE record - calculation mode (type = 13)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fAutoRecalc  2  calculation mode
          =0 for manual
          =1 for automatic
          =-1 for automatic, no tables
  Description
   The CALCMODE record specifies the calculation mode
   as set in the Options Calculation command.
*/

public class CalcModeRecord extends BIFFRecord {

  private CalcMode autoRecalc;

  public CalcModeRecord(CalcMode autoRecalc) {
    super(13, 2, 50);
    
    this.autoRecalc = autoRecalc;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(autoRecalc.getAttributeValue());
  }

}
