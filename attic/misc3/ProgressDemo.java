

import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class ProgressDemo {

  String startdir; // starting directory for search
  String patt; // pattern to search for
  JTextArea outarea; // output area for file pathnames
  JFrame frame; // frame
  JProgressBar progbar; // progress bar
  JLabel fileslab; // number of files found
  boolean search_flag; // true if search in progress

  // nested class used to do actual searching

  class Search extends Thread {

    // do GUI updates

    void doUpdate(Runnable r) {
      try {
        SwingUtilities.invokeAndWait(r);
      } catch (InvocationTargetException e1) {
        System.err.println(e1);
      }
      catch (InterruptedException e2) {
        System.err.println(e2);
      }
    }

    // get a list of all the files under a given directory

    void getFileList(File f, List list) {

      // recurse if a directory

      if (f.isDirectory()) {
        String entries[] = f.list();
        for (int i = 0; i < entries.length; i++) {
          getFileList(new File(f, entries[i]), list);
        }
      }

      // for plain files, add to list and
      // update progress bar

      else if (f.isFile()) {
        list.add(f.getPath());
        final int size = list.size();
        if (size % 100 != 0) {
          return;
        }
        doUpdate(new Runnable() {
                   public void run() {
                     progbar.setValue(size % 1000);
                   }
                 }
                );
      }
    }

    // check whether a file contains the specified pattern

    boolean fileMatch(String fn, String patt) {
      boolean found = false;

      try {
        java.io.FileReader fr = new java.io.FileReader(fn);
        java.io.BufferedReader br = new java.io.BufferedReader(fr);
        String str;
        while ((str = br.readLine()) != null) {
          if (str.indexOf(patt) != -1) {
            found = true;
            break;
          }
        }
        br.close();
      } catch (IOException e) {
        System.err.println(e);
      }

      return found;
    }

    // perform the search

    public void run() {
      List filelist = new ArrayList();
      final String sep = System.getProperty("line.separator");

      // clear old output

      doUpdate(new Runnable() {
                 public void run() {
                   outarea.setText("");
                   fileslab.setText("");
                 }
               }
              );

      // get the list of files and display a count

      getFileList(new File(startdir), filelist);
      final int size = filelist.size();
      doUpdate(new Runnable() {
                 public void run() {
                   progbar.setValue(0);
                   fileslab.setText("Found " + size + " files, now searching ...");
                 }
               }
              );

      // set up a progress monitor

      final ProgressMonitor pm =
        new ProgressMonitor(frame, "Searching files", "", 0, size - 1);
      pm.setMillisToDecideToPopup(0);
      pm.setMillisToPopup(0);

      // iterate across the files, updating
      // the progress monitor

      for (int i = 0; i < size; i++) {
        final String fn = (String) filelist.get(i);
        final int curr = i;
        if (pm.isCanceled()) {
          break;
        }
        final boolean b = fileMatch(fn, patt);
        doUpdate(new Runnable() {
                   public void run() {
                     pm.setProgress(curr);
                     pm.setNote(fn);
                   if (b) {
                       outarea.append(fn + sep);
                     }
                   }
                 }
                );
      }

      // close the progress monitor and
      // set the caret position in the output
      // area to the beginning of the file list

      doUpdate(new Runnable() {
                 public void run() {
                   pm.close();
                   outarea.setCaretPosition(0);
                   fileslab.setText("");
                 }
               }
              );

      search_flag = false;
    }
  }

  public ProgressDemo() {
    frame = new JFrame("ProgressDemo");

    // set up the window closer for the frame

    frame.addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent e) {
                                System.exit(0);
                              }
                            }
                           );

    // set up panels

    JPanel paneltop = new JPanel();
    JPanel panelbot = new JPanel();
    paneltop.setLayout(new GridLayout(5, 1));

    JPanel panel1 = new JPanel();
    panel1.add(new JLabel("Starting Directory"));
    final JTextField dirfield = new JTextField(20);
    panel1.add(dirfield);

    JPanel panel2 = new JPanel();
    panel2.add(new JLabel("Search Pattern"));
    final JTextField pattfield = new JTextField(20);
    panel2.add(pattfield);

    JPanel panel3 = new JPanel();
    JButton button = new JButton("Search");
    panel3.add(button);

    JPanel panel4 = new JPanel();
    progbar = new JProgressBar(0, 999);
    panel4.add(progbar);

    JPanel panel5 = new JPanel();
    fileslab = new JLabel();
    panel5.add(fileslab);

    JPanel panel6 = new JPanel();
    outarea = new JTextArea(8, 40);
    outarea.setEditable(false);
    JScrollPane jsp =
      new JScrollPane(outarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    panel6.add(jsp);

    // processing for "Search" button

    button.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                 startdir = dirfield.getText();
                                 patt = pattfield.getText();
                               if (startdir == null || startdir.trim().equals("") ||
                                     patt == null || patt.trim().equals("")) {
                                   JOptionPane.showMessageDialog(frame, "Invalid input", "Error",
                                                                 JOptionPane.ERROR_MESSAGE);
                               } else if (search_flag) {
                                   JOptionPane.showMessageDialog(frame, "Search in progress", "Error",
                                                                 JOptionPane.ERROR_MESSAGE);
                               } else {
                                   search_flag = true;
                                   new Search().start();
                                 }
                               }
                             }
                            );

    paneltop.add(panel1);
    paneltop.add(panel2);
    paneltop.add(panel3);
    paneltop.add(panel4);
    paneltop.add(panel5);

    panelbot.add(panel6);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    panel.add(paneltop);
    panel.add(panelbot);

    // display the frame

    frame.getContentPane().add(panel);
    frame.pack();
    frame.setLocation(200, 200);
    frame.setVisible(true);
  }

  public static void main(String args[]) {
    new ProgressDemo();
  }
}
