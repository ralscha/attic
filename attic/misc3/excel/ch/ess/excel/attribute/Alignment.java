/* Alignment.java
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

public class Alignment {
  private final int alignment;

  private Alignment(int alignment) {
    this.alignment = alignment;
  }

  public String toString() {
    return String.valueOf(alignment);
  }

  public int getAlignmentValue() {
    return alignment;
  }

  public static final Alignment GENERAL = new Alignment(0);
  public static final Alignment LEFT = new Alignment(1);
  public static final Alignment CENTER = new Alignment(2);
  public static final Alignment RIGHT = new Alignment(3);
  public static final Alignment FILL = new Alignment(4);
}