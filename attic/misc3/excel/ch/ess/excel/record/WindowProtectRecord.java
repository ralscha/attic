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
WINDOW PROTECT record - window protection (type = 25)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fLockWn  2  =1 if the windows of the document
             are protected
          =0 otherwise

  Description
   The WINDOW PROTECT record specifies whether or not the
   document's windows are protected, as specified in the Protect
   Document command.
*/

public class WindowProtectRecord extends BIFFRecord {

  private boolean protect;
  
  public WindowProtectRecord(boolean protect) {
    super(25, 2, 350);
    
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
