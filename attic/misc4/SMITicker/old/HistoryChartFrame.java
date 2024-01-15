
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import COM.writeme.guyrice.awt.*;

public class HistoryChartFrame extends Frame
{
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final static boolean WEB = true;


    public HistoryChartFrame()
    {
        super("Swiss Market Index History");
        Image img[] = new Image[4];

        MediaTracker tracker = new MediaTracker(this);
        try {
            if (WEB)
            {
                img[0] = toolkit.getImage(new URL("http://otf1.tszrh.ska.com/CTIB/OTFCHART?IMG=d90.SSMI285.gif"));
                img[1] = toolkit.getImage(new URL("http://otf1.tszrh.ska.com/CTIB/OTFCHART?IMG=d365.SSMI285.gif"));
                img[2] = toolkit.getImage(new URL("http://otf1.tszrh.ska.com/CTIB/OTFCHART?IMG=w112.SSMI285.gif"));
                img[3] = toolkit.getImage(new URL("http://otf1.tszrh.ska.com/CTIB/OTFCHART?IMG=w280.SSMI285.gif"));
            }
            else
            {
                img[0] = toolkit.getImage("month3.gif");
                img[1] = toolkit.getImage("month12.gif");
                img[2] = toolkit.getImage("year2.gif");
                img[3] = toolkit.getImage("year5.gif");

            }
        }
        catch (MalformedURLException m)
        {
            System.out.println(m);
        }

        //wait until the image is in memory
        for (int i = 0; i < 4; i++)
            tracker.addImage(img[i], i);

        try
        {
            tracker.waitForAll();
        }
        catch (InterruptedException e) { }

        MenuBar mb = new MenuBar();
        setMenuBar(mb);

        Menu fileMenu = new Menu("File");
        mb.add(fileMenu);
        MenuItem exitMI = new MenuItem("Exit");
        fileMenu.add(exitMI);

        exitMI.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent a)
                                    {
                                        dispatchEvent(new WindowEvent(HistoryChartFrame.this, WindowEvent.WINDOW_CLOSING));
                                    }
                                });

        setForeground(Color.black);
		setBackground(Color.lightGray);

        addWindowListener(new Exit());


        ImageCanvas p1 = new ImageCanvas(img[0]);
        ImageCanvas p2 = new ImageCanvas(img[1]);
        ImageCanvas p3 = new ImageCanvas(img[2]);
        ImageCanvas p4 = new ImageCanvas(img[3]);

		FolderPanel folderPanel = new FolderPanel(new Insets(4, 4, 4, 4), 0, 0);
		folderPanel.add(p1, "3 months");
		folderPanel.add(p2, "12 months");
		folderPanel.add(p3, "2 years");
		folderPanel.add(p4, "5 years");

		add(folderPanel);

        setSize(p1.getPreferredSize().width,p1.getPreferredSize().height+90);

    }

    public void showCharts()
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

