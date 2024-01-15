import java.awt.*;

public class CosScrollbarPanel extends ScrollbarPanel
{
    private int value;

    public CosScrollbarPanel(String title, DrawCanvas draw)
    {
        super(title, draw);
    }

   	public boolean handleEvent(Event evt)
    {
        int help;
        switch (evt.id)
        {
            case Event.SCROLL_LINE_UP:
            case Event.SCROLL_LINE_DOWN:
            case Event.SCROLL_PAGE_UP:
            case Event.SCROLL_PAGE_DOWN:
            case Event.SCROLL_ABSOLUTE:

            if (evt.target == horz)
            {
                start = horz.getValue();
                for (int i = 0; i < 10; i++)
                {
                    scrollbar[i].setValue(scrollValues[start+i]);
                    sblabel[i].change(String.valueOf(i+start+1));
                }
            }
            else
            {
                for (int i = 0; i < 10; i++)
                    scrollValues[start+i] = scrollbar[i].getValue();

                int max = scrollbar[0].getMaximum() - 1;

                for (int i = 0; i < Global.sb; i++)
                {
                    help = max - scrollValues[i];
                    scrolls[i] =  (double)help / (double)max;
                }
                drawer.setCos(scrolls);
                drawer.repaint();
            }
        }
        return super.handleEvent(evt);
    }
}

