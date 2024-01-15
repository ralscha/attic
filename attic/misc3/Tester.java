// Copyright 1999 MageLang Institute
// $Id: //depot/main/src/edu/modules/Collections/magercises/WordCount/Solution/Tester.java#1 $
import java.io.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Tester {

  // Test program
  public static void main(String args[]) {
    JFrame frame = new JFrame("Word Count URL");
    frame.setDefaultCloseOperation(3);

    Container contentPane = frame.getContentPane();

    final JTextField textField = new JTextField("http://www.magelang.com");
    contentPane.add(textField, BorderLayout.NORTH);

    final JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    contentPane.add(scrollPane, BorderLayout.CENTER);

    final WordCount wordCount = new WordCount();

    ActionListener readURLAction = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        String urlString = textField.getText().trim();
        if (urlString.length() != 0) {
          try {
            URL url = new URL(urlString);
            wordCount.readURL(url);
            Map map = wordCount.getMap();
            String mapString = convertMap(map);
            textArea.setText(mapString);
          } catch (MalformedURLException mal) {
            textArea.setText("Bad URL: " + urlString);
          }
        }
      };
    };
    textField.addActionListener(readURLAction);

    JButton clearButton = new JButton("Clear");
    contentPane.add(clearButton, BorderLayout.SOUTH);

    ActionListener clearAction = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        textArea.setText("");
        wordCount.clear();
      }
    };
    clearButton.addActionListener(clearAction);

    frame.setSize(400, 250);
    frame.setVisible(true);
  }
  private static String convertMap(Map map) {
    StringWriter writer = new StringWriter();
    PrintWriter out = new PrintWriter(writer);

  	Map sortedMap = new TreeMap(new CaseInsensitiveComparator());
  	sortedMap.putAll(map);

  	Iterator it = sortedMap.keySet().iterator();
  	while(it.hasNext()) {
  		String key = (String)it.next();
  		out.print(key);
  		out.print("  ");
  		out.println(sortedMap.get(key));
  	}
    return writer.toString();
  }
}