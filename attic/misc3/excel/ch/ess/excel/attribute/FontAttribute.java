/* FontAttribute.java
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

public class FontAttribute {
  private final int attribute;

  private FontAttribute(int attribute) {
    this.attribute = attribute;
  }

  public String toString() {
    return String.valueOf(attribute);
  }

  public int getAttributeValue() {
    return attribute;
  }

  public static final FontAttribute NO_FORMAT = new FontAttribute(0);
  public static final FontAttribute BOLD = new FontAttribute(1);
  public static final FontAttribute ITALIC = new FontAttribute(2);
  public static final FontAttribute UNDERLINE = new FontAttribute(4);
  public static final FontAttribute STRIKEOUT = new FontAttribute(8);


}