/* DefaultRowHeightRecord.java
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
DEFAULT ROW HEIGHT record - default row height (type = 37)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   miyRwGhost  2   default row height

  Description
   The DEFAULT ROW HEIGHT record specifies the height of all
   undefined rows in the document.  The miyRwGhost field
   contains the row height in units of 1/20 of a point.
   This record does not affect the row heights of any rows
   that are explicitly defined.
*/

public class DefaultRowHeightRecord extends BIFFRecord {

  private int miyRwGhost;
  
  public DefaultRowHeightRecord(int miyRwGhost) {
    super(37, 2, 160);
    
    this.miyRwGhost = miyRwGhost;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(miyRwGhost);
  }

}
