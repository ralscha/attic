import java.awt.*;

public class Card extends Frame
{

    Panel cards;
    String PANEL1 = "Panel 1";
    String PANEL2 = "Panel 2";
    String PANEL3 = "Panel 3";

    public Card()
    {
        super("CardLayout Test");
        setLayout(new BorderLayout());

        Panel pc = new Panel();
        pc.setLayout(new FlowLayout(FlowLayout.CENTER));

        Checkbox cb1, cb2, cb3;
        CheckboxGroup cbg;

        cbg = new CheckboxGroup();
        cb1 = new Checkbox(PANEL1, cbg, true);
        cb2 = new Checkbox(PANEL2, cbg, false);
        cb3 = new Checkbox(PANEL3, cbg, false);
        pc.add(cb1);
        pc.add(cb2);
        pc.add(cb3);
        add("South", pc);

        cards = new Panel();
        cards.setLayout(new CardLayout());


        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout(2,2));
        p1.add("North", new Button("North"));
        p1.add("South", new Button("South"));
        p1.add("West", new Button("West"));
        p1.add("East", new Button("East"));
        p1.add("Center", new Label("Center", Label.CENTER));

        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout(2,2));
        p2.add("North", new Button("I"));
        p2.add("South", new Button("IV"));
        p2.add("West", new Button("III"));
        p2.add("East", new Button("II"));
        p2.add("Center", new Label("V", Label.CENTER));

        Panel p3 = new Panel();
        p3.setLayout(new BorderLayout(2,2));
        p3.add("North", new Button("Nord"));
        p3.add("South", new Button("Süd"));
        p3.add("West", new Button("West"));
        p3.add("East", new Button("Ost"));
        p3.add("Center", new Label("Zentrum", Label.CENTER));

        cards.add(PANEL1, p1);
        cards.add(PANEL2, p2);
        cards.add(PANEL3, p3);
        add("Center", cards);
    }

    public boolean action(Event evt, Object arg)
    {
        if (evt.target instanceof CheckboxGroup)
        {
            System.out.println(arg);
            return true;
        }
        else if (evt.target instanceof Checkbox)
        {
            ((CardLayout)cards.getLayout()).show(cards,((Checkbox)evt.target).getLabel());
            return true;
        }
        else if (evt.target instanceof Button)
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

        Card win = new Card();

        win.pack();
        win.resize(400, 300);
        win.show();
    }
}