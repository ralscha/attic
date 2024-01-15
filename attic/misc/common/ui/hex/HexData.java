package common.ui.hex;

import java.io.*;

public interface HexData
{
  public int getRowCount();
  public int getColumnCount();
  public int getLastRowSize();
  
  public byte getByte(int row, int col);
  public void setByte(int row, int col, byte value);
  public byte[] getRow(int row);
}

