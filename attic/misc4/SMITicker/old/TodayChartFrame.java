
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TodayChartFrame extends Frame  implements SMIHTMLExtractorListener
{
    private StockGraph sg;

    public TodayChartFrame(String title, double min, double max)
    {
        super("Swiss Market Index Chart");

        setForeground(Color.black);
		setBackground(Color.lightGray);

        addWindowListener(new Exit());

        sg = new StockGraph(title, min, max);
		add(sg);

        pack();

    }

    public void newDataArrived(SMIHTMLExtractorEvent e)
    {
        SMIHTMLExtractor ex = (SMIHTMLExtractor)e.getSource();
        String lastStr = ex.getLast();
        String updateStr = ex.getTime();
        int h = Integer.parseInt(updateStr.substring(0,2));
        int m = Integer.parseInt(updateStr.substring(3,5));
        sg.addItem((h*60+m)-600, Double.valueOf(lastStr).doubleValue());

        String lowStr = ex.getLow();
        String highStr = ex.getHigh();
        sg.setMinMax(Double.valueOf(lowStr).doubleValue(),
                     Double.valueOf(highStr).doubleValue());
    }


    public void showChart()
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

