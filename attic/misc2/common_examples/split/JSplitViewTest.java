package split;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import common.ui.split.*;

public class JSplitViewTest extends JPanel
{
  public JSplitViewTest()
    throws IOException
  {
    setLayout(new GridLayout());
    String text = readFile("JSplitViewTest.java");
    add(new JSplitView(text));
  }
  
  protected String readFile(String filename)
    throws IOException
  {
    FileReader file = new FileReader(filename);
    BufferedReader reader = new BufferedReader(file);
    StringWriter text = new StringWriter();
    PrintWriter writer = new PrintWriter(text);
    String line;
    while ((line = reader.readLine()) != null)
    {
      writer.println(line);
    }
    writer.close();
    reader.close();
    return text.toString();
  }
  
  public static void main(String[] args)
    throws IOException
  {
    JFrame frame = new JFrame("JSplitView Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JSplitViewTest());
    frame.setSize(500, 300);
    frame.show();
  }
}
