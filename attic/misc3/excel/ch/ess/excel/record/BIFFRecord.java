/* BIFFRecord.java
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


public abstract class BIFFRecord implements Comparable {

  private int length;
  private int identifier;

  private int order;

  public BIFFRecord(int identifier, int length, int order) {
    this.identifier = identifier;
    this.length = length;
    this.order = order;
  }

  public BIFFRecord(int identifier, int order) {
    this(identifier, 0, order);
  }

  public void writeRecord(LEDataOutputStream los) throws IOException {
    los.writeShort(identifier);
    los.writeShort(length);
    
    writeData(los);
  }

  public abstract void writeData(LEDataOutputStream los) throws IOException;

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getLength() {
    return this.length;
  }

  public int getIdentifier() {
    return this.identifier;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getOrder() {
    return order;
  }

  public int compareTo(Object o) {
    BIFFRecord record = (BIFFRecord)o;
    return order - record.getOrder();  
  }

}
