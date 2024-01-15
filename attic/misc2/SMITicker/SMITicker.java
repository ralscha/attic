import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class SMITicker extends Frame implements SMIHTMLExtractorListener
{
    private Label labels[] = new Label[16];
    private SMIHTMLExtractor sq;
    private Cursor waitCursor = new Cursor(WAIT_CURSOR);
    private CheckboxMenuItem selectedMenu;
    private CheckboxMenuItem min1;
    private CheckboxMenuItem min5;
    private CheckboxMenuItem min10;
    private CheckboxMenuItem min20;
    private CheckboxMenuItem never;
    //private Thread loadThread;
    //private HistoryChartFrame hcf;
    //private TodayChartFrame todayChart;

    public SMITicker() {
        super("SMI Ticker");

        //hcf = new HistoryChartFrame();

        //todayChart = new TodayChartFrame("SMI", Double.valueOf(sq.getLow()).doubleValue(),
        //                                    Double.valueOf(sq.getHigh()).doubleValue());
        //sq.addListener(todayChart);


        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu updateMenu = new Menu("Update");
        Menu chartMenu  = new Menu("Chart");
        mb.add(updateMenu);
        mb.add(chartMenu);

        MenuItem updateNowMI = new MenuItem("Update Now!");
        updateMenu.add(updateNowMI);
        updateMenu.addSeparator();
        min1  = new CheckboxMenuItem("1 minute");
        min5  = new CheckboxMenuItem("5 minutes");
        min10 = new CheckboxMenuItem("10 minutes");
        min20 = new CheckboxMenuItem("20 minutes");
        never = new CheckboxMenuItem("Never", true);
        selectedMenu = never;

        ItemListener il = new ItemListener() {
                                public void itemStateChanged(ItemEvent i) {
                                    updateCheckboxMenu(i);
                                }};

        min1.addItemListener(il);
        min5.addItemListener(il);
        min10.addItemListener(il);
        min20.addItemListener(il);
        never.addItemListener(il);

        updateMenu.add(min1);
        updateMenu.add(min5);
        updateMenu.add(min10);
        updateMenu.add(min20);
        updateMenu.add(never);

        updateMenu.addSeparator();
        MenuItem exitMI = new MenuItem("Exit");
        updateMenu.add(exitMI);

        updateNowMI.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent a) {
                                        updateAction();
                                    }});
        exitMI.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent a) {
                                        dispatchEvent(new WindowEvent(SMITicker.this, WindowEvent.WINDOW_CLOSING));
                                    }});


        MenuItem todayChartMI = new MenuItem("Today");
        chartMenu.add(todayChartMI);
        todayChartMI.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent a){
                                        //todayChart.showChart();
                                    }});


        MenuItem historyChartMI = new MenuItem("History");
        chartMenu.add(historyChartMI);
        historyChartMI.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent a){
                                        //hcf.showCharts();
                                    }});


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

        //setLabels(sq);
        for (int i = 8; i < 16; i++)
            labels[i] = new Label("");
        labels[8].setFont(new Font("Times", Font.BOLD, 12));

        Panel p = new Panel();
        p.setLayout(new GridLayout(8,2));

        for (int i = 0; i < 8; i++) {
            p.add(labels[i]);
            p.add(labels[i+8]);
        }

        add("Center",p);

        /*
        Panel q = new Panel();
        Button updateButton = new Button("Update");
        updateButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e)
                                        { updateAction(); } });
        q.setLayout(new FlowLayout());
        q.add(updateButton);
        add("South", q);
*/

        setSize(130, 200);
        setVisible(true);
        addWindowListener(new Exit());
        
        sq = new SMIHTMLExtractor(0);
        sq.addListener(this);
        sq.update();
    }

    void updateCheckboxMenu(ItemEvent i) {
        if (i.getSource() instanceof CheckboxMenuItem) {
            CheckboxMenuItem cb = (CheckboxMenuItem)i.getSource();
            if (cb != selectedMenu) {
                selectedMenu.setState(false);
                selectedMenu = cb;

                if (selectedMenu == min1)
                    sq.setUpdateTime(1);
                else if (selectedMenu == min5)
                    sq.setUpdateTime(5);
                else if (selectedMenu == min10)
                    sq.setUpdateTime(10);
                else if (selectedMenu == min20)
                    sq.setUpdateTime(20);
                else if (selectedMenu == never)
                    sq.setUpdateTime(0);

                //sq.addListener(todayChart);

//                if (selectedMenu != never)
//                    sq.start();
            } else {
                cb.setState(true);
            }
        }
    }

    void updateAction()
    {
        Cursor old = Cursor.getDefaultCursor();
        setCursor(waitCursor);
        sq.update();
        setCursor(old);
    }

    public void setLabels(SMIHTMLExtractor ex) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        DecimalFormat form = new DecimalFormat("#,##0.0");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);
System.out.println("UPDATE");
        labels[8].setText(form.format(ex.getLast()));
        labels[9].setText(form.format(ex.getNetChg()));
        labels[10].setText(form.format(ex.getPctChg())+ " %");
//            labels[11].setText(ex.getPrev());
//            labels[12].setText(ex.getOpen());
        labels[11].setText("");
        labels[12].setText("");
        labels[13].setText(form.format(ex.getHigh()));
        labels[14].setText(form.format(ex.getLow()));
        labels[15].setText(dateFormat.format(ex.getTime().getTime()));
    }

    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

    

    public void newDataArrived(SMIHTMLExtractorEvent sh) {
        SMIHTMLExtractor ex = (SMIHTMLExtractor)sh.getSource();
        setLabels(ex);
    }

    public static void main(String args[]) {
        new SMITicker();

    }
}
