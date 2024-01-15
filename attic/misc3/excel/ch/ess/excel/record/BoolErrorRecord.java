/* BoolErrorRecord.java
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


import ch.ess.excel.attribute.*;
import java.io.*;

/*
BOOLERR record - cell with constant boolean or error (type = 5)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   rw   2  row
  6   col     2   column
  8   rgbAttr  3  cell attributes
  11  bBoolErr 1  boolean or error value
  12  fError   1  specifies boolean or error
          =1 for error
          =0 for boolean

  Description
   A BOOLERR record describes a cell containing a constant
   boolean or error value.

   Boolean values are 1 for TRUE and 0 or FALSE.
   Error values are as follows:
      0   #NULL!
      7   #DIV/0!
      15  #VALUE!
      23  #REF!
      29  #NAME?
      36  #NUM!
      42  #N/A
*/

public class BoolErrorRecord extends CellRecord {

  private boolean boolValue;
  private CellError errorValue;

  public BoolErrorRecord(int row, int col, CellError value) {
    this(row, col, value, null);
  }

  public BoolErrorRecord(int row, int col, boolean value) {
    this(row, col, value, null);
  }


  public BoolErrorRecord(int row, int col, boolean value, CellAttributes attributes) {
    super(5, 9, row, col, attributes);
    this.boolValue = value;
    this.errorValue = null;
  } 

  public BoolErrorRecord(int row, int col, CellError value, CellAttributes attributes) {
    super(5, 9, row, col, attributes);
    this.errorValue = value;
  } 


  public void writeData(LEDataOutputStream los) throws IOException {  
    if (errorValue != null) {
      los.writeByte(errorValue.getCellErrorValue());
      los.writeByte(1);        
    } else {
      if (boolValue) {
        los.writeByte(1);
      } else {
        los.writeByte(0);
      }
      los.writeByte(0);            
    }      
    
  }

}
