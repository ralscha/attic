
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import COM.taligent.widget.*;
import com.bdnm.awt.*;
import COM.writeme.guyrice.awt.*;
import lotto.html.*;

public class LottoWin extends Frame
{
    int i,j;
    List listbox = new List();
    List rlbox   = new List();
    int lastfrom = -1;
    int lastto   = -1;

    Button addButton, deleteButton, clearButton;
    TextField tf[] = new TextField[6];
    int selectedIndex;
    ZiehungChoicePanel zcp;
    JokerPanel jokerPanel;

    boolean selected = false;
    String wintext[] = {"3 R", "4 R", "5 R", "5+R", "6 R"};
    boolean onlyWin = false;
    Checkbox onlyWinCb;

    Vector ziehungen;
    Vector tips = new Vector();
    Vector jtips = new Vector();
    int zahlen[] = new int[6];
    ZiehungLW z;

    Enumeration e;
    Tip t;
    int win[] = new int[5];

    OkDialog okd;

    public LottoWin()
    {
        super("LottoWin");

        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu fileMenu = new Menu("File");
        mb.add(fileMenu);
        MenuItem updateMI = new MenuItem("Update");
        fileMenu.add(updateMI);
        fileMenu.addSeparator();
        MenuItem exitMI = new MenuItem("Exit");
        fileMenu.add(exitMI);

        updateMI.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        updateAction();
                                    }
                                });
        exitMI.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        dispatchEvent(new WindowEvent(LottoWin.this, WindowEvent.WINDOW_CLOSING));
                                    }
                                });

        setForeground(Color.black);
		setBackground(Color.lightGray);

        addWindowListener(new Exit());

        loadWinData();
        loadTipsData();

        setLayout(new BorderLayout());

        Panel lottoPanel = new Panel();
        jokerPanel = new JokerPanel(jtips, ziehungen, this);

        FractionalLayout fLay = new FractionalLayout();
        lottoPanel.setLayout(fLay);

        BorderPanel lp = new BorderPanel(BorderPanel.OUT);
        lp.setBackground(Color.lightGray);
        lp.setLayout(new BorderLayout());

        listbox.setFont(new Font("Courier", Font.PLAIN, 12));

        listbox.addItemListener(new ItemListener() {
                                public void itemStateChanged(ItemEvent i)
                                {

                                    switch (i.getStateChange())
                                    {
                                        case ItemEvent.SELECTED:
                                            selectedIndex = ((Integer)i.getItem()).intValue();
                                            selected = true;
                                            break;
                                        case ItemEvent.DESELECTED:
                                            selectedIndex = ((Integer)i.getItem()).intValue();
                                            selected = true;
                                            break;
                                    }
                                }});

        StringBuffer sb = new StringBuffer();
        Tip tip;

        Enumeration e = tips.elements();
        while(e.hasMoreElements())
        {
            sb.setLength(0);
            tip = (Tip)e.nextElement();

            zahlen = tip.getZahlen();
            for (i = 0; i < 6; i++)
            {
                sb.append("  ");

                if (zahlen[i] < 10)
                    sb.append(" ");
                sb.append(String.valueOf(zahlen[i]));
            }
            listbox.addItem(sb.toString());
        }
        lp.add("Center", listbox);

        addButton = new Button("Add");
        addButton.addActionListener(new ActionListener()
                            {
                                public void actionPerformed(ActionEvent a)
                                {
                                    addAction();
                                }
                            });

        deleteButton = new Button("Delete");
        deleteButton.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent a)
                                        {
                                            if (selected)
                                            {
                                                listbox.delItem(selectedIndex);
                                                tips.removeElementAt(selectedIndex);
                                                selected = false;
                                            }
                                        }
                                    });


        clearButton = new Button("Clear");
        clearButton.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent a)
                                        {
                                             listbox.removeAll();
                                             tips.removeAllElements();
                                        }
                                    });

        Panel np = new Panel();
        np.setLayout(new GridLayout(1, 6, 5, 0));
        for (i = 0; i < 6; i++)
        {
            tf[i] = new TextField(2);
            np.add(tf[i]);
        }

        Panel bp = new Panel();
        bp.setLayout(new FlowLayout(FlowLayout.LEFT));
        bp.add(addButton);
        bp.add(deleteButton);
        bp.add(clearButton);

        Panel ap = new Panel();
        ap.setLayout(new BorderLayout());
        ap.add("Center", np);
        ap.add("South", bp);

        lp.add("South", ap);

        fLay.setConstraint(lp, new FrameConstraint(0.00,0, 0.00,0, 0.48,0, 0.45,0));
        lottoPanel.add(lp);

        zcp = new ZiehungChoicePanel(ziehungen);
        zcp.addZiehungChoiceListener(new ZiehungChoiceListener()
                                    {
                                        public void actionPerformed(EventObject e)
                                        {
                                            if (e instanceof ZiehungChoiceEvent)
                                            {
                                                ZiehungChoiceEvent zce = (ZiehungChoiceEvent)e;
                                                jokerPanel.getZCP().select(zce.getFrom(), zce.getTo());
                                                jokerPanel.startAction(zce.getFrom(), zce.getTo());
                                                startAction(zce.getFrom(), zce.getTo());
                                            }
                                        }
                                    });

        fLay.setConstraint(zcp, new FrameConstraint(0.48,0, 0.00,0, 1.0,0, 0.45,0));
        lottoPanel.add(zcp);



        BorderPanel rep = new BorderPanel(BorderPanel.OUT);
        rep.setBackground(Color.lightGray);
        rep.setLayout(new BorderLayout());

        rlbox.setFont(new Font("Courier", Font.PLAIN, 12));
        rep.add("Center", rlbox);

        onlyWinCb = new Checkbox("nur Gewinne anzeigen");
        onlyWinCb.addItemListener(new ItemListener() {
                                public void itemStateChanged(ItemEvent i)
                                {
                                    switch (i.getStateChange())
                                    {
                                        case ItemEvent.SELECTED:
                                            onlyWin = true;
                                            showAgain();
                                            jokerPanel.setonlyWin(true);
                                            break;
                                        case ItemEvent.DESELECTED:
                                            onlyWin = false;
                                            showAgain();
                                            jokerPanel.setonlyWin(false);
                                            break;
                                    }
                                }});

        rep.add("South", onlyWinCb);
        fLay.setConstraint(rep, new FrameConstraint(0.00,0, 0.45,0, 1.0,0, 1.0,0));
        lottoPanel.add(rep);


		FolderPanel folderPanel = new FolderPanel(new Insets(4, 4, 4, 4), 0, 0);
		folderPanel.add(lottoPanel, "Lotto");
		folderPanel.add(jokerPanel, "Joker");
		add(folderPanel);


        setSize(570, 470);
        setVisible(true);

    }

    public ZiehungChoicePanel getZCP()
    {
        return(zcp);
    }

    public void setonlyWin(boolean state)
    {
        onlyWinCb.setState(state);
        onlyWin = state;
        showAgain();
    }

    public void start()
    {
        tf[0].requestFocus();
    }

    void addAction()
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            for (i = 0; i < 6; i++)
            {

                zahlen[i] = Integer.parseInt(tf[i].getText());
                sb.append("  ");
                if (zahlen[i] < 10)
                    sb.append(" ");
                sb.append(String.valueOf(zahlen[i]));

                if ((zahlen[i] < 1) || (zahlen[i] > 45))
                {
                    okd = new OkDialog(this, "Eingabefehler", "Eingabefehler");
                    okd.setVisible(true);
                    tf[0].requestFocus();
                    return;
                }
            }



        	for (i = 0; i < 6; i++)
	        {
            	for (j = i+1; j < 6; j++)
    		    	if (zahlen[i] == zahlen[j])
    		    	{
                        okd = new OkDialog(this, "Eingabefehler", "Zahl doppelt eingegeben");
                        okd.setVisible(true);
                        tf[0].requestFocus();
                        return;
    			    }
    		}

            listbox.addItem(sb.toString());

            Tip t = new Tip(0, zahlen);
            tips.addElement(t);

            for (i = 0; i < 6; i++)
                tf[i].setText("");

            tf[0].requestFocus();
        }
        catch (NumberFormatException  nfe)
        {
            okd = new OkDialog(this, "Eingabefehler", "Nur Zahlen zwischen 1 und 45 eingeben");
            okd.setVisible(true);
        }

    }

    void showAgain()
    {
        if (lastfrom == -1) return;
        startAction(lastfrom, lastto);
    }

    void startAction(int from, int to)
    {
        lastfrom = from;
        lastto   = to;
        int a,b;
        boolean won, firstwon;
        StringBuffer sb = new StringBuffer();

        rlbox.setVisible(false);
        rlbox.removeAll();

        if (from > to)
        {
            a = to;
            b = from;
        }
        else
        {
            a = from;
            b = to;
        }

        for (i = a; i <= b; i++)
        {
            won = false;

            for (j = 0; j < 5; j++)
                win[j] = 0;

            z = (ZiehungLW)ziehungen.elementAt(i);
            sb.setLength(0);
            sb.append(" ");
            if (z.getNr() < 100)
                sb.append(" ");
            if (z.getNr() < 10)
                sb.append(" ");
            sb.append(String.valueOf(z.getNr()));
            sb.append("/");
            sb.append(String.valueOf(z.getJahr()));
            sb.append(", ");
            if (z.getDate().get(Calendar.DAY_OF_MONTH) < 10)
                sb.append(" ");
            sb.append(String.valueOf(z.getDate().get(Calendar.DAY_OF_MONTH)));
            sb.append(".");
            if (z.getDate().get(Calendar.MONTH)+1 < 10)
                sb.append(" ");
            sb.append(String.valueOf(z.getDate().get(Calendar.MONTH)+1));
            sb.append(".");
            sb.append(String.valueOf(z.getDate().get(Calendar.YEAR)));

            e = tips.elements();
            while(e.hasMoreElements())
            {
                t = (Tip)e.nextElement();

                zahlen = t.getZahlen();
                switch(z.getWin(zahlen))
                {
                    case 6 : win[4]++; break;
                    case 51: win[3]++; break;
                    case 5 : win[2]++; break;
                    case 4 : win[1]++; break;
                    case 3 : win[0]++; break;
                }
            }
            firstwon = true;
            for (j = 0; j < 5; j++)
            {
                if (win[j] != 0)
                {
                    won = true;
                    if (firstwon)
                    {
                        sb.append("   GEWINN: ");
                        firstwon = false;
                    }
                    sb.append(String.valueOf(win[j]));
                    sb.append(" x ");
                    sb.append(wintext[j]);
                    sb.append("  ");
                }
            }

            if (won)
                rlbox.addItem(sb.toString());
            else
            {
                if (!onlyWin)
                {
                    sb.append("   kein Gewinn");
                    rlbox.addItem(sb.toString());
                }
            }
        }
        rlbox.setVisible(true);

    }

    void updateAction()
    {
        char cbuf[] = new char[500];
        MsgDialog msgdlg = new MsgDialog(this, "Information", "Update läuft", false);
        msgdlg.setVisible(true);

        int len;
        try
        {
            URL pal = new URL("http://members.xoom.com/ralph_schaer/Lotto/winser.data");
            BufferedWriter bw = new BufferedWriter(new FileWriter("winser.data"));
            BufferedReader br = new BufferedReader(new InputStreamReader(pal.openStream()));

            while ((len = br.read(cbuf,0,cbuf.length)) != -1)
            {
                bw.write(cbuf, 0, len);
            }

            bw.close();
            br.close();

        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }

        loadWinData();

        zcp.updateChoice(ziehungen);
        jokerPanel.getZCP().updateChoice(ziehungen);
        jokerPanel.setNewZiehungVector(ziehungen);

        msgdlg.setVisible(false);
        okd = new OkDialog(this, "Update beendet");
        okd.setVisible(true);
    }

    public void loadWinData()
    {
        ziehungen = new Vector();
        try
        {
            FileInputStream in = new FileInputStream("winser.data");
        	ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(in));
            ziehungen = (Vector)s.readObject();
            s.close();
    	}
    	catch (ClassNotFoundException e)
    	{
    	    System.out.println(e);
    	}
    	catch (IOException e)
    	{
    	    System.out.println(e);
    	}
    }

    public void loadTipsData()
    {
        try
        {
            FileInputStream intip = new FileInputStream("tips.data");
        	ObjectInputStream s = new ObjectInputStream(new BufferedInputStream(intip));
            tips = (Vector)s.readObject();            
            jtips = (Vector)s.readObject();
            s.close();

        }
      	catch (FileNotFoundException fnfe) { }
    	catch (ClassNotFoundException e)
    	{
    	    System.out.println(e);
    	}
    	catch (IOException e)
    	{
    	    System.out.println(e);
    	}
    }


    public static void main(String args[])
    {
        new LottoWin().start();
    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            try
	        {
                FileOutputStream f = new FileOutputStream("tips.data");
            	ObjectOutput s  =  new ObjectOutputStream(new BufferedOutputStream(f));
	            s.writeObject(tips);
	            s.writeObject(jtips);
    	        s.close();
    	    }
	        catch (IOException ioe)
            {
                System.out.println("IOException" + ioe);
            }


            System.exit(0);
        }
    }

}

