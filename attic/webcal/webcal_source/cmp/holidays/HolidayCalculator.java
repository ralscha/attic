package cmp.holidays;

// HolidayCalculator.java CMP Holiday Calculator
/*

HolidayCalculator 1.3

 Enter a year, and it displays the dates of all the holidays in that year.
 If a holiday was not celebrated in a given year, it will not show.

 The classes it uses for calculating holidays can be easily
 cannibalised for your own applications.

 This calculator does not demonstrate the use of the IsHoliday class to
 rapidly determine if a given day is a holiday.

*/

/**
 * @author  copyright (c) 1999 Roedy Green of Canadian Mind Products
 * may be copied and used freely for any purpose but military.
 *
 * Shareware: If you use any of the Holiday classes for more that casual use, please
 * remit $10 to the address below in Canadian or US funds.  That fee covers use of
 * all the classes in the Holidays package.
 *
 *
 * Roedy Green
 * Canadian Mind Products
 * 5317 Barker Avenue
 * Burnaby, BC Canada V5H 2N6
 * tel: (604) 435-3052
 * mailto:roedy@mindprod.com
 * http://mindprod.com
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import cmp.business.Misc;
import cmp.business.BigDate;
import java.io.*;

/*
 *  version 1.3 1999 October 24
 *                   - add Robbie Burns Day
 *  version 1.2 1999 September 17
 *                   - reorg so each holiday calculator is a separate class
 *                   - add equinox and solstice calcultors.
 *  version 1.1 1999 September 10
 *                   - add St Patricks Day and Commonwealth Day
 *  version 1.0 1999 September 9
 *                   - to show off the Holidays class
 */

public class HolidayCalculator extends Applet {

   private static final String EmbeddedCopyright =
   "Copyright 1999 Roedy Green, Canadian Mind Products, http://mindprod.com";



   // Use our own colours so Symantec won't mess with them or create dups.
   private static final Color black = Color.black;
   private static final Color blue =  Color.blue;
   private static final Color darkGreen = new Color(0, 128, 0);
   private static final Color red =   Color.red;
   private static final Color white = Color.white;

   // DECLARE_CONTROLS

   java.awt.TextArea    display;
   java.awt.TextField   theYear;
   java.awt.Choice      shift;
   java.awt.Choice      terse;

   java.awt.Label       theYearLabel;
   java.awt.Label       title;
   java.awt.Label       instructions;

   /**
    * Inner class to act as listener to respond to all end-user actions.
    */
   class TheListener implements java.awt.event.ActionListener, java.awt.event.ItemListener {

      public void actionPerformed(java.awt.event.ActionEvent event) {
         Object object = event.getSource();
         if ( object == theYear ) {
            refresh();
         }
      } // end actionPerformed

      /**
       * Notice any change to shift choice
       *
       * @param event details of just what the user clicked.
       *
       */
      public void itemStateChanged(java.awt.event.ItemEvent event) {
         Object object = event.getSource();
         if ( object == shift || object == terse ) {
            refresh();
         } // end if
      } // end itemStateChanged

   } //end TheListener

   static String[] monthsOfTheYear = { "unknown", "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"};


