/* ProtectRecord.java
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
PROTECT record - worksheet protection (type = 18)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fLock    2  =1 if the document is protected
          =0 if the document is not
             protected

  Description
   The PROTECT record specifies whether or not an Excel
   document has been protected through the Options Protect
   Document command.

   Note that this record specifies a document password, as
   opposed to the FILEPASS record, which specifies a file
   password.
*/

public class ProtectRecord extends BIFFRecord {

  private boolean protect;
  
  public ProtectRecord(boolean protect) {
    super(18, 2, 340);
    
    this.protect = protect;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    if (protect) {
      los.writeShort(1);
    } else {
      los.writeShort(0);
    }
  }

}
