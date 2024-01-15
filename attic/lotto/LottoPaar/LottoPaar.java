
import java.awt.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import com.taligent.widget.*;
import com.taligent.util.*;

public class LottoPaar extends Applet
{
    int paar[][][];
    int x, i, j;
    Choice c1 = new Choice();
    Object[][] obj = new Object[45][2];
    Object[]   obj0 = new Object[45];
    Object[]   obj1 = new Object[45];
    int since;
    int lz = 0;
    final static String CB_ALL = "alle Ziehungen";
    final static String CB_42  = "ab 6 aus 42";
    final static String CB_45  = "ab 6 aus 45";
    final static String CB_Z97 = "ab Ziehung Nr. 1/97";

    MultiColumnListbox listbox = new MultiColumnListbox();


    public void init()
    {
        super.init();
        paar = new int[4][45][45];
        for (x = 0; x < 4; x++)
            for (i = 0; i < 45; i++)
                for (j = 0; j < 45; j++)
                    paar[x][i][j] = 0;

        loadData();
        since = 0;

        setBackground(Color.lightGray);
        setLayout(new BorderLayout());


        listbox.showHorizontalSeparator();
        listbox.showVerticalSeparator();
        listbox.addColumn("Zahl");
        listbox.addColumn("Ausspielungen");

        Integer h;
        for (i = 0; i < 45; i++)
        {
            h = new Integer(i+1);
            obj[i][0] = h;
            obj0[i]   = h;
            obj[i][1] = new Integer(paar[since][lz][i]);
        }
        listbox.addRows(obj);

        SelectionSorter ss = new SelectionSorter();
        ss.setComparator(new IntegerComparator());

        listbox.getColumnInfo(0).setSorter(ss).setWidth(50).setAlignment(ListboxColumn.CENTER).setResizable(false);
        listbox.getColumnInfo(1).setSorter(ss).setWidth(100).setAlignment(ListboxColumn.CENTER).setResizable(false);

        for (i = 0; i < 45; i++)
            c1.addItem(String.valueOf(i+1));

        BorderPanel bp = new BorderPanel(BorderPanel.RAISED);
        bp.setLayout(new BorderLayout());

        Checkbox cb1, cb2, cb3, cb4;
        CheckboxGroup cbg;

        BorderPanel cp = new BorderPanel();
        cp.setLayout(new GridLayout(4,1));
        cbg = new CheckboxGroup();
        cb1 = new Checkbox(CB_ALL, cbg, true);
        cb2 = new Checkbox(CB_42 , cbg, false);
        cb3 = new Checkbox(CB_45 , cbg, false);
        cb4 = new Checkbox(CB_Z97, cbg, false);
        cp.add(cb1);
        cp.add(cb2);
        cp.add(cb3);
        cp.add(cb4);
        bp.add("South", cp);

        BorderPanel lbp = new BorderPanel();
        lbp.setLayout(new BorderLayout());
        lbp.add("Center", listbox);

        BorderPanel cbp = new BorderPanel();
        cbp.setLayout(new FlowLayout(FlowLayout.CENTER));
        cbp.add(new Label("Auflistung für: "));
        cbp.add(c1);

        bp.add("Center", lbp);
        bp.add("North", cbp);
        add("Center", bp);
        listbox.sort();
    }

    public boolean action(Event evt, Object arg)
    {

        if (evt.target == c1)
        {
            try { lz = Integer.parseInt((String)arg); }
            catch (NumberFormatException nfe) { lz = 0; }
            lz--;
            for (i = 0; i < 45; i++)
                obj1[i] = new Integer(paar[since][lz][i]);
            listbox.replaceColumn(obj0,0);
            listbox.replaceColumn(obj1,1);
            listbox.sort();
            return true;
        }
        else if (evt.target instanceof Checkbox)
        {
            if (((Checkbox)evt.target).getLabel().equals(CB_ALL))
                since = 0;
            if (((Checkbox)evt.target).getLabel().equals(CB_42))
                since = 1;
            if (((Checkbox)evt.target).getLabel().equals(CB_45))
                since = 2;
            if (((Checkbox)evt.target).getLabel().equals(CB_Z97))
                since = 3;

            for (i = 0; i < 45; i++)
                obj1[i] = new Integer(paar[since][lz][i]);
            listbox.replaceColumn(obj0,0);
            listbox.replaceColumn(obj1,1);
            listbox.sort();

            return true;
        }

        return false;
    }


    public void loadData()
    {
        try
        {
            URL pal = new URL(getDocumentBase(), "paar.data");
            String inputLine;
            String tok;
            int val;
            x = 0;
            i = 0;
            j = 0;

            DataInputStream dis = new DataInputStream(new BufferedInputStream(pal.openStream()));
            while ((inputLine = dis.readLine()) != null)
            {
                if (inputLine.equals("#")) x++;
                else
                {
                    StringTokenizer st = new StringTokenizer(inputLine, ",");
                    if (st.hasMoreTokens())
                    {
                        tok = st.nextToken();
                        i = Integer.parseInt(tok);
                        j = i;
                        while(st.hasMoreTokens())
                        {
                            val = Integer.parseInt(st.nextToken());
                            paar[x][i-1][j] = val;
                            paar[x][j][i-1] = val;
                            j++;
                        }
                    }
                }
            }
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: "+nfe);
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

