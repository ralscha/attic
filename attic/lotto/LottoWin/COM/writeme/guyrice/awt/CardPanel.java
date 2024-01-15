// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardPanel.java

package COM.writeme.guyrice.awt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

// Referenced classes of package COM.writeme.guyrice.awt:
//            BaseButton, TextButton

public class CardPanel extends Panel
    implements ActionListener
{

    public CardPanel(int i, int j, int k)
    {
        super(new BorderLayout(2, 0));
        add(cards = new Panel(cardsLayout = new CardLayout(i, j)), "Center");
        add(flow = new Panel(new FlowLayout(1, i, j)), "East");
        flow.add(buttons = new Panel(new GridLayout(0, 1, 0, k)));
    }

    public void addCard(Component component, String s)
    {
        cards.add(component, s);
        TextButton textbutton = new TextButton(s);
        textbutton.addActionListener(this);
        textbutton.setSticky(true);
        if(current == null)
        {
            textbutton.setStuck(true);
            current = textbutton;
        }
        buttons.add(textbutton);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        current.setStuck(false);
        current = (TextButton)actionevent.getSource();
        String s = actionevent.getActionCommand();
        cardsLayout.show(cards, s);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        int i = 0;
        int j = flow.getLocation().x - 2;
        int k = j + 1;
        int l = getSize().height - 1;
        g.setColor(getBackground().darker());
        g.drawLine(j, i, j, l);
        g.setColor(getBackground().brighter());
        g.drawLine(k, i, k, l);
    }

    private CardLayout cardsLayout;
    private Panel cards;
    private Panel flow;
    private Panel buttons;
    private TextButton current;
}
