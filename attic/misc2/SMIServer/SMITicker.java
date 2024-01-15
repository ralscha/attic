import java.awt.*;
import java.awt.event.*;
import COM.objectspace.voyager.*;
import COM.objectspace.voyager.space.*;

public class SMITicker extends Frame implements SMIServerListener
{
    private Label labels[] = new Label[16];
    private Cursor waitCursor = new Cursor(WAIT_CURSOR);
    private CheckboxMenuItem selectedMenu;
    private CheckboxMenuItem min1;
    private CheckboxMenuItem min5;
    private CheckboxMenuItem min10;
    private CheckboxMenuItem min20;
    private CheckboxMenuItem never;
    private Thread loadThread;
    private HistoryChartFrame hcf;
    private TodayChartFrame todayChart;
    private VTodayChartFrame vtcf;
    private VSMITicker self;

    private VSMIServer vsmiserver;

    public SMITicker()
    {
        super("SMI Ticker");

        hcf = new HistoryChartFrame();

        try
        {

    	    self = (VSMITicker)VObject.forObject(this);
            self.liveForever();

            //Timeout 20s
            vsmiserver = (VSMIServer)VObject.forObjectAt("r4006174.itzrh.ska.com:7000/SMIServer");
            vsmiserver.addListener(self);

            todayChart = new TodayChartFrame("SMI", Double.valueOf(vsmiserver.getLow()).doubleValue(),
                                             Double.valueOf(vsmiserver.getHigh()).doubleValue());
    	    vtcf = (VTodayChartFrame)VObject.forObject(todayChart);
    	    vtcf.liveForever();
            vsmiserver.addListener(vtcf);
        }
        catch( VoyagerException exception )
        {
            System.err.println( exception );
     	    System.exit(0);
        }

        /*
        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu updateMenu = new Menu("Update");
        Menu chartMenu  = new Menu("Chart");
        mb.add(updateMenu);
        mb.add(chartMenu);

        MenuItem updateNowMI = new MenuItem("Update Now!");
        updateMenu.add(updateNowMI);
        updateMenu.addSeparator();

        MenuItem exitMI = new MenuItem("Exit");
        updateMenu.add(exitMI);

        updateNowMI.addActionListener(new ActionListener()
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
                                        dispatchEvent(new WindowEvent(SMITicker.this, WindowEvent.WINDOW_CLOSING));
                                    }
                                });


        MenuItem todayChartMI = new MenuItem("Today");
        chartMenu.add(todayChartMI);
        todayChartMI.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        todayChart.showChart();
                                    }
                                });


        MenuItem historyChartMI = new MenuItem("History");
        chartMenu.add(historyChartMI);
        historyChartMI.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        hcf.showCharts();
                                    }
                                });

*/
        setLayout(new BorderLayout());


        labels[0] = new Label("Last");
        labels[0].setFont(new Font("Times", Font.BOLD, 12));
        labels[1] = new Label("Change");
        labels[2] = new Label("");
        labels[3] = new Label("Prev");
        labels[4] = new Label("Open");
        labels[5] = new Label("High");
        labels[6] = new Label("Low");
        labels[7] = new Label("Update");


        try
        {
            labels[8]  = new Label(vsmiserver.getLast());
            labels[8].setFont(new Font("Times", Font.BOLD, 12));
            labels[9]  = new Label(vsmiserver.getNetChg());
            labels[10] = new Label(vsmiserver.getPctChg());
            labels[11] = new Label(vsmiserver.getPrev());
            labels[12]  = new Label(vsmiserver.getOpen());
            labels[13] = new Label(vsmiserver.getHigh());
            labels[14] = new Label(vsmiserver.getLow());
            labels[15] = new Label(vsmiserver.getTime());
        }
        catch( VoyagerException exception )
        {
            System.err.println( exception );
            System.exit(0);
        }

        Panel p = new Panel();
        p.setLayout(new GridLayout(8,2));

        for (int i = 0; i < 8; i++)
        {
            p.add(labels[i]);
            p.add(labels[i+8]);
        }

        add("Center",p);

        Panel q = new Panel();
        /*
        Button updateButton = new Button("Update");
        updateButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e)
                                        { updateAction(); } });
        q.setLayout(new FlowLayout());
        q.add(updateButton);
        */
        Button chartButton = new Button("Chart");
        chartButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e)
                                        { todayChart.showChart(); } });
        q.setLayout(new FlowLayout());
        q.add(chartButton);


        add("South", q);

        setSize(130, 200);
        setVisible(true);
        addWindowListener(new Exit());

    }

    void updateAction()
    {
       try
	 {
	  	 vsmiserver.update();
       }
       catch( VoyagerException exception )
       {
           System.err.println( exception );
	       System.exit(0);
       }

    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            try
            {
                vsmiserver.removeListener(self);
                vsmiserver.removeListener(vtcf);
            }
            catch( VoyagerException exception )
            {
                System.err.println( exception );
     	        System.exit(0);
            }

            System.exit(0);
        }
    }

    public void newData(SMIServerEvent smievent)
    {
        if (labels[8] != null)
        {
            labels[8].setText(smievent.getLast());
            labels[9].setText(smievent.getNetChg());
            labels[10].setText(smievent.getPctChg());
            labels[11].setText(smievent.getPrev());
            labels[12].setText(smievent.getOpen());
            labels[13].setText(smievent.getHigh());
            labels[14].setText(smievent.getLow());
            labels[15].setText(smievent.getTime());
        }
    }

    public static void main(String args[])
    {
        new SMITicker();
    }
}
