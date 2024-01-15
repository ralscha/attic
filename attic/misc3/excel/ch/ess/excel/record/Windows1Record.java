/* Windows1Record.java
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
import ch.ess.excel.attribute.*;

/*
WINDOW1 record - basic window information (type = 61)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   x    2  horizontal position of the
          window
  6   y    2  vertical position of the window
  8   dx   2  width of the window
  10  dy   2  height of the window
  12  fHidden  1  =1 if the window is hidden
          =0 otherwise

  Description
   The WINDOW1 record provides basic information about an
   Excel window.  The x and y fields give the location of the
   window in units of 1/20 of a point, relative to the upper
   left corner of the desktop. dx and dy give the window size,
   also in units of 1/20 of a point.  fHidden is used to specify
   a hidden window.

   If you are creating a BIFF file, you can omit the WINDOW1
   record, and Excel will create a default window into your
   document.
*/

public class Windows1Record extends BIFFRecord {


  private int x;
  private int y;
  private int dx;
  private int dy;
  private boolean hidden;
  
  public Windows1Record(int x, int y, int dx, int dy, boolean hidden) {
    super(61, 9, 380);

    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy  = dy;
    this.hidden = hidden;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    
    los.writeShort(x);
    los.writeShort(y);
    los.writeShort(dx);
    los.writeShort(dy);

    if (hidden)
      los.writeByte(1);
    else
      los.writeByte(0);
        
  }

}
