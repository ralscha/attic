
import java.awt.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import com.taligent.widget.*;
import com.taligent.util.*;
import com.bdnm.awt.*;

public class LottoWin extends Applet
{
    int i,j;
    MultiColumnListbox listbox = new MultiColumnListbox();
    MultiColumnListbox rlbox   = new MultiColumnListbox();
    Integer ia[] = new Integer[6];
    Object ro[]  = new Object[6];
    Button addButton, deleteButton, clearButton, startButton;
    TextField tf[] = new TextField[6];
    int selectedIndex, fromIndex, toIndex;
    ComponentList cl;
    boolean selected = false;

    Choice fromChoice, toChoice;

    Vector ziehungen;
    Vector tips;
    int zahlen[] = new int[6];
    Ziehung z;

    Enumeration e;
    Tip t;
    int win[] = new int[5];

    OkDialog okd;

    public void init()
    {
        super.init();

        cl = new ComponentList(6);
        loadData();
        tips = new Vector();
        fromIndex = toIndex = 0;

        setBackground(Color.lightGray);

        FractionalLayout fLay = new FractionalLayout();
        setLayout(fLay);



        BorderPanel lp = new BorderPanel(BorderPanel.OUT);
        lp.setBackground(Color.lightGray);
        lp.setLayout(new BorderLayout());

        listbox.showHorizontalSeparator();
        listbox.showVerticalSeparator();
        for (int i = 0; i < 6; i++)
            listbox.addColumn("#"+(i+1));


        for (int i = 0; i < 6; i++)
            listbox.getColumnInfo(i).setWidth(30).setAlignment(ListboxColumn.RIGHT).setResizable(false);


        listbox.showBorder();
        lp.add("Center", listbox);

        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        clearButton = new Button("Clear");

        Panel np = new Panel();
        np.setLayout(new GridLayout(1, 6, 5, 0));
        for (i = 0; i < 6; i++)
        {
            tf[i] = new TextField(2);
            cl.addComponent(tf[i]);
            np.add(tf[i]);
        }

        Panel bp = new Panel();
        bp.setLayout(new FlowLayout(FlowLayout.LEFT));
        bp.add(addButton);
        bp.add(deleteButton);
        bp.add(clearButton);

        cl.addComponent(addButton);
        cl.addComponent(deleteButton);
        cl.addComponent(clearButton);

        Panel ap = new Panel();
        ap.setLayout(new BorderLayout());
        ap.add("Center", np);
        ap.add("South", bp);

        lp.add("South", ap);

        fLay.setConstraint(lp, new FrameConstraint(0.0,0, 0.00,0, 0.48,0, 0.6,0));
        add(lp);


        BorderPanel rp = new BorderPanel(BorderPanel.OUT);
        rp.setBackground(Color.lightGray);
        rp.setLayout(new BorderLayout());

        fromChoice = new Choice();
        toChoice   = new Choice();

        e = ziehungen.elements();
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

        Panel cp = new Panel();
        cp.setLayout(new BorderLayout());

        cp.add("North", new Label("Vergleichen mit Ausspielungen", Label.CENTER));

        Panel cap = new Panel();
        cap.setLayout(new GridLayout(2,1,0,0));
        Panel c1p = new Panel();
        c1p.setLayout(new FlowLayout(FlowLayout.CENTER));
        c1p.add(new Label("VON "));
        c1p.add(fromChoice);

        Panel c2p = new Panel();
        c2p.setLayout(new FlowLayout(FlowLayout.CENTER));
        c2p.add(new Label("BIS "));
        c2p.add(toChoice);
        cap.add(c1p);
        cap.add(c2p);

        cp.add("Center", cap);

        Panel c3p = new Panel();
        c3p.setLayout(new FlowLayout(FlowLayout.CENTER));
        startButton = new Button("Vergleich starten");
        c3p.add(startButton);
        cp.add("South", c3p);

        rp.add("North", cp);

        fLay.setConstraint(rp, new FrameConstraint(0.48,0, 0.00,0, 1.0,0, 0.6,0));
        add(rp);


        rlbox.showHorizontalSeparator();
        rlbox.showVerticalSeparator();
        rlbox.addColumn("Ausspielung");
        rlbox.addColumn("6 R");
        rlbox.addColumn("5+R");
        rlbox.addColumn("5 R");
        rlbox.addColumn("4 R");
        rlbox.addColumn("3 R");

        rlbox.getColumnInfo(0).setWidth(160).setAlignment(ListboxColumn.LEFT).setResizable(true);
        for (i = 1; i < 6; i++)
            rlbox.getColumnInfo(i).setWidth(50).setAlignment(ListboxColumn.CENTER).setResizable(false);

        rlbox.showBorder();

        fLay.setConstraint(rlbox, new FrameConstraint(0.0,0, 0.60,0, 1.0,0, 1.0,0));
        add(rlbox);


    }

    public void start()
    {
        tf[0].requestFocus();
    }

