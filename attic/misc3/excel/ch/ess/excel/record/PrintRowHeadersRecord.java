/* PrintRowHeadersRecord.java
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
PRINT ROW HEADERS record - print row headers flag (type = 42)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   fPrintRwCol  2  =1 if we should print row and
             column headers when printing
             the document
          =0 otherwise

  Description
   The PRINT ROW HEADERS record controls whether or not Excel
   prints row and column headers when printing the document.


*/

public class PrintRowHeadersRecord extends BIFFRecord {

  private boolean printHeaders;
  
  public PrintRowHeadersRecord(boolean printHeaders) {
    super(42, 2, 120);
    
    this.printHeaders = printHeaders;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    if (printHeaders) {
      los.writeShort(1);
    } else {
      los.writeShort(0);
    }
  }

}
