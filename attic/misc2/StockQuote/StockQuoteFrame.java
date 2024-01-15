import java.awt.*;
import java.util.*;
import wit.html.*;
import wit.http.*;
import java.io.*;

public class StockQuoteFrame extends Frame
{
    Button fetch, auto;
    TextField symbolTF;
    Label ts, title, current, today, status;
    String symbol;

    public StockQuoteFrame()
    {
        init();
        show();
    }

    public void init()
    {
        Panel p = new Panel();

        setLayout(new BorderLayout(3,3));
        status = new Label("Ready", Label.LEFT);
        add("South", status);


        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        p.setLayout(gridbag);

        fetch = new Button("Fetch");
        auto  = new Button("Automatic");
        ts = new Label("Tickersymbol");
        symbolTF = new TextField(5);
        title = new Label("TITLE                                                     ");
        current = new Label("CURRENT                                                 ");
        today = new Label("TODAY                                                     ");

        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(ts, c);
        p.add(ts);

        c.gridwidth = 1;
        gridbag.setConstraints(symbolTF, c);
        p.add(symbolTF);

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(fetch, c);
        p.add(fetch);

        c.weightx = 1.0;
        gridbag.setConstraints(title, c);
        p.add(title);

        gridbag.setConstraints(current, c);
        p.add(current);

        gridbag.setConstraints(today, c);
        p.add(today);

        add("Center", p);

        addNotify();
        resize(400, 150);
        show();
    }

    public static void main(String args[])
    {
	    new StockQuoteFrame();
    }

    public boolean action(Event evt, Object arg)
    {

        if (evt.target == fetch)
        {
            symbol = symbolTF.getText();
            if (symbol.length() > 0)
            {
                try
                {
    	    		StockQuote o = new StockQuote(symbol);

                    title.setText(o.name);
                    current.setText(o.date + " " + o.time + " Last: " + o.last + " " + o.change);
                    today.setText("Today Open: " + o.todayopen + " High: " + o.todayhigh + " Low: "
                                    + o.todaylow + "Volume: " + o.volume);
                    System.out.println(o.week52high);
                    System.out.println(o.week52low);
        		}
		        catch (IOException e)
        		{
		        	System.err.println("IO Exception: " + e.getMessage());
        		}
		        catch (WITException e)
        		{
		        	System.err.println(e.getMessage());
        		}
		        catch (WITServiceException e)
        		{
		        	System.err.println(e.getMessage());
        		}
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

    //{{DECLARE_CONTROLS
    //}}

}
