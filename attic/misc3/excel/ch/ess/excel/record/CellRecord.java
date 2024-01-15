/* CellRecord.java
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

public abstract class CellRecord extends BIFFRecord {

  private final static byte[] EMPTY_ATTRIBUTES = new byte[]{(byte)0,(byte)0,(byte)0};

  private int length;
  private int identifier;
  private int row;
  private int col;
  private byte[] cellAttributes = new byte[3];

  public CellRecord(int identifier, int length, int row, int col, CellAttributes attributes) {
    super(identifier, length, 330);
    this.row = row;
    this.col = col;

    if (attributes != null) {
      byte[] tmp = attributes.getAttributes();
      cellAttributes[0] = tmp[0];
      cellAttributes[1] = tmp[1];
      cellAttributes[2] = tmp[2];
    } else {
      cellAttributes = EMPTY_ATTRIBUTES;    
    }
  }

  public void writeRecord(LEDataOutputStream los) throws IOException {
    los.writeShort(getIdentifier());
    los.writeShort(getLength());
    los.writeShort(row);
    los.writeShort(col);
    los.write(cellAttributes);

    writeData(los);
  }

}
