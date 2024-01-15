/* CellAttributes.java
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

/*
Cell Attributes
---------------
  This section describes the cell attribute field found in the ROW,
  BLANK, INTEGER, NUMBER, LABEL, BOOLERR, FORMULA, and COLUMN DEFAULT
  records. The field is three bytes long and consists of bit fields:

  Offset  Bits Mask   Name    Contents
  ------  ---- ----   ----    --------
  0   7   0x80 fHidden    =1 if the cell is hidden
      6  0x40 fLocked    =1 if the cell is locked
      5-0        RESERVED - must be zeros
  1   7-6  0xC0   ifnt    font number
      5-0 0x3F   ifmt    the cell's format code
  2   7   0x80 fShade     =1 if the cell is shaded
      6  0x40 fBottom    =1 if the cell has a  bottom border
      5  0x20 fTop    =1 if the cell has a top border
      4  0x10 fRight     =1 if the cell has a right border
      3  0x08 fLeft   =1 if the cell has a left border
      2-0 0x07   alc     the cell's alignment code

  The ifnt field is a zero-based index into the document's table of
  fonts. The ifmt field is a zero-based index into the document's table
  of picture formats.  See the FONT and FORMAT records for details.

  The alc field has one of the following values:
   0  General
   1  Left
   2  Center
   3  Right
   4  Fill
   7  (Multiplan only) Default alignment

*/

public class CellAttributes {
  
  private final static int SHADED = 0x80;
  private final static int HIDDEN = 0x80;
  private final static int LOCKED = 0x40;

  private final static int ALIGNMENT_COMP = ~0x07;
  private final static int FONT_COMP = ~0xC0;
  private final static int FORMAT_COMP = ~0x3F;
  private final static int BORDER_COMP = ~0xF8;

  private byte[] attributes;

  public CellAttributes() {
    attributes = new byte[3];
  }

  public void setHidden(boolean flag) {
    if (flag) {
      attributes[0] = (byte)(attributes[0] | HIDDEN);
    } else {
      attributes[0] = (byte)(attributes[0] & ~HIDDEN);
    }
  }

  public void setLocked(boolean flag) {
    if (flag) {
      attributes[0] = (byte)(attributes[0] | LOCKED);
    } else {
      attributes[0] = (byte)(attributes[0] & ~LOCKED);
    }  
  }

  public void setShaded(boolean flag) {
    if (flag) {
      attributes[2] = (byte)(attributes[2] | SHADED);
    } else {
      attributes[2] = (byte)(attributes[2] & ~SHADED);
    }    
  }

  public void setFontNumber(FontNumber fontNumber) { 
    attributes[1] = (byte)(attributes[1] & FONT_COMP);
    attributes[1] = (byte)(attributes[1] | fontNumber.getFontNumberValue());
  }

  public void setFormatCode(int formatCode) {
    attributes[1] = (byte)(attributes[1] & FORMAT_COMP);
    attributes[1] = (byte)(attributes[1] | formatCode);    
  }

  public void setBorder(Border border) {
    attributes[2] = (byte)(attributes[2] & BORDER_COMP);
    attributes[2] = (byte)(attributes[2] | border.getBorderValue());  
  }

  public void setBorder(BorderSet borderSet) {
    attributes[2] = (byte)(attributes[2] & BORDER_COMP);
    attributes[2] = (byte)(attributes[2] | borderSet.getBorderValue());    
  }
  
  public void setAlignment(Alignment alignment) {
    attributes[2] = (byte)(attributes[2] & ALIGNMENT_COMP);
    attributes[2] = (byte)(attributes[2] | alignment.getAlignmentValue());  
  }


  public void reset() {
    attributes[0] = (byte)0;
    attributes[1] = (byte)0;
    attributes[2] = (byte)0;
  }
  
  public byte[] getAttributes() {
    return attributes;
  }


}
