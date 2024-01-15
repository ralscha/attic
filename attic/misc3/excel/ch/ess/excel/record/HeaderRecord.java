/* HeaderRecord.java
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
HEADER record - print header string (type = 20)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   cch     1   length of string
  5   rgch    var  the string

  Description
   The HEADER record specifies a print header string for a
   document.  This string appears at the top of every page
   when the document is printed.
*/

public class HeaderRecord extends BIFFRecord {

  private byte[] header;
  
  public HeaderRecord(String header) {
    super(20, 190);

    if (header.length() > 255) {
      header = header.substring(0, 255);
    } 

    setLength(1+header.length());
    
    this.header = header.getBytes();
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeByte(header.length);
    los.write(header);
  }

}
