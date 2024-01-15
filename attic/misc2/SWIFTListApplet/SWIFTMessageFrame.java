import java.awt.*;
import java.awt.event.*;
import COM.taligent.widget.*;
import COM.taligent.widget.event.*;
import COM.taligent.util.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.text.*;


public class SWIFTMessageFrame extends Frame
{
    private TextArea textArea;

    public SWIFTMessageFrame(Vector msg, String TOSN)
    {
        super("SWIFT Message "+TOSN);
        addWindowListener(new Exit());
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        textArea = new TextArea();
        textArea.setFont(new Font("Courier", Font.PLAIN, 12));
        textArea.setEditable(false);
        add("Center", textArea);

        setText(msg, TOSN);

        setSize(400,250);
        showMsg();
    }

    public void setText(Vector msg, String TOSN)
    {
        textArea.setText("");
        setTitle("SWIFT Message "+TOSN);

        Enumeration e = msg.elements();
        while(e.hasMoreElements())
        {
            textArea.append((String)e.nextElement());
            textArea.append("\n");
        }
    }

    public void showMsg()
    {
        setVisible(true);
    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            setVisible(false);
        }
    }



}