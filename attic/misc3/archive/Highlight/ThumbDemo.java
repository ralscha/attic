import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class ThumbDemo extends JxFrame {
    JTextPane textPane;
    LimitedStyledDocument lsDoc;
    static final int MAX_CHARACTERS = 10000;
    Mediator med;
    JTextField txt;

    public ThumbDemo() {
        //some initial setup
        super("Text Highlighting Demo");
        
        med = new Mediator();
        InputFile f = new InputFile("conv1.txt");
        String s = f.readLine();
        while (s != null) {
            parseTokens(s);
            s = f.readLine();
        }
        f.close();

        
        //Create the document for the text area.
        lsDoc = new LimitedStyledDocument(MAX_CHARACTERS, med);
        
        //Create the text pane and configure it.
        textPane = new JTextPane(lsDoc);  
        lsDoc.setTextPane(textPane);
        textPane.setEditable (false); 
        textPane.addMouseListener(new MListen());
        
        textPane.setMargin(new Insets(5,5,5,5));
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JPanel jp = new JPanel();
        jp.setLayout (new  BorderLayout());
        jp.add("Center", scrollPane);
        
        //add text panel to show selected text
        txt = new JTextField(20);
        jp.add("South", txt);
        setContentPane(jp);  

        //Put the initial text into the text pane.
        initDocument(); 
        pack();
       
        setVisible(true);
    }

    //-------------------------------
    private void parseTokens(String s) {
        
        if(s.length() == 0)
            med.add(new Word("\n"));
        else {
        StringTokenizer tok = new StringTokenizer(s);
        while(tok.hasMoreTokens ()) {
            Word w = new Word(tok.nextToken ());
            med.add (w);
            
            }
        }
    }
    //-------------------------------
    protected void initDocument() {
        
        for (int i = 0; i < med.size(); i ++) {
                lsDoc.insertString(med.get(i));
        }
    }
    //------------------------------------------ 
    //The standard main method.
    public static void main(String[] args) {
        new ThumbDemo();
    }

//==============
    class MListen extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Point pt = new Point(e.getX(), e.getY());
            int offset = textPane.viewToModel (pt);
            Word w = med.findLastWord (offset);
            txt.setText (w.getWord ());           
        }
    }

}

