/* CalcModeAttribute.java
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

public class CalcMode {
  private final int attribute;

  private CalcMode(int attribute) {
    this.attribute = attribute;
  }

  public String toString() {
    return String.valueOf(attribute);
  }

  public int getAttributeValue() {
    return attribute;
  }

  public static final CalcMode MANUAL = new CalcMode(0);
  public static final CalcMode AUTOMATIC = new CalcMode(1);
  public static final CalcMode AUTOMATIC_NOTABLES = new CalcMode(-1);

}