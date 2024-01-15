
import java.applet.Applet;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.*;
import java.io.*;
import java.net.*;

public class MGTab extends java.applet.Applet
{
   int iTabCount, iCurrentTab, iFontSize;
   String sLineList[] = new String[20];
   String sUrlList[]  = new String[20];
   String sWindow[]   = new String[20];
   String sFontName;
   Font fPlainFont, fBoldFont;
   Color TextColor, BGColor, HighColor;


   public void init()
   {
      String sParam, sLine, sFontSize;
      boolean bRun;

      iCurrentTab = 0;
      iTabCount = 0;
      bRun = true;

      TextColor = readColor(getParameter("TextColor"), Color.black);
      BGColor = readColor(getParameter("BGColor"), Color.lightGray);
      HighColor = readColor(getParameter("HighColor"), Color.black);

      sFontName = getParameter("font");
      if (sFontName == null)
         sFontName = "helvetica";

      sParam = getParameter("Initial");
      if (sParam == null)
         iCurrentTab = 0;
      else
         iCurrentTab = Integer.parseInt(sParam);

      sFontSize = getParameter("size");
      if (sFontSize == null)
         sFontSize = "12";
      iFontSize = Integer.parseInt(sFontSize);

      fPlainFont = new Font(sFontName, Font.PLAIN, iFontSize);
      fBoldFont = new Font(sFontName, Font.BOLD, iFontSize);

      while(bRun)
      {
         sParam = "Line" + sParam.valueOf(iTabCount);
         sLine = getParameter(sParam);
         if(sLine == null)
            bRun = false;

         sLineList[iTabCount] = sLine;

         sParam = "URL" + sParam.valueOf(iTabCount);
         sLine = getParameter(sParam);
         if(sLine == null)
            bRun = false;

         sUrlList[iTabCount] = sLine;

         sParam = "Title" + sParam.valueOf(iTabCount);
         sLine = getParameter(sParam);
         if(sLine == null)
            sLine = "";

         sWindow[iTabCount] = sLine;

         iTabCount++;
      }
      iTabCount--;
   }


   public void paint(Graphics g)
   {
      Dimension d = size();
      int iYper, i, iXpos, iOffset;

      iYper = d.height / iTabCount;

      for(i = 0; i < iTabCount; i++)
      {
         if (i == iCurrentTab)
            iXpos = 0;
         else
            iXpos = 8;

         g.setColor(BGColor);

         if (i+1 == iTabCount)
            g.fillRect(0, i * iYper, d.width, d.height);
         else
            g.fillRect(0, i * iYper, d.width, iYper);

         g.draw3DRect(iXpos, i * iYper, d.width + 2, iYper - 1, true);

         if(i == iCurrentTab)
            g.draw3DRect(iXpos + 1, ( i * iYper ) + 1, d.width + 2, iYper - 3, true );

         if( i == iCurrentTab )
         {
            g.setColor(HighColor);
            g.setFont( fBoldFont );
         }
         else
         {
            g.setFont( fPlainFont );
            g.setColor( TextColor );
         }

         iOffset = ( iYper - iFontSize ) / 2;
         g.drawString( sLineList[ i ], 8 + iXpos, ( i * iYper ) + iOffset + iFontSize );
      }
   }

   public boolean mouseDown(Event evt, int x, int y)
   {

      Dimension d = size();
      int iY;
      URL url;

      iY = 0;
      iCurrentTab = 0;

      while(iY < y)
      {
         iCurrentTab++;
         iY += (d.height / iTabCount);
      }
      iCurrentTab--;
      repaint();

      try
      {
         url = new URL(getDocumentBase(), sUrlList[iCurrentTab]);
      }
      catch(MalformedURLException e)
      {
         url = null;
      }

      if( url == null )
      {
         try
         {
            url = new URL(sUrlList[iCurrentTab]);
         }
         catch(MalformedURLException e)
         {
            url = null;
         }
      }
      if(url != null)
      {
         getAppletContext().showDocument(url, sWindow[iCurrentTab]);
      }

      return true;
   }



   public void update(Graphics g)
   {
      paint( g );
   }


   public Color readColor(String aColor, Color aDefault)
   {
      int rgbValue;

      if ((aColor == null) || (aColor.charAt(0) != '#' ) || ( aColor.length() != 7 ) )
      {
         return aDefault;
      }

      try
      {
         rgbValue = Integer.parseInt(aColor.substring(1, 7), 16);
         return new Color(rgbValue);
      }
      catch(NumberFormatException e)
      {
         return aDefault;
      }
   }
}


