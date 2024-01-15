/* Border.java
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

import java.util.*;

public class FontAttributeSet {

  Set faSet = new HashSet();
  
  public FontAttributeSet add(FontAttribute fa) {
    faSet.add(fa);
    return this;
  }

  public int getAttributeValue() {
    int value = 0;
    Iterator it = faSet.iterator();
    while(it.hasNext()) {
      FontAttribute fa = (FontAttribute)it.next();
      value += fa.getAttributeValue();
    }
    return value;
  }
}
