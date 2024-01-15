
import java.awt.*;
import java.awt.image.*;
import java.applet.*;

class NewCanvas extends Canvas
{
   private New _n = null;

   public NewCanvas(New n)
   {
      _n = n;
   }

   public void update(Graphics g)
   {
      if (_n.im != null) g.drawImage(_n.im, 10, 10, this);
   }

   public void paint(Graphics g)
   {
      update(g);
   }
}

public class New extends Applet
{
   Image im = null;

   private NewCanvas _nc = null;
   private Button _b = null;

   public void init()
   {
      setLayout(new BorderLayout());

      _nc = new NewCanvas(this);
      _b = new Button("Restart");

      add("Center", _nc);
      add("South", _b);

      im = createImage(new MandelbrotImageProducer());

      prepareImage(im, _nc);
   }

   public boolean action(Event e, Object o)
   {
      if (o.equals("restart"))
      {
         im = createImage(new MandelbrotImageProducer());

         prepareImage(im, _nc);

         return true;
      }

      return false;
   }
}
