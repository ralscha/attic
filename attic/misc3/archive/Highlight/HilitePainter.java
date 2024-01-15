import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class HilitePainter implements Highlighter.HighlightPainter {
    private Mediator med;

    public HilitePainter(Mediator md) {
        med = md;
    }
    public void paint (Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        int offset = 0;

        g.setColor(Color.yellow);  //paint background in yellow                          
        Rectangle r = null;
        Rectangle r1 = null;
        //set each rectangle
        try {
            r= c.modelToView (p0);
            Word w = med.findWord(p0); //find word beginning there
            //find offset of next word
            offset = w.getOffset() + w.length ();
            r1 = c.modelToView (offset);
        } catch (BadLocationException ex) {
            System.out.println("Bad offset");
            r = bounds.getBounds ();
        }
        //draw rectangle for background
        g.fillRect(r.x, r.y, (int)Math.abs(r1.x - r.x), r.height);
    }
}