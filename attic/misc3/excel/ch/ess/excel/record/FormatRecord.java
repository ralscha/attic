/* FormatRecord.java
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
FORMAT record - cell format (type = 30)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   cch     1   length of format string
  5   rgch    var  picture format string

  Description
   The FORMAT record describes a picture format on the document.

   All the FORMAT records should appear together in a BIFF file.
   The order of FORMAT records in an existing BIFF file is
   important and should not be changed. You can add new formats
   to a file, but they should be added at the end of the FORMAT
   list.
*/

public class FormatRecord extends BIFFRecord {

  private byte[] format;
  
  public FormatRecord(String format) {
    super(30, 290);    

    if (format.length() > 255) {
      format = format.substring(0, 255);
    } 

    setLength(1+format.length());
    
    this.format = format.getBytes();
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeByte(format.length);
    los.write(format);
  }

}
