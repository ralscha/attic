package common.ui.imagemap;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;

/**
 * SUPPORTED EXAMPLE:
 * 
 * <AREA Shape="Polygon"
 *   coords = "185,129,152,195,185,267,259,257,302,200,260,133,185,129"
 *   href="http://www.java-pro.com/polygon.html">
 * <AREA Shape="Circle"
 *   coords = "94,98,66"
 *   href="http://www.java-pro.com/circle.html">
 * <AREA Shape="Rect"
 *   coords = "314,35,433,155"
 *   href="http://www.java-pro.com/rect.html">
**/

public class ClientMapFile
  extends AbstractMapFile
{
  public ClientMapFile(String filename)
    throws IOException
  {
    super(filename);
  }

  public String getDelimiters()
  {
    return " \t\n\r\"'=<>";
  }

  public void parseShapes(List tokenList)
  {
    while (tokenList.size() > 0)
    {
      popToken(tokenList); // AREA
      popToken(tokenList); // Shape
      String token = popToken(tokenList);
      if (token.equalsIgnoreCase("rect"))
      {
        parseRectangle(tokenList);
      }
      if (token.equalsIgnoreCase("circle"))
      {
        parseCircle(tokenList);
      }
      if (token.equalsIgnoreCase("polygon"))
      {
        parsePolygon(tokenList);
      }
    }
  }
  
  public void parseRectangle(List tokenList)
  {
    popToken(tokenList); // Coords
    int[] coord = parseCoordinates(popToken(tokenList));
    String url = parseURL(tokenList);
    setShapeAction(new Rectangle2D.Float(
      coord[0], coord[1],
      coord[2] - coord[0],
      coord[3] - coord[1]),
      getActionFor(url));
  }
  
  public void parseCircle(List tokenList)
  {
    popToken(tokenList); // Coords
    int[] coord = parseCoordinates(popToken(tokenList));
    String url = parseURL(tokenList);
    setShapeAction(new Ellipse2D.Float(
      coord[0] - coord[2], coord[1] - coord[2],
      coord[2] * 2, coord[2] * 2),
      getActionFor(url));
  }
  
  public void parsePolygon(List tokenList)
  {
    popToken(tokenList); // Coords
    int[] coord = parseCoordinates(popToken(tokenList));
    Polygon polygon = new Polygon();
    for (int i = 0; i < coord.length; i += 2)
    {
      polygon.addPoint(coord[i], coord[i + 1]);
    }
    String url = parseURL(tokenList);
    setShapeAction(polygon, getActionFor(url));
  }
  
  public String parseURL(List tokenList)
  {
    if (tokenList.size() < 2) return "";
    if (peekToken(tokenList).equalsIgnoreCase("href"))
    {
      popToken(tokenList);
      return popToken(tokenList);
    }
    return "";
  }
}

