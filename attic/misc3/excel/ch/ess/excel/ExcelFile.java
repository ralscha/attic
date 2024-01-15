/* ExcelFile.java
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

package ch.ess.excel;

import ch.ess.excel.record.*;
import ch.ess.excel.attribute.*;

import java.util.*;
import java.io.*;

public class ExcelFile {

  private String fileName;
  private OutputStream os;
  private List recordList;

  private int firstRow;
  private int firstCol;
  private int lastRow;
  private int lastCol;

  private List horizPageBreakList;
  private List vertPageBreakList;

  public ExcelFile(String fileName) {
    this.fileName = fileName;
    this.os = null;
    init();
  }

  public ExcelFile(OutputStream os) {
    this.fileName = null;
    this.os = os;
    init();
  }

  private void init() {
    recordList = new ArrayList();
    addRecord(new BOFRecord());

    horizPageBreakList = new ArrayList();
    vertPageBreakList = new ArrayList();
  }

  private void addRecord(BIFFRecord record) {
    recordList.add(record);
  }

  public void insertHorizPageBreak(int row) {
    horizPageBreakList.add(new Integer(row));
  }

  public void insertVerticalPageBreak(int col) {
    vertPageBreakList.add(new Integer(col));
  }

  public void addFormat(String format) {
    addRecord(new FormatRecord(format));
  }

  /* writeValue Methods */

  public void writeValue(int row, int col, long value) {
    writeValue(row, col, value, null);
  }

  public void writeValue(int row, int col, double value) {
    writeValue(row, col, value, null);
  }

  public void writeValue(int row, int col, boolean value) {
    writeValue(row, col, value, null);
  }

  public void writeValue(int row, int col, CellError error) {
    writeValue(row, col, error, null);
  }

  public void writeValue(int row, int col, long value, CellAttributes attributes) {
    updateDimension(row, col);

    if ((value >= 0) && (value <= 65535))
      addRecord(new IntegerRecord(row, col, (short) value, attributes));
    else
      addRecord(new NumberRecord(row, col, value, attributes));
  }

  public void writeValue(int row, int col, double value, CellAttributes attributes) {
    updateDimension(row, col);

    addRecord(new NumberRecord(row, col, value, attributes));
  }

  public void writeValue(int row, int col, boolean value, CellAttributes attributes) {
    updateDimension(row, col);

    addRecord(new BoolErrorRecord(row, col, value, attributes));
  }

  public void writeValue(int row, int col, CellError error, CellAttributes attributes) {
    updateDimension(row, col);

    addRecord(new BoolErrorRecord(row, col, error, attributes));
  }

  public void writeValue(int row, int col, String value) {
    writeValue(row, col, value, null);
  }

  public void writeValue(int row, int col, String value, CellAttributes attributes) {

    updateDimension(row, col);

    if (value != null)
      addRecord(new LabelRecord(row, col, value, attributes));
    else
      addRecord(new BlankRecord(row, col, attributes));
  }



  public void setMargin(Margin type, double marginInInches) {
    if (type == Margin.LEFT) {
      addRecord(new LeftMarginRecord(marginInInches));
    } else if (type == Margin.RIGHT) {
      addRecord(new RightMarginRecord(marginInInches));
    } else if (type == Margin.TOP) {
      addRecord(new TopMarginRecord(marginInInches));
    } else if (type == Margin.BOTTOM) {
      addRecord(new BottomMarginRecord(marginInInches));
    }

  }

  public void setColumnWidth(int firstCol, int lastCol, int width) {
    addRecord(new ColWidthRecord(firstCol, lastCol, width * 256));
  }

  public void addFont(String fontName, int fontHeight, FontAttribute attribute) {
    addRecord(new FontRecord(fontName, fontHeight * 20, attribute));
  }

  public void addFont(String fontName, int fontHeight, FontAttributeSet attribute) {
    addRecord(new FontRecord(fontName, fontHeight * 20, attribute));
  }

  public void setHeader(String header) {
    addRecord(new HeaderRecord(header));
  }

  public void setFooter(String footer) {
    addRecord(new FooterRecord(footer));
  }

  public void setPrintGridLines(boolean printGridLines) {
    addRecord(new PrintGridLinesRecord(printGridLines));
  }

  public void setProtectSpreadsheet(boolean protect) {
    addRecord(new ProtectRecord(protect));
  }

  public void close() throws IOException {

    //Horizontal Page Break
    Collections.sort(horizPageBreakList);
    if (!horizPageBreakList.isEmpty()) {
      int[] hpb = new int[horizPageBreakList.size()];
      for (int i = 0; i < horizPageBreakList.size(); i++) {
        hpb[i] = ((Integer) horizPageBreakList.get(i)).intValue();
      }
      addRecord(new HorizontalPageBreaksRecord(hpb));
    }

    //Vertical Page Break
    Collections.sort(vertPageBreakList);
    if (!vertPageBreakList.isEmpty()) {
      int[] hpb = new int[vertPageBreakList.size()];
      for (int i = 0; i < vertPageBreakList.size(); i++) {
        hpb[i] = ((Integer) vertPageBreakList.get(i)).intValue();
      }
      addRecord(new VerticalPageBreaksRecord(hpb));
    }


    addRecord(new EOFRecord());
    addRecord(new DimensionRecord(firstRow, lastRow + 1, firstCol, lastCol + 1));

    DataOutputStream dos = null;
    if (fileName != null) {
      dos = new DataOutputStream(
              new BufferedOutputStream(new FileOutputStream(fileName)));
    } else {
      dos = new DataOutputStream(os);
    }

    LEDataOutputStream los = new LEDataOutputStream(dos);

    Collections.sort(recordList);

    Iterator it = recordList.iterator();
    while (it.hasNext()) {
      BIFFRecord record = (BIFFRecord) it.next();
      record.writeRecord(los);
    }

    los.close();

  }

  private void updateDimension(int row, int col) {

    firstRow = Math.min(firstRow, row);
    lastRow = Math.max(lastRow, row);

    firstCol = Math.min(firstCol, col);
    lastCol = Math.max(lastCol, col);
  }

}
