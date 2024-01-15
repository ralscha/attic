/* FontRecord.java
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
FONT record - document font (type = 49)
  Offset  Name    Size Contents
  ------  ----    ---- --------
  4   dy   2  height of the font
  6   grbit    2  font attributes
  8   cch     1   length of font name
  9   rgch    var  the font name

  Description
   The FONT record describes an entry in the Excel document's
   font table.  There are up to four different fonts on an Excel
   document, numbered 0 to 3.  FONT records are read into the
   font table in the order in which they are encountered in the
   BIFF file.

   The dy field gives the height of the font in units of 1/20
   of a point.  grbit contains the font attributes as follows:

  Offset  Bits Mask   Name    Contents
  ------  ---- ----   ----    --------
  0   7-0  0xFF       RESERVED - must be zeros
  1   7-4  0xF0       RESERVED - must be zeros
      3  0x08 fStrikeout  =1 if the font is struck out
      2  0x04 fUnderline  =1 if the font is underlined
      1  0x02 fItalic    =1 if the font is italic
      0  0x01 fBold   =1 if the font is bold

   cch and rgch contain the font's face name.

*/

public class FontRecord extends BIFFRecord {

  private byte[] fontName;
  private int fontHeight;
  private int attributes;

  public FontRecord(String fontName, int height, int attributes) {
    super(49, 5+fontName.length(), 170);
    
    this.fontName = fontName.getBytes();
    this.fontHeight = height;
    this.attributes = attributes;
  } 

  public FontRecord(String fontName, int height, FontAttribute attributes) {
    this(fontName, height, attributes.getAttributeValue());
  } 

  public FontRecord(String fontName, int height, FontAttributeSet attributes) {
    this(fontName, height, attributes.getAttributeValue());
  } 
 

  public void writeData(LEDataOutputStream los) throws IOException {    
    los.writeShort(fontHeight);
    los.writeShort(attributes);
    los.writeByte(fontName.length);
    los.write(fontName);
  }

}
