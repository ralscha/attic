/* BackupRecord.java
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
BACKUP record - file backup option (type = 64)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fBackupFile  2  =1 if Excel should save a backup
             version of the file when it is
             saved
          =0 otherwise

  Description
   The BACKUP record specifies whether or not Excel should save
   backup versions of a BIFF file, as specified in the "Create
   Backup File" checkbox in the Save As dialog box.

*/

public class BackupRecord extends BIFFRecord {

  private boolean saveBackup;
  
  public BackupRecord(boolean saveBackup) {
    super(64, 2, 110);
    
    this.saveBackup = saveBackup;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    if (saveBackup) {
      los.writeShort(1);
    } else {
      los.writeShort(0);
    }
  }

}
