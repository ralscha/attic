/* PaneRecord.java
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
PANE Record - window split information (type = 65)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   x    2  horizontal position of the split,
          or zero if none
  6   y    2  vertical position of the split,
          or zero if none
  8   rwTop    2  top row visible in the bottom pane
  10  colLeft  2  leftmost column visible in the
          right pane
  12  pnnAct   1  pane number of the active pane

  Description
   The PANE record describes the number and position of panes in
   a window.  The x and y fields give the position of the
   vertical and horizontal splits, respectively, in units of
   1/20 of a point. Either of these fields may be zero,
   indicating that the window is not split in the corresponding
   direction.

   For a window with a horizontal split, rwTop is the topmost
   row visible in the bottom pane or panes.  For a window with a
   vertical split, colLeft gives the leftmost column visible in
   the right pane or panes.

   The pnnAct field tells which pane is the active pane.  It
   contains one of the following values:
      0   Bottom right
      1   Top right
      2   Bottom left
      3   Top left

   If the document window associated with a pane has frozen
   panes, as specified in the WINDOW2 record, then x and y have
   special meaning.  If there is a vertical split, then x
   contains the number of columns visible in the top pane. If
   there is a horizontal split, then y contains the number of
   rows visible in the left pane.  Both types of splits can be
   present in a window, as in unfrozen panes.
*/

public class PaneRecord extends BIFFRecord {


  private int x;
  private int y;
  private int rwTop;
  private int colLeft;
  private ActivePane activePane;
  
  public PaneRecord(int x, int y, int rwTop, int colLeft, ActivePane activePane) {
    super(65, 9, 400);

    this.x = x;
    this.y = y;
    this.rwTop = rwTop;
    this.colLeft  = colLeft;
    this.activePane = activePane;
  } 

  public void writeData(LEDataOutputStream los) throws IOException {    
    
    los.writeShort(x);
    los.writeShort(y);
    los.writeShort(rwTop);
    los.writeShort(colLeft);
    los.writeByte(activePane.getActivePaneValue());
        
  }

}
