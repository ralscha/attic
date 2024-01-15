/* BOFRecord.java
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
BOF record - beginning of file (type = 9)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   vers    2   version number
  6   dt   2  document type
          0x10 = worksheet
          0x40 = macro sheet

  Description
   The BOF must be the first record in every BIFF file. The
   version number for Excel documents is currently 2; Microsoft
   may change this number in the future, as BIFF is modified for
   future needs.

   Currently defined version numbers are:
      Value   Name    Meaning
      -----   ----    -------
      2   versExcel   Excel document
      3   versMP   Multiplan document

   All other version numbers are reserved for future use by
   Microsoft.

   The high byte of the version number contains flag bits.
   Current flag values are:
      Mask Name    Meaning
      ---- ----    -------
      0x0100  bitFMP   =1 if the BIFF file is a Multiplan document
      0xFE00  RESERVED for future use - must be zeros

   The dt field specifies whether the document is a worksheet or
   a macro sheet.  
*/

public class BOFRecord extends BIFFRecord {

  public final static int WORKSHEET = 0x10;
  public final static int MACROSHEET = 0x40;

  private int type;

  public BOFRecord(int type) {
    super(9, 4, 0);

    this.type = type;
  } 

  public BOFRecord() {
    this(WORKSHEET);
  }

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(2);
    los.writeShort(type);        
  }

}
