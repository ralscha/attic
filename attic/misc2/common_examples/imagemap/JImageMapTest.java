package imagemap;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import common.ui.imagemap.*;

public class JImageMapTest
{
  public static JImageMap createMap(ImageMap model)
  {
    ImageIcon stripesIcon = new ImageIcon("images/stipes.gif");
    ImageIcon twistsIcon = new ImageIcon("images/twists.gif");
    ImageIcon solidsIcon = new ImageIcon("images/solids.gif");
    Image stripes = stripesIcon.getImage();
    Image twists = twistsIcon.getImage();
    Image solids = solidsIcon.getImage();
    JImageMap map = new JImageMap(
      stripes, twists, solids, model);
    //map.setBorder(BorderFactory.
    //	createTitledBorder("JImageMap"));
    return map;
  }
  
  public static JTextArea createTextArea(String filename)
  {
    String text = "";
    try
    {
      text = readTextFile(filename);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    JTextArea textArea = new JTextArea(text);
    textArea.setBorder(BorderFactory.
      createEmptyBorder(2, 2, 2, 2));
    return textArea;
  }
  
  public static JPanel createPanel(
    JComponent north, JComponent center)
  {
    JPanel panel = new JPanel(new BorderLayout(4, 4));
    panel.add(BorderLayout.NORTH, north);
    panel.add(BorderLayout.CENTER, center);
    panel.setBorder(BorderFactory.
      createEmptyBorder(2, 2, 2, 2));
    return panel;
  }
  
  public static String readTextFile(String filename)
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
    return text.toString();
  }
    
  public static void main(String[] args)
    throws Exception
  {
    ImageMap clientMap = new ClientMapFile("imagemap/images/client.map");
    ImageMap serverMap = new ServerMapFile("imagemap/images/server.map");
    
    ImageMap defaultMap = new DefaultImageMap();
    Rectangle2D rect =
      new Rectangle2D.Float(228, 68, 148, 147);
    Ellipse2D circ = 
      new Ellipse2D.Float(427, 173, 160, 160);
    Polygon poly = new Polygon();
    poly.addPoint(106, 175);
    poly.addPoint(230, 290);
    poly.addPoint(112, 337);
    poly.addPoint(42, 324);
    poly.addPoint(106, 177);
    defaultMap.setDefaultAction(new PrintLineAction("Default"));
    defaultMap.setShapeAction(rect,
      new PrintLineAction("Rectangle"));
    defaultMap.setShapeAction(circ,
      new PrintLineAction("Circle"));
    defaultMap.setShapeAction(poly,
      new PrintLineAction("Polygon"));
    
    JTabbedPane tabs = new JTabbedPane();
    tabs.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    tabs.addTab("Client Map",  createPanel(
        createMap(clientMap),
        createTextArea("imagemap/images/client.map")));
    tabs.addTab("Server Map", createPanel(
        createMap(serverMap),
        createTextArea("imagemap/images/server.map")));
    tabs.addTab("Default Map", createPanel(
        createMap(defaultMap), new JPanel()));
    
    JFrame frame = new JFrame("JImageMap Test");
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(tabs);
    frame.pack();
    frame.show();
  }
}

