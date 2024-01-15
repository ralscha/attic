/* FontNumber.java
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

package ch.ess.excel.attribute;

public class FontNumber {
  private final int number;

  private FontNumber(int number) {
    this.number = number;
  }

  public String toString() {
    return String.valueOf(number);
  }

  public int getFontNumberValue() {
    return number;
  }

  public static final FontNumber FONT0 = new FontNumber(0);
  public static final FontNumber FONT1 = new FontNumber(64);
  public static final FontNumber FONT2 = new FontNumber(128);
  public static final FontNumber FONT3 = new FontNumber(192);
 
}