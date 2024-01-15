
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.bdnm.awt.*;
import com.taligent.widget.*;

public class JokerPanel extends Panel
{
    int i,j;
    java.awt.List listbox = new java.awt.List();
    java.awt.List rlbox   = new java.awt.List();
    int lastfrom = -1;
    int lastto   = -1;

    Button addButton, deleteButton, clearButton;
    TextField jokerText = new TextField(6);
    int selectedIndex;
    ZiehungChoicePanel zcp;
    Checkbox onlyWinCb;

    boolean selected = false;
    String wintext[] = {"2 R", "3 R", "4 R", "5 R", "6 R"};
    boolean onlyWin = false;

    Vector jtips;
    Vector ziehungen;
    Ziehung z;

    Enumeration e;
    LottoWin lw;

    public JokerPanel(Vector jtips, Vector ziehungen, LottoWin lw)
    {
        this.lw = lw;
        this.jtips = jtips;
        this.ziehungen = ziehungen;
        FractionalLayout fLay = new FractionalLayout();
        setLayout(fLay);

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

        Enumeration e = jtips.elements();
        while(e.hasMoreElements())
        {
            listbox.add((String)e.nextElement());
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
                                            deleteAction();
                                        }
                                    });


        clearButton = new Button("Clear");
        clearButton.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent a)
                                        {
                                             clearAction();
                                        }
                                    });

        Panel np = new Panel();
        np.setLayout(new FlowLayout(FlowLayout.LEFT));
        np.add(jokerText);

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
        add(lp);

        zcp = new ZiehungChoicePanel(ziehungen);
        zcp.addZiehungChoiceListener(new ZiehungChoiceListener()
                                    {
                                        public void actionPerformed(EventObject e)
                                        {
                                            if (e instanceof ZiehungChoiceEvent)
                                            {
                                                zceAction((ZiehungChoiceEvent) e);
                                            }
                                        }
                                    });

        fLay.setConstraint(zcp, new FrameConstraint(0.48,0, 0.00,0, 1.0,0, 0.45,0));
        add(zcp);

        BorderPanel rep = new BorderPanel(BorderPanel.OUT);
        rep.setBackground(Color.lightGray);
        rep.setLayout(new BorderLayout());

        rlbox.setFont(new Font("Courier", Font.PLAIN, 12));
        rep.add("Center", rlbox);

        onlyWinCb = new Checkbox("nur Gewinne anzeigen");
        onlyWinCb.addItemListener(new ItemListener() {
                                public void itemStateChanged(ItemEvent i)
                                {
                                    itemAction(i);
                                }});

        rep.add("South", onlyWinCb);
        fLay.setConstraint(rep, new FrameConstraint(0.00,0, 0.45,0, 1.0,0, 1.0,0));
        add(rep);

    }

    public void setNewZiehungVector(Vector ziehungen)
    {
        this.ziehungen = null;
        this.ziehungen = ziehungen;
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

    void addAction()
    {
        String in = jokerText.getText();
        if (in.length() != 6)
        {
            jokerText.requestFocus();
            return;
        }
        else
        {
            listbox.add(jokerText.getText());
            jtips.addElement(jokerText.getText());
            jokerText.setText("");
            jokerText.requestFocus();
        }
    }

    void deleteAction()
    {
        if (selected)
        {
            listbox.remove(selectedIndex);
            jtips.removeElementAt(selectedIndex);
            selected = false;
        }
    }

    void clearAction()
    {
        listbox.removeAll();
        jtips.removeAllElements();
    }

    void showAgain()
    {
        if (lastfrom == -1) return;
        startAction(lastfrom, lastto);
    }

    void zceAction(ZiehungChoiceEvent zce)
    {
        lw.getZCP().select(zce.getFrom(), zce.getTo());
        lw.startAction(zce.getFrom(), zce.getTo());
        startAction(zce.getFrom(), zce.getTo());
    }
    void itemAction(ItemEvent i)
    {
         switch (i.getStateChange())
         {
             case ItemEvent.SELECTED:
                   onlyWin = true;
                   showAgain();
                   lw.setonlyWin(true);
                   break;
             case ItemEvent.DESELECTED:
                   onlyWin = false;
                   showAgain();
                   lw.setonlyWin(false);
                   break;
         }
    }

    void startAction(int from, int to)
    {
        lastfrom = from;
        lastto   = to;
        int a,b,rno;
        StringBuffer sb = new StringBuffer();
        boolean firstwon;
        boolean won;
        int win[] = new int[5];
        for (int i = 0; i < 5; i++) win[i] = 0;

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
            z = (Ziehung)ziehungen.elementAt(i);
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
				sb.append(z.getDatum());
            for (int i = 0; i < 5; i++) win[i] = 0;
            e = jtips.elements();
            while(e.hasMoreElements())
            {
                rno = calcWin((String)e.nextElement(), z.getJoker());
                switch(rno)
                {
                    case 6 : win[4]++; break;
                    case 5 : win[3]++; break;
                    case 4 : win[2]++; break;
                    case 3 : win[1]++; break;
                    case 2 : win[0]++; break;
                }
            }
            firstwon = true;
            won = false;
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
                rlbox.add(sb.toString());
            else
            {
                if (!onlyWin)
                {
                    sb.append("   kein Gewinn");
                    rlbox.add(sb.toString());
                }
            }
        }
        rlbox.setVisible(true);

    }

    int calcWin(String t1, String jzie)
    {
        int w = 0;
        int len = t1.length();
        for (int i = 1; i <= len; i++)
        {
            if (t1.charAt(len - i) == jzie.charAt(len-i))
                w++;
            else
                return(w);
        }
        return(w);
    }

}