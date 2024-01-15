/* FooterRecord.java
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
FOOTER record - print footer string (type = 21)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   cch     1   length of string
  5   rgch    var  the string

  Description
   The FOOTER record specifies a print footer string for a
   document.  This string appears at the bottom of every
   page when the document is printed.
*/

public class FooterRecord extends BIFFRecord {

  private byte[] footer;
  
  public FooterRecord(String footer) {
    super(21, 200);

    if (footer.length() > 255) {
      footer = footer.substring(0, 255);
    } 

    setLength(1+footer.length());
    
    this.footer = footer.getBytes();
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeByte(footer.length);
    los.write(footer);
  }

}
