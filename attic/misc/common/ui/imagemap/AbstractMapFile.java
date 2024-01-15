package common.ui.imagemap;

import java.io.*;
import java.awt.Shape;
import javax.swing.*;
import java.util.*;

public abstract class AbstractMapFile
  extends DefaultImageMap
{
/*
  DEFAULT - coordinates: none (client-side only)
  CIRCLE - coordinates: center, some edgepoint (server-side only)
  CIRCLE - coordinates: center, some radius (client-side only)
  POLY - at most 100 coordinate pairs
  RECT - coordinates: upper-left corner lower-right
  POINT - coordinate pair for the point (server-side only)
*/
  
  public AbstractMapFile(String filename)
    throws IOException
  {
    FileReader reader = new FileReader(filename);
    int count;
    char[] buffer = new char[1024];
    StringBuffer text = new StringBuffer();
    while ((count = reader.read(buffer)) > -1)
    {
      text.append(buffer, 0, count);
    }
    parseShapes(tokenize(text.toString()));
  }

  protected String peekToken(List tokenList)
  {
    String token = (String)tokenList.get(0);
    return token;
  }
  
  protected String popToken(List tokenList)
  {
    String token = (String)tokenList.get(0);
    tokenList.remove(0);
    return token;
  }
  
  public int[] parseCoordinates(String text)
  {
    StringTokenizer tokenizer =
      new StringTokenizer(text, "\t\n ,", false);
    int[] list = new int[tokenizer.countTokens()];
    for (int i = 0; i < list.length; i++)
    {
      String token = tokenizer.nextToken();
      //System.out.println(token);
      list[i] = Integer.parseInt(token);
    }
    return list;
  }
  
  public List tokenize(String text)
  {
    List list = new ArrayList();
    StringTokenizer tokenizer =
      new StringTokenizer(text, getDelimiters(), false);
    while (tokenizer.hasMoreTokens())
    {
      list.add(tokenizer.nextToken());
    }
    return list;
  }
  
  public String toString(List list)
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < list.size(); i++)
    {
      if (i > 0) buffer.append(";");
      buffer.append((String)list.get(i));
    }
    return buffer.toString();
  }

  public abstract String getDelimiters();
  public abstract void parseShapes(List tokenList);
  
  public Action getActionFor(String text)
  {
    return new PrintLineAction(text);
  }
}

