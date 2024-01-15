
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class MandelbrotImageProducer implements ImageProducer, Runnable
{
   private Vector _v = null;

   public MandelbrotImageProducer()
   {
      _v = new Vector();
   }

   /**
    * The following methods handle the addition and removal of
    * image consumers.
    */

   public void addConsumer(ImageConsumer ic)
   {
      if (isConsumer(ic) == false) _v.addElement(ic);
   }

   public void removeConsumer(ImageConsumer ic)
   {
      if (isConsumer(ic) == true) _v.removeElement(ic);
   }

   public boolean isConsumer(ImageConsumer ic)
   {
      return _v.indexOf(ic) > -1;
   }

   /**
    * We don't observer this request.
    */

   public void requestTopDownLeftRightResend(ImageConsumer ic)
   {
      ;
   }
 
   /**
    * Image production starts here.  Image production occurs in a
    * separate thread.  The application itself is free to go on
    * with life.
    */

   private Thread _t = null;

   public void startProduction(ImageConsumer ic)
   {
      addConsumer(ic);

      if (_t == null)
      {
         _t = new Thread(this);
         _t.start();
      }
   }

   /**
    * This is the Mandelbrot generation engine.
    */

   private int _nW, _nH;
   private int _nWt, _nHt;

   private double _dX, _dY, _dW, _dH;

   private int _nMax;

   public void run()
   {
      _nW = 512;
      _nH = 512;
      _nWt = 1;
      _nHt = 1;

      _dX = 2.0;
      _dY = 2.0;
      _dW = 4.0;
      _dH = 4.0;

      _nMax = 100;

      ImageConsumer ic;

      ColorModel cm = new DirectColorModel(24, 0x0000FF, 0x00FF00, 0xFF0000);

      Vector v = (Vector)_v.clone();

      Enumeration e;

      // Tell each image consumer the dimensions of the image
      //   and the color model we will use.  Since we don't say
      //   otherwise, they won't expect pixels to arrive in
      //   any particular order (RANDOMPIXELORDER is the
      //   default).

      for (e = v.elements(); e.hasMoreElements(); )
      {
         ic = (ImageConsumer)e.nextElement();

         ic.setDimensions(_nW, _nH);
         ic.setColorModel(cm);
      }

      // Build the Mandelbrot set.  Do so at increasingly higher
      //   resolution (_nWt and _nHt contain the current effective
      //   resolution).

      while (_nWt <= _nW && _nHt <= _nH)
      {
         for (int nY = 0; nY < _nHt; nY++)
         {
            for (int nX = 0; nX < _nWt; nX++)
            {

               // Don't repeat calculation of a particular point

               if (nY % 2 == 0 && nX % 2 == 0 && _nHt > 1 && _nWt > 1)
                  continue;

               int nColor = 0x000000;

               double dX = _dX - nX * _dW / _nWt;
               double dY = _dY - nY * _dH / _nHt;

               double dR = 0.0;
               double dI = 0.0;

               double d = 0.0;

               int i = 0;

               // Determine if the point (dX dY) is in the
               //   Mandelbrot set.  The variable 'd' contains
               //   a measure of the point's magnitude.  If
               //   it ever grows bigger than 4.0 we quit.
               //   In addition, we quit if we ever perform
               //   more than _nMax iterations.

               while (true)
               {
                  if (d > 4.0 || i > _nMax) break;

                  double dRNew = (dR * dR) - (dI * dI) + dX;
                  double dINew = (2.0 * dR * dI) + dY;

                  d = (dR * dR) + (dI * dI);

                  dR = dRNew;
                  dI = dINew;

                  i++;
               }
               
               // If the point was not within the set, determine
               //   its color based on the number of iterations
               //   required to determine it was not within the
               //   set.

               if (d > 4.0)
               {
                  int nRed = (i < 4) ? (i * 64) : 0xFF;
                  int nGreen = (i < 16) ? (i * 16) : 0xFF;
                  int nBlue = (i < 64) ? (i * 4) : 0xFF;

                  nColor = nRed + nGreen * 0x100 + nBlue * 0x10000;
               }

               // Send the pixel values to each image consumer.
               //   Each effective pixel on our side translates
               //   into a block of pixels on the othe other
               //   side.

               int w = _nW / _nWt;
               int h = _nH / _nHt;

               int x = nX * w;
               int y = nY * h;

               int [] rgn = new int [w * h];

               for (int j = 0; j < w * h; j++) rgn[j] = nColor;

               for (e = v.elements(); e.hasMoreElements(); )
               {
                  ic = (ImageConsumer)e.nextElement();

                  ic.setPixels(x, y, w, h, cm, rgn, 0, w);
               }
            }
         }

         // Tell each image consumer that a frame has been
         //   delivered.

         for (e = v.elements(); e.hasMoreElements(); )
         {
            ic = (ImageConsumer)e.nextElement();

            ic.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
         }

         _nWt = _nWt * 2;
         _nHt = _nHt * 2;
      }

      // Tell each image consumer that the image is finsihed.

      for (e = v.elements(); e.hasMoreElements(); )
      {
         ic = (ImageConsumer)e.nextElement();

         ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
      }

      _t = null;
   }
}