   static String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
   };


   cmp.holidays.HolInfo[] holidayDelegate;

   /**
    * prepare list of all holidays in given year
    */
   String displayHolidays(int year, boolean shifted, boolean verbose) {
      String result = "";
      for ( int i = 0; i < holidayDelegate.length; i++ ) {
         HolInfo h = holidayDelegate[i];
         if ( h == null )
            continue;
         BigDate d = new BigDate(h.when(year, shifted));
         if ( d.getOrdinal() == BigDate.NULL_ORDINAL )
            continue;
         result += daysOfTheWeek[d.getDayOfWeek()] + " " + monthsOfTheYear[d.getMM()] + " " + d.getDD() + " is " + (shifted ? " nearest weekday to " : "") + h.getName() + ".\n";
         if ( verbose ) {
            result += h.getRule() + "\n";
            int yearFirstCelebrated = h.getFirstYear(HolInfo.CELEBRATED);
            int yearFirstProclaimed = h.getFirstYear(HolInfo.PROCLAIMED);
            result += "first celebrated " + yearToDisplay(yearFirstCelebrated) + ". ";
            if ( yearFirstCelebrated != yearFirstProclaimed ) {
               result += "first proclaimed " + yearToDisplay(yearFirstProclaimed) + ".";
            }
            result += "\n";
            String authority = h.getAuthority();
            if ( authority.length() > 0 )
               result += "authority: " + authority + "\n";
            result += "\n";
         }
      } // end for
      return result;
   }

   /**
    * This method was created in VisualAge.
    */

   /* get list of holidays from properties file:
      cmp.holidays.Holiday.properties which lives in the jar.
      Class names are case sensitive:
      yes=include no=ignore
      file looks like this:
      Christmas=yes
      GroundhogDay=no
      */
   void findHolidays() {
      String[][] result = null;
      try {
         // look in jar, and on classpath for cmp.holidays.Holiday.properties
         InputStream fis = HolidayCalculator.class.getResourceAsStream("Holiday.properties");
         result = Misc.loadProperties(fis);
      } catch ( IOException oops ) {
         System.out.println(oops + " Problem accessing Holiday.properties file.");
         System.exit(1);
      }

      // in pairs className=yes/no

      int length = result[0].length;
      int j = 0;
      holidayDelegate = new HolInfo[length];
      for ( int i = 0; i < length; i++ ) {
         try {
            if ( result[1][i].equalsIgnoreCase("yes") ) {
               holidayDelegate[j++] = (HolInfo) (Class.forName("cmp.holidays." + result[0][i]).newInstance());
            }
         } catch ( Exception oops ) {
            System.out.println(oops + " Bug in Holiday.properties or class file for " + result[0][i]);
            System.exit(1);
         }
      } // end for

   }

   /**
    * start the applet
    */
   public void init() {
      setBackground(white);
      if ( !Misc.isJavaVersionOK(1, 1, 0) ) {
         System.out.println("You need Java 1.1.0 or later to run this Applet.");
         System.out.println("You are running under " + System.getProperty("java.version"));
         System.exit(1);
      }
      GridBagLayout gridBagLayout;
      gridBagLayout = new GridBagLayout();
      GridBagConstraints gbc;
      setLayout(gridBagLayout);

      // Layout looks like this:
      //     0       1          2
      // 0 -------title-----------
      // 1 shift   terse      1999
      // 2                   year
      // 3 -------results---------
      // 4 -------instructions----
      //     0       1          2

      title = new java.awt.Label("CMP Holiday Calculator 1.3", Label.CENTER);
      title.setFont(new Font("Dialog", Font.BOLD, 18));
      title.setForeground(red);
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 3;
      gbc.fill = GridBagConstraints.NONE;
      gbc.insets = new Insets(10, 10, 5, 10);
      ((GridBagLayout) getLayout()).setConstraints(title, gbc);
      add(title);

      shift = new java.awt.Choice();
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.weightx = 25.0;
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 10, 0, 5);
      gbc.ipadx = 150;
      ((GridBagLayout) getLayout()).setConstraints(shift, gbc);
      add(shift);

      terse = new java.awt.Choice();
      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.weightx = 25.0;
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 5, 0, 5);
      gbc.ipadx = 150;
      ((GridBagLayout) getLayout()).setConstraints(terse, gbc);
      add(terse);

      theYear = new java.awt.TextField(30);
      gbc = new GridBagConstraints();
      gbc.gridx = 2;
      gbc.gridwidth = 1;
      gbc.gridy = 1;
      gbc.weightx = 75.0;
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 5, 0, 10);
      gbc.ipadx = 150;
      ((GridBagLayout) getLayout()).setConstraints(theYear, gbc);
      add(theYear);

      theYearLabel = new java.awt.Label("year", Label.CENTER);
      theYearLabel.setFont(new Font("Dialog", Font.BOLD, 15));
      theYearLabel.setForeground(blue);
      gbc = new GridBagConstraints();
      gbc.gridx = 2;
      gbc.gridy = 2;
      gbc.fill = GridBagConstraints.NONE;
      gbc.insets = new Insets(0, 5, 0, 10);
      ((GridBagLayout) getLayout()).setConstraints(theYearLabel, gbc);
      add(theYearLabel);

      display = new java.awt.TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
      display.setEditable(false);
      // Serif has best chance of supporting Unicode.
      // So far only NT+Internet Explorer works
      display.setFont(new Font("Serif", Font.PLAIN, 15));
      display.setForeground(black);
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.gridwidth = 3;
      gbc.weightx = 0;
      gbc.weighty = 100;
      gbc.fill = GridBagConstraints.BOTH;
      // Top, Left, Bottom, Right
      gbc.insets = new Insets(10, 10, 10, 10);
      ((GridBagLayout) getLayout()).setConstraints(display, gbc);
      add(display);

      instructions = new java.awt.Label("Select options, enter year (negative for BC) and hit enter.", Label.CENTER);
      instructions.setForeground(darkGreen);
      gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 4;
      gbc.gridwidth = 3;
      gbc.fill = GridBagConstraints.NONE;
      gbc.insets = new Insets(0, 10, 10, 5);
      ((GridBagLayout) getLayout()).setConstraints(instructions, gbc);
      add(instructions);


      // add legal choices to boxes
      shift.addItem("actual");
      shift.addItem("nearest weekday");
      shift.select(0);
      terse.addItem("terse");
      terse.addItem("verbose");
      terse.select(0);
      theYear.setText(Integer.toString(1999));
      findHolidays();
      refresh();

      // hook up listener
      TheListener theListener = new TheListener();
      theYear.addActionListener(theListener);
      shift.addItemListener(theListener);
      terse.addItemListener(theListener);
      this.validate();
      this.setVisible(true);
   } // end init

   /**
    * Allow this applet to run as as application as well.
    *
    * @param args command line arguments ignored.
    */
   static public void main(String args[]) {
      final HolidayCalculator applet = new HolidayCalculator();
      Frame frame = new Frame("Holiday Calculator");
      frame.setSize(600 /* width */, 400 /* height */);
      applet.init();
      frame.add(applet);
      frame.validate();
      frame.setVisible(true);
      applet.start();
      frame.addWindowListener
      (
      new java.awt.event.WindowAdapter()
      {
         /**
          * Handle request to shutdown.
          *
          * @param e event giving details of closing.
          */
         public void windowClosing(java.awt.event.WindowEvent e)
         {
            applet.stop();
            applet.destroy();
            System.exit(0);
         } // end WindowClosing
      } // end anonymous class
      ); // end addWindowListener line
   } // end main

   /**
    *  Refresh screen after shift or number change.
    *  Protected so inner class can call it.
    */
   protected void refresh ( ) {
      int year = 1999;
      try {
         year = (int) Math.min(Misc.parseDirtyLong(theYear.getText()), BigDate.MAX_YEAR);
      } catch ( NumberFormatException e ) {

      }
      // avoid illegal year 0
      if ( year == 0 ) year = 1999;
      theYear.setText(Integer.toString(year));
      display.setText(displayHolidays(year,
                                      shift.getSelectedIndex() != 0,
                                      terse.getSelectedIndex() != 0));

   } // end refresh

   /**
    * This method was created in VisualAge.
    * @return String display of year BC for negative years
    * @param year int
    */
   static String yearToDisplay(int year) {
      if ( year < 0 )
         return Integer.toString(-year) + " BC";
      else if ( year == 0 )  return "";
      else return Integer.toString(year);
   }
}