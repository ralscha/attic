/* FilePassRecord.java
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
FILEPASS record - file password key (type = 47)
  Description
   The FILEPASS record is used for an Excel document which was
   saved with a password in the File Save As command.  If this
   record appears, it must directly follow the BOF record.  All
   subsequent BIFF records will be encrypted, so you cannot read
   a password-protected BIFF file.

   Note that this record specifies a file password, as opposed
   to the PASSWORD record, which specifies a document password.
*/

public class FilePassRecord extends BIFFRecord {

  private byte[] password;
  
  public FilePassRecord(String password) {
    super(47, 20);

    if (password.length() > 255) {
      password = password.substring(0, 255);
    } 

    setLength(1+password.length());
    
    this.password = password.getBytes();
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeByte(password.length);
    los.write(password);
  }

}
