/* ActivePane.java
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

public class ActivePane {
  private final int activePane;

  private ActivePane(int activePane) {
    this.activePane = activePane;
  }

  public String toString() {
    return String.valueOf(activePane);
  }

  public int getActivePaneValue() {
    return activePane;
  }

  public static final ActivePane BOTTOM_RIGHT = new ActivePane(0);
  public static final ActivePane TOP_RIGHT = new ActivePane(1);
  public static final ActivePane BOTTOM_LEFT = new ActivePane(2);
  public static final ActivePane TOP_LEFT = new ActivePane(3);
 
}