    public boolean action(Event evt, Object arg)
    {

        if (evt.target == clearButton)
        {
            listbox.clear();
            tips.removeAllElements();
            return true;
        }
        else if (evt.target == deleteButton)
        {
            if (selected)
            {
                listbox.deleteRow(selectedIndex);
                tips.removeElementAt(selectedIndex);
                selected = false;
            }
            return true;
        }
        else if (evt.target == addButton)
        {
            try
            {
                for (i = 0; i < 6; i++)
                {
                    ia[i] = new Integer(tf[i].getText());
                    zahlen[i] = ia[i].intValue();

                    if ((zahlen[i] < 1) || (zahlen[i] > 45))
                    {
                        okd = new OkDialog("Eingabefehler", "Nur Zahlen zwischen 1 und 45 eingeben");
                        okd.show();
                        tf[0].requestFocus();
                        return true;
                    }
                }



            	for (i = 0; i < 6; i++)
    	        {
                	for (j = i+1; j < 6; j++)
        		    	if (zahlen[i] == zahlen[j])
        		    	{
                            okd = new OkDialog("Eingabefehler", "Zahl doppelt eingegeben");
                            okd.show();
                            tf[0].requestFocus();
                            return true;
        			    }
        		}


                listbox.addRow(ia);
                listbox.repaint();

                Tip t = new Tip(0, zahlen);
                tips.addElement(t);

                for (i = 0; i < 6; i++)
                    tf[i].setText("");

                tf[0].requestFocus();
            }
            catch (NumberFormatException  nfe)
            {
                okd = new OkDialog("Eingabefehler", "Nur Zahlen zwischen 1 und 45 eingeben");
                okd.show();
            }
            return true;

        }
        else if (evt.target == startButton)
        {
            int a,b;
            boolean won;


            rlbox.clear();

            if (fromIndex > toIndex)
            {
                a = toIndex;
                b = fromIndex;
            }
            else
            {
                a = fromIndex;
                b = toIndex;
            }

            for (i = a; i <= b; i++)
            {
                won = false;

                for (j = 0; j < 5; j++)
                    win[j] = 0;

                z = (Ziehung)ziehungen.elementAt(i);
                ro[0] = z.toString();

                e = tips.elements();
                while(e.hasMoreElements())
                {
                    t = (Tip)e.nextElement();

                    switch(z.getWin(t.zahlen))
                    {
                        case 6 : win[0]++; break;
                        case 51: win[1]++; break;
                        case 5 : win[2]++; break;
                        case 4 : win[3]++; break;
                        case 3 : win[4]++; break;
                    }
                }
                for (j = 0; j < 5; j++)
                {
                    if (win[j] != 0)
                    {
                        won = true;
                        ro[j+1] = new Integer(win[j]);
                    }
                    else
                    {
                        ro[j+1] = new String("");
                    }
                }

                if (won)
                    rlbox.addRow(ro);
            }
            rlbox.repaint();

            if (rlbox.countRows() == 0)
            {
                okd = new OkDialog("Leider nichts gewonnen");
                okd.show();
            }

            return true;
        }
        else if (evt.target instanceof Choice)
        {
            if (evt.target == fromChoice)
                fromIndex = ((Choice)evt.target).getSelectedIndex();
            else
                toIndex = ((Choice)evt.target).getSelectedIndex();
            return true;
        }

        return false;
    }


    public boolean handleEvent(Event evt)
    {
        Component help;

        if (evt.target == listbox)
        {
            switch (evt.id)
            {
                case Event.LIST_SELECT:
                    selectedIndex = ((Integer)evt.arg).intValue();
                    selected = true;
                    break;
                case Event.LIST_DESELECT:
                    selectedIndex = ((Integer)evt.arg).intValue();
                    selected = true;
                    break;
            }
            return true;
        }
        if (evt.key == 10 && evt.id == Event.KEY_PRESS)
        {
            if (evt.target == addButton)
            {
                postEvent(new Event(addButton, Event.ACTION_EVENT,
                                    addButton.getLabel()));
            }
            else if (evt.target == deleteButton)
            {
                postEvent(new Event(deleteButton, Event.ACTION_EVENT,
                                    deleteButton.getLabel()));
            }
            else if (evt.target == clearButton)
            {
                postEvent(new Event(clearButton, Event.ACTION_EVENT,
                                    clearButton.getLabel()));
            }
        }
        else if (evt.key == 9 && evt.id == Event.KEY_PRESS)
        {
            if (evt.shiftDown())
                help = cl.prevComponent(evt.target);
            else
                help = cl.nextComponent(evt.target);

            help.requestFocus();
            if (help instanceof TextField)
                ((TextField)help).selectAll();
        }

        return super.handleEvent(evt);
    }


    public void loadData()
    {
        Ziehung zie;
        int nr, jahr, t, m, j, zz;
        int z[] = new int[6];

        ziehungen = new Vector();

        try
        {
            URL pal = new URL(getDocumentBase(), "win.data");
            String inputLine;

            DataInputStream dis = new DataInputStream(new BufferedInputStream(pal.openStream()));
            try
            {
                while (true)
                {
                    nr   = dis.readInt();
                    jahr = dis.readInt();
                    t    = dis.readInt();
                    m    = dis.readInt();
                    j    = dis.readInt();
                    for (int i = 0; i < 6; i++)
                        z[i] = dis.readInt();
                    zz   = dis.readInt();
                    zie = new Ziehung(nr, jahr, t, m, j, z, zz);
                    ziehungen.addElement(zie);
                }
            }
            catch (EOFException e) { }
            dis.close();

        }
        catch (MalformedURLException me)
        {
            System.out.println("MalformedURLException: " + me);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }
    }


}
