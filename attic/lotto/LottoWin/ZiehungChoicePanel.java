
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import COM.taligent.widget.*;
import com.bdnm.awt.*;

public class ZiehungChoicePanel extends BorderPanel
{
    private Choice fromChoice, toChoice;
    private int toIndex, fromIndex;
    private Vector ziehungChoiceListeners;

    public ZiehungChoicePanel(Vector ziehungen)
    {
        super(BorderPanel.OUT);

        fromIndex = toIndex = 0;
        Ziehung z;
        ziehungChoiceListeners = new Vector();
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        fromChoice = new Choice();
        toChoice   = new Choice();

        fromChoice.setFont(new Font("Courier", Font.PLAIN, 12));
        toChoice.setFont(new Font("Courier", Font.PLAIN, 12));

        Enumeration e = ziehungen.elements();
        while(e.hasMoreElements())
        {
            toIndex++;
            fromIndex++;
            z = (Ziehung)e.nextElement();
            fromChoice.addItem(z.toString());
            toChoice.addItem(z.toString());
        }
        toIndex--;
        fromIndex--;

        fromChoice.select(fromIndex);
        toChoice.select(toIndex);

        fromChoice.addItemListener(new ItemListener()
                                    {
                                        public void itemStateChanged(ItemEvent i )
                                        {
                                            fromIndex = fromChoice.getSelectedIndex();
                                        }
                                    });


        toChoice.addItemListener(new ItemListener()
                                    {
                                        public void itemStateChanged(ItemEvent i)
                                        {
                                            toIndex = toChoice.getSelectedIndex();
                                        }
                                    });

        Panel cp = new Panel();
        cp.setLayout(new BorderLayout());
        cp.add("North", new Label("Vergleichen mit Ausspielungen", Label.CENTER));

        Panel cap = new Panel();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        cap.setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;

        c.weighty = 0.0;
        c.weightx = 1.0;

        c.gridwidth = GridBagConstraints.REMAINDER;
        Label vonLabel = new Label("VON", Label.CENTER);
        gridbag.setConstraints(vonLabel, c);
        cap.add(vonLabel);

        gridbag.setConstraints(fromChoice, c);
        cap.add(fromChoice);

        Label bisLabel = new Label("BIS", Label.CENTER);

        gridbag.setConstraints(bisLabel, c);
        cap.add(bisLabel);

        gridbag.setConstraints(toChoice, c);
        cap.add(toChoice);

        cp.add("Center", cap);

        Panel c3p = new Panel();
        c3p.setLayout(new FlowLayout(FlowLayout.CENTER));
        Button startButton = new Button("Vergleich starten");


        startButton.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent a)
                                        {
                                            notifyListeners();
                                        }
                                    });



        c3p.add(startButton);
        cp.add("South", c3p);

        add("Center", cp);

    }

    public void select(int from, int to)
    {
        fromChoice.select(from);
        fromIndex = from;
        toChoice.select(to);
        toIndex = to;
    }

    public void updateChoice(Vector ziehungen)
    {
        Ziehung z;
        fromChoice.removeAll();
        toChoice.removeAll();

        fromIndex = toIndex = 0;
        Enumeration e = ziehungen.elements();
        while(e.hasMoreElements())
        {
            toIndex++;
            fromIndex++;
            z = (Ziehung)e.nextElement();
            fromChoice.addItem(z.toString());
            toChoice.addItem(z.toString());
        }
        toIndex--;
        fromIndex--;

        fromChoice.select(fromIndex);
        toChoice.select(toIndex);
    }

    public synchronized void addZiehungChoiceListener(ZiehungChoiceListener listener)
    {
        ziehungChoiceListeners.addElement(listener);
    }

    public synchronized void removeZiehungChoiceListener(ZiehungChoiceListener listener)
    {
        ziehungChoiceListeners.removeElement(listener);
    }


    void notifyListeners()
    {
        Vector thisList = new Vector();
        ZiehungChoiceEvent thisEvent = new ZiehungChoiceEvent(this, fromIndex, toIndex);

        synchronized(this)
        {
            thisList = (Vector)ziehungChoiceListeners.clone();
        }

        for (int elem = 0; elem < thisList.size(); elem++)
        {
            ((ZiehungChoiceListener)thisList.elementAt(elem)).actionPerformed(thisEvent);
        }
    }


}


