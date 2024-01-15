package common.ui.imagemap;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;

/**
 * SUPPORTED EXAMPLE:
 * 
 * default http://www.java-pro.com/default.html
 * rect http://www.java-pro.com/rect.html 314,35,433,155
 * circ http://www.java-pro.com/circ.html 94,98,66
 * poly http://www.java-pro.com/poly.html 185,129,152,195,185,267,259,257,302,200,260,133,185,129
**/

public class ServerMapFile
  extends AbstractMapFile
{
  public ServerMapFile(String filename)
    throws IOException
  {
    super(filename);
  }
  
  public String getDelimiters()
  {
    return " \t\n\r";
  }

  public void parseShapes(List tokenList)
  {
    while (tokenList.size() > 0)
    {
      String token = popToken(tokenList);
      if (token.equalsIgnoreCase("default"))
      {
        parseDefault(tokenList);
      }
      if (token.equalsIgnoreCase("rect"))
      {
        parseRectangle(tokenList);
      }
      if (token.equalsIgnoreCase("circ"))
      {
        parseCircle(tokenList);
      }
      if (token.equalsIgnoreCase("poly"))
      {
        parsePolygon(tokenList);
      }
    }
  }
  
  public void parseDefault(List tokenList)
  {
    String url = popToken(tokenList);
    setDefaultAction(getActionFor(url));
  }
  
  public void parseRectangle(List tokenList)
  {
    String url = popToken(tokenList);
    int[] coord = parseCoordinates(popToken(tokenList));
    setShapeAction(new Rectangle2D.Float(
      coord[0], coord[1],
      coord[2] - coord[0],
      coord[3] - coord[1]),
      getActionFor(url));
  }
  
  public void parseCircle(List tokenList)
  {
    String url = popToken(tokenList);
    int[] coord = parseCoordinates(popToken(tokenList));
    setShapeAction(new Ellipse2D.Float(
      coord[0] - coord[2], coord[1] - coord[2],
      coord[2] * 2, coord[2] * 2),
      getActionFor(url));
  }
  
  public void parsePolygon(List tokenList)
  {
    String url = popToken(tokenList);
    int[] coord = parseCoordinates(popToken(tokenList));
    Polygon polygon = new Polygon();
    for (int i = 0; i < coord.length; i += 2)
    {
      polygon.addPoint(coord[i], coord[i + 1]);
    }
    setShapeAction(polygon, getActionFor(url));
  }
}

