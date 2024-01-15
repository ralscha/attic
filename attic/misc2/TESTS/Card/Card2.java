import java.awt.*;
import sourcecraft.toolkit.TabPanel;

public class Card2 extends Frame
{

    public Card2()
    {
        super("TabPanel Test");


        TabPanel tabPanel = new TabPanel("South");

        setLayout(new BorderLayout());

        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout(2,2));
        p1.add("North", new Button("North"));
        p1.add("South", new Button("South"));
        p1.add("West", new Button("West"));
        p1.add("East", new Button("East"));
        p1.add("Center", new Label("Center", Label.CENTER));
        tabPanel.addTab("PANEL 1", p1);

        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout(2,2));
        p2.add("North", new Button("I"));
        p2.add("South", new Button("IV"));
        p2.add("West", new Button("III"));
        p2.add("East", new Button("II"));
        p2.add("Center", new Label("V", Label.CENTER));
        tabPanel.addTab("PANEL 2", p2);

        Panel p3 = new Panel();
        p3.setLayout(new BorderLayout(2,2));
        p3.add("North", new Button("Nord"));
        p3.add("South", new SRSlider(0, 200));
        p3.add("West", new SRSlider(0, 100));
        p3.add("East", new Button("Ost"));
        p3.add("Center", new Label("Zentrum", Label.CENTER));
        tabPanel.addTab("PANEL 3", p3);

        add("Center", tabPanel);
    }

    public boolean action(Event evt, Object arg)
    {
        if (evt.target instanceof Button)
        {
            System.out.println((String)arg + " wurde  angeklickt");
            return true;
        }

        return false;
    }

    public boolean handleEvent(Event e)
    {
        if (e.id == Event.WINDOW_DESTROY)
        {
                System.exit(0);
        }
        return super.handleEvent(e);
    }

    public static void main(String args[])
    {

        Card2 win = new Card2();

        win.pack();
        win.resize(400, 300);
        win.show();
    }
}