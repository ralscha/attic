import java.awt.*;
import java.io.*;


public class Search extends Frame
{

    TextField searchTF;
    Button search, stop;
    Label l1, l2;
    List foundList;
    String sString;
    boolean ok = true;


    public Search()
    {
        super("Search PI");
        init();
    }

    public void init()
    {
        searchTF = new TextField(15);
        search = new Button("Suchen");
        stop  = new Button("Stop");
        stop.disable();
        l1 = new Label("Suchen nach: ", Label.LEFT);
        l2 = new Label("Gefunden an Position", Label.LEFT);
        foundList = new List(50, false);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;

        c.anchor = GridBagConstraints.WEST;

        c.weighty = 0.0;
        c.weightx = 1.0;

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(l1, c);
        add(l1);

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(searchTF, c);
        add(searchTF);


        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(l2, c);
        add(l2);

        c.weighty = 1.0;

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(foundList, c);
        add(foundList);
        c.weighty = 0.0;

        Panel bp = new Panel();
        bp.setLayout(new GridLayout(1, 2, 5, 5));

        bp.add(search);
        bp.add(stop);

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(bp, c);
        add(bp);

        resize(150, 200);
        show();

    }

    public boolean action(Event evt, Object arg)
    {
        if (evt.target == search)
        {
            ok = true;

            sString = searchTF.getText();
            if (sString.equals(""))
            {
                MsgBox mb = new MsgBox(this, "Eingabefehler", "Keine Eingabe");
                mb.show();
            }
            else
            {
                for (int i = 0; i < sString.length(); i++)
                {
                    if (!Character.isDigit(sString.charAt(i)))
                    {
                        i = sString.length();
                        ok = false;
                        MsgBox mb = new MsgBox(this, "Eingabefehler", "Nur Zahlen eingeben");
                        mb.show();
                    }
                }
            }

            if (ok)
            {
                foundList.clear();
                loadPIfile();
            }

            return true;
        }

        return false;
    }

    public boolean handleEvent(Event evt)
	{
		if (evt.id == Event.WINDOW_DESTROY && evt.target == this)
		{
			System.exit(0);
		}

		return super.handleEvent(evt);
	}


    public static void main(String args[])
    {
    	new Search();
    }


    void loadPIfile()
    {
        DataInputStream dis;
        String inputLine;
        String file = "pi.txt";
        StringBuffer sb;
        String oldBuf = null;
        int i;
        char ch;

        SearchAlgo sa = new SearchAlgo(sString);

        int lenss = sString.length();
    	int res;
    	int pos = 1;

        try
        {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            while ((inputLine = dis.readLine()) != null)
            {
                sb = new StringBuffer(inputLine.length()+lenss);
                if (oldBuf != null)
                    sb.append(oldBuf.substring(oldBuf.length()-lenss+1));

                for (i = 0; i < inputLine.length(); i++)
                {
                    ch = inputLine.charAt(i);
                    if (Character.isDigit(ch))
                    {
                        sb.append(ch);
                    }
                }
                if (sb.length() > 0)
                {
                    oldBuf = sb.toString();

                    res = sa.mischarsearch(sb.toString(), 0);
		            while (res < sb.length())
            		{
            		    foundList.addItem(String.valueOf(pos+res)+" - "+String.valueOf(pos+res+lenss-1));
            			res = sa.mischarsearch(sb.toString(), res+1);
            		}
            		pos += sb.length() - lenss + 1;
                }

            }
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe);
        }
    }

}

