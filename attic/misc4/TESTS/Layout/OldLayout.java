
import java.awt.*;
import java.applet.*;

public class Layout extends Applet
{

    public void init()
    {
        super.init();

        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gridbag = new GridBagLayout();

        setLayout(gridbag);

        Label poly1 = new Label("Enter Polynomial 1:");
        Label poly2 = new Label("Enter Polynomial 2:");
        Label resultLabel = new Label("Result:");
        Label result = new Label("100");

        TextField p1 = new TextField(10);
        TextField p2 = new TextField(10);

        Button add  = new Button("Add");
        Button mult = new Button("Mult");

        c.insets = new Insets(3, 3, 3, 3);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 2;
        gridbag.setConstraints(poly1, c);
        add(poly1);

        c.weightx = 1.0;
        c.gridwidth = 1;
        gridbag.setConstraints(p1, c);
        add(p1);

        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(add, c);
        add(add);


        c.gridwidth = 2;
        gridbag.setConstraints(poly2, c);
        add(poly2);

        c.gridwidth = 1;
        gridbag.setConstraints(p2, c);
        add(p2);

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(mult, c);
        add(mult);

        c.gridwidth = 1;
        gridbag.setConstraints(resultLabel, c);
        add(resultLabel);

        c.gridwidth = 1;
        gridbag.setConstraints(result, c);
        add(result);

    }
}
