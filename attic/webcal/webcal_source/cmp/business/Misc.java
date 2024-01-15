package cmp.business;

// Misc.java
import java.io.*;

/**
  * Misc.java
  * Miscellaneous static methods used by CMP methods
  *
  * Copyright (c) 1997-1999
  * Roedy Green
  * Canadian Mind Products
  * 5317 Barker Avenue
  * Burnaby, BC Canada V5H 2N6
  * tel: (604) 435-3052
  * mailto:roedy@mindprod.com
  * http://mindprod.com
  */


/*
 * version 1.3 1999 August 24, add leftPad, rightPad, smarter rep.
 *                             isJavaVersionOK now handles 1.3beta.
 * version 1.2 1998 December 14, add isJavaVersionOK
 * version 1.1 1998 November 10, add dates
 * version 1.0 1997 March 23, initial.
 */

public class Misc {

   static final boolean debugging = false;

   /**
     * Misc contains only static methods.  You cannot instantiate
     * a Misc object.
     */
   private Misc() {

   }

   /**
     * makeshift system beep if awt.Toolkit.beep is not available.
     * Works also in JDK 1.02.
     */
   public final static void beep () {
      System.out.print("\007");
      System.out.flush();
   } //end beep

   /**
     * Convert String to canonical standard form.
     * null -> "".
     * Trims lead trail blanks.
     * @param s String to be converted.
     * @return String in canonical form.
     */
   public final static String canonical( String s ) {
      if ( s == null ) return "";
      else return s.trim();
   } // end canonical

   /**
     * Caps the max value, ensuring it does not go too high.
     * alias for min.
     * @see Math#min
     * @param v the value
     * @param high the high bound above which v cannot go.
     * @return the lesser of v and high.
     */
   public final static int cap (int v, int high) {
      if ( v > high ) return high;
      else          return v;
   } // end cap

   /**
     * Corrals a value back into safe bounds.
     * @param v the value
     * @param low the low bound below which v cannot go.
     * @param high the high bound above which v cannot go.
     * @return low if v < low, high if v > high, but normally just v.
     */
   public final static int corral (int v, int low, int high) {
      if ( v < low )  return low;
      else if ( v > high ) return high;
      else               return v;
   } // end corral

   /**
     * Ensures a value does not go too low.
     * alias for max
     * @see Math#max
     * @param v the value
     * @param low the low bound below which v cannot go.
     * @return the greater of v and low.
     */
   public final static int hem (int v, int low) {
      if ( v < low ) return low;
      else         return v;
   } // end hem

   /**
     * Is this string empty?
     * @param s String to be tested for emptiness.
     * @return true if the string is null or equal to the "" null string.
     * or just blanks
     */
   public final static boolean isEmpty ( String s ) {
      return (s == null) ? true : s.trim().length() == 0;
   } // end isEmpty

   /**
    * Ensures Java runtime version e.g. 1.1.7 is sufficiently recent.
    * @param wantedMajor java major version e.g. 1
    * @param wantedMinor Java minor version e.g. 1
    * @param wantedBugFix Java bugfix version e.g. 7
    * return true if JVM version is equal to or more recent than
    * (higher than) the level specified.
    * Based on code by Dr. Tony Dahlman <adahlman@jps.net>
    */
   static public boolean isJavaVersionOK( int wantedMajor, int wantedMinor, int wantedBugFix) {

      try {
         try {
            // java.version will have form 1.1.7A, 11, 1.1., 1.1 or 1.3beta
            // It may be gibberish. It may be undefined.
            // We have do deal with all this malformed garbage.
            // Because incompetents run the world,
            // it is not nicely formatted for us in three fields.
            String ver = System.getProperty("java.version");

            if ( ver == null ) {
               return false;
            }

            ver = ver.trim();

            if ( ver.length() < 2 ) {
               return false;
            }

            int dex = ver.indexOf('.');

            if ( dex < 0 ) {
               // provide missing dot
               ver = ver.charAt(0) + '.' + ver.substring(1);
               dex = 1;
            }

            int gotMajor = Integer.parseInt(ver.substring(0, dex));

            if ( gotMajor < wantedMajor ) return false;
            if ( gotMajor > wantedMajor ) return true;

            // chop off major and first dot.
            ver = ver.substring( dex + 1 );


            // chop trailing "beta"
            if ( ver.endsWith("beta") ) {
               ver = ver.substring(0, ver.length() - "beta".length());
            }

            // chop any trailing letter as in 1.1.7A,
            // but convert 1.1.x or 1.1.X to 1.1.9
            char ch = ver.charAt(ver.length()-1);
            if ( ! Character.isDigit(ch) ) {
               ver = ver.substring(0, ver.length()-1);
               if ( ch == 'x' || ch == 'X' ) ver += '9';
            }
            // check minor version
            dex = ver.indexOf('.');
            if ( dex < 0 ) {
               // provide missing BugFix number as in 1.2 or 1.0
               ver += ".0";
               dex = ver.indexOf('.');
            }

            int gotMinor = Integer.parseInt( ver.substring(0, dex) );
            if ( gotMinor < wantedMinor )  return false;
            if ( gotMinor > wantedMinor )  return true;

            // check bugfix version
            ver = ver.substring( dex + 1 );
            int gotBugFix = Integer.parseInt( ver );
            return ( gotBugFix >= wantedBugFix );

         } catch ( NumberFormatException e ) {
            return false;
         } // end catch

      } catch ( StringIndexOutOfBoundsException e ) {
         return false;
      } // end catch

   } // end isJavaVersionOK

   /**
     * Check if char is plain ASCII lower case.
     * @see Character#isLowerCase
     * @param c char to check
     * @return true if char is in range a..z
     */
   public final static boolean isUnaccentedLowerCase( char c ) {
      return 'a' <= c && c <= 'z';
   } // isUnaccentedLowerCase

   /**
     * Check if char is plain ASCII upper case.
     * @see Character#isUpperCase
     * @param c char to check.
     * @return true if char is in range A..Z.
     */
   public final static boolean isUnaccentedUpperCase( char c ) {
      return 'A' <= c && c <= 'Z';
   } // end isUnaccentedUpperCase

   /**
    * Pads the string out to the given length by applying blanks on the left.
    *
    * @param s String to be padded/chopped.
    *
    * @param newLen length of new String desired.
    *
    * @param true if Strings longer than newLen should be truncated to newLen chars.
    *
    * @return String padded on left/chopped to the desired length.
    */
   public final static String leftPad( String s, int newLen, boolean chop) {
      int grow = newLen - s.length();
      if ( grow <= 0 ) {
         if ( chop ) {
            return s.substring(0, newLen);
         } else {
            return s;
         }
      } else if ( grow <= 30 ) {
         return "                              ".substring(0, grow) + s;
      } else {
         return rep (' ', grow) + s;
      }
   } // end leftPad

   /**
    * Load a properties file, but not into a Property hashTable, into an array
    * that does not disturb property order.
    * Returns array of string pairs.
    * Closes the given inputstream.
    * Property file might look like something like this:
    * # cmp.InWords.InWords.properties must live in inwords.jar.
    * # Describes languages supported to translate numbers to words.
    * # Fully qualified classname, (without .class)=name on menu (embedded blanks ok)
    * # Everything is case sensitive.
    * cmp.InWords.Indonesian=Bahahasa Indonesia
    * cmp.InWords.BritishEnglish=British
    * cmp.Inwords.Dutch=Dutch
    * cmp.InWords.Esperanto=Esperanto
    * cmp.InWords.AmericanEnglish=North American  Vector
    * cmp.InWords.Norwegian=Norwegian
    * cmp.InWords.Swedish=Swedish
    * #-30-
    */
   public static String[][] loadProperties (InputStream fis) throws IOException {
      // make them big to start, we will shrink them later to fit.
      String [] left = new String[1000];
      String [] right = new String[1000];
      int count = 0;
      // we don't use Properties.load since that would scramble the order.
      StreamTokenizer s = new StreamTokenizer(new BufferedReader(new InputStreamReader(fis)));
      // treat space, alpha, numbers and most punctuation as ordinary char
      s.wordChars(' ','_');
      s.commentChar('#');
      s.whitespaceChars('=','='); // ignore equal, just separates fields
      s.eolIsSignificant(true);

      while ( true ) {
         s.nextToken();
         if ( s.ttype == StreamTokenizer.TT_EOF ) break;
         if ( s.ttype == StreamTokenizer.TT_EOL ) continue;
         left [count] = s.sval.trim();
         s.nextToken();
         right[count] = s.sval.trim();
         count++;
      } // end for

      fis.close();
      // prune back arrays to size
      String [][] result = new String[2][count];
      System.arraycopy(left, 0, result[0], 0, count);
      System.arraycopy(right, 0, result[1], 0, count);
      return result;

   } // end loadProperties

   /**
    * convert a String to a long.  The routine is very forgiving.
    * It ignores invalid chars, lead trail, embedded spaces, decimal points etc.
    * Dash is treated as a minus sign.
    *
    * @param numStr String to be parsed.
    *
    * @return long value of String with junk characters stripped.
    *
    * @exception NumberFormatException if the number is too big to fit in a long.
    */
   public static long parseDirtyLong( String numStr ) {
      numStr = numStr.trim();
      // strip commas, spaces, + etc
      StringBuffer b = new StringBuffer(numStr.length());
      boolean negative = false;
      for ( int i=0; i<numStr.length(); i++ ) {
         char c = numStr.charAt(i);
         if ( c == '-' ) negative = true;
         else if ( '0' <= c && c <= '9' ) {
            b.append(c);
         }
      } // end for
      numStr = b.toString();
      if ( numStr.length() == 0 ) {
         return 0;
      }
      long num = Long.parseLong (numStr);
      if ( negative ) {
         num = -num;
      }
      return num;
   } // end parseDirtyLong



   /**
     * convert a String into long pennies.
     * It ignores invalid chars, lead trail, embedded spaces.
     * Dash is treated as a minus sign.
     * 0 or 2 decimal places are permitted.
     *
     * @param numStr String to be parsed.
     *
     * @return long pennies.
     *
     * @exception NumberFormatException if the number is too big to fit in a long.
     */
   public static long parseLongPennies( String numStr ) {
      numStr = numStr.trim();
      // strip commas, spaces, + etc
      StringBuffer b = new StringBuffer(numStr.length());
      boolean negative = false;
      int decpl = -1;
      for ( int i=0; i<numStr.length(); i++ ) {
         char c = numStr.charAt(i);
         switch ( c ) {
         case '-': negative = true;
            break;

         case '.':
            if ( decpl == -1 ) {
               decpl = 0;
            } else {
               throw new NumberFormatException("more than one decimal point");
            }
            break;

         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
            if ( decpl != -1 ) {
               decpl++;
            }
            b.append(c);
            break;

         default:
            // ignore junk chars
            break;
         } // end switch

      } // end for
      if ( numStr.length() != b.length() ) {
         numStr = b.toString();
      }

      if ( numStr.length() == 0 ) {
         return 0;
      }
      long num = Long.parseLong (numStr);
      if ( decpl == -1 || decpl == 0 ) {
         num *= 100;
      } else if ( decpl == 2 ) { /* it is fine as is */

      }

      else {
         throw new NumberFormatException("wrong number of decimal places.");
      }

      if ( negative ) {
         num = -num;
      }
      return num;
   } // end parseLongPennies

   /**
    * convert pennies to a string with a decorative decimal point.
    * @param long amount in pennies.
    * @return amount with decorative decimal point, but no lead $.
    */
   public final static String penniesToString (long pennies) {
      boolean negative;
      if ( pennies < 0 ) {
         pennies = -pennies;
         negative = true;
      } else {
         negative = false;
      }
      String s = Long.toString(pennies);
      int len = s.length();
      switch ( len ) {
      case 1:
         s = "0.0" + s;
         break;
      case 2:
         s = "0." + s;
         break;
      default:
         s = s.substring(0,len-2) + "." + s.substring(len-2,len);
         break;
      } // end switch
      if ( negative ) {
         s = "-" + s;
      }
      return s;
   } // end penniesToString

   /**
     * Extracts a number from a string, returns 0 if malformed.
     * @param s String containing the integer.
     * @return binary integer.
     */
   public final static int pluck (String s ) {
      int result = 0;
      try {
         result = Integer.parseInt(s);
      } catch ( NumberFormatException e ) {

      }
      return result;
   } //end pluck

   /**
     * Produce a String of a given repeating character.
     * @param c the character to repeat
     * @param count the number of times to repeat
     * @return String, e.g. rep('*',4) returns "****"
     */
   public final static String rep (char c, int count) {
      char[] s = new char[count];
      for ( int i=0; i < count; i++ ) {
         s[i] = c;
      }
      return new String(s).intern();
   } // end rep

   /**
    * Pads the string out to the given length by applying blanks on the right.
    *
    * @param s String to be padded/chopped.
    *
    * @param newLen length of new String desired.
    *
    * @param true if Strings longer than newLen should be truncated to newLen chars.
    *
    * @return String padded on left/chopped to the desired length.
    */
   public final static String rightPad( String s, int newLen, boolean chop) {
      int grow = newLen - s.length();
      if ( grow <= 0 ) {
         if ( chop ) {
            return s.substring(0, newLen);
         } else {
            return s;
         }
      } else if ( grow <= 30 ) {
         return s + "                              ".substring(0, grow);
      } else {
         return s + rep (' ', grow);
      }
   } // end rightPad

   /**
    * Remove all spaces from a String.
    * @param s String to strip of blanks.
    * @return String with all blanks, lead/trail/embedded removed.
    */
   public static String squish( String s) {
      if ( s == null ) return null;
      s = s.trim();
      if ( s.indexOf(' ') < 0 ) return s;
      int len = s.length();
      StringBuffer b = new StringBuffer(len-1);
      for ( int i=0; i<len; i++ ) {
         char c;
         if ( (c = s.charAt(i))  != ' ' ) b.append(c);
      } // end for
      return b.toString();
   } // end squish

   /**
    * convert to Book Title case,
    * with first letter of each word capitalised.
    * e.g. "handbook to HIGHER consciousness"
    *   -> "Handbook to Higher Consciousness"
    * e.g. "THE HISTORY OF THE U.S.A."
    *   -> "The History of the U.S.A."
    * e.g. "THE HISTORY OF THE USA"
    *   -> "The History of the Usa"  (sorry about that.)
    * Don't confuse this with Character.isTitleCase which concerns
    * ligatures.
    *
    * @param s String to convert.  May be any mixture of case.
    * @return String with each word capitalised, except embedded
    * words "the" "of" "to"
    */
   public final static String toBookTitleCase( String s ) {
      char[] ca = s.toCharArray();
      // Track if we changed anything so that
      // we can avoid creating a duplicate String
      // object if the String is already in Title case.
      boolean changed = false;
      boolean capitalise = true;
      boolean firstCap = true;
      for ( int i=0; i<ca.length; i++ ) {
         char oldLetter = ca[i];
         if ( oldLetter <= '/'
              || ':' <= oldLetter && oldLetter <= '?'
              || ']' <= oldLetter && oldLetter <= '`' ) {
            /* whitespace, control chars or punctuation */
            /* Next normal char should be capitalised */
            capitalise = true;
         } else {
            if ( capitalise && ! firstCap ) {
               // might be the_ of_ or to_
               capitalise = ! (s.substring(i, Math.min(i+4, s.length())).equalsIgnoreCase("the ")
                               || s.substring(i, Math.min(i+3, s.length())).equalsIgnoreCase("of ")
                               || s.substring(i, Math.min(i+3, s.length())).equalsIgnoreCase("to "));
            } // end if
            char newLetter = capitalise
                             ? Character.toUpperCase(oldLetter)
                             : Character.toLowerCase(oldLetter);
            ca[i] = newLetter;
            changed |= (newLetter != oldLetter);
            capitalise = false;
            firstCap = false;
         } // end if
      } // end for
      if ( changed ) {
         s = new String (ca);
      }
      return s;

   } // end toBookTitleCase

   /**
     * Convert an integer to a String, with left zeroes.
     * @param i the integer to be converted
     * @param len the length of the resulting string.
     * Warning.  It will chop the result on the left if it is too long.
     * @return String representation of the int e.g. 007
     */
   public final static String toLZ( int i, int len) {
      // Since String is final, we could not add this method there.
      String s = Integer.toString(i);
      if ( s.length() > len ) return s.substring(0,len);
      else if ( s.length() < len )
         // pad on left with zeros
         return "000000000000000000000000000000".substring(0,len  - s.length()) + s;
      else return s;
   } // end toLZ

   /**
    * Removes white space from beginning this string.
    * <p>
    * All characters that have codes less than or equal to
    * <code>'&#92;u0020'</code> (the space character) are considered to be
    * white space.
    *
    * @return  this string, with leading white space removed
    */
   public static String trimLeading(String s) {
      if ( s == null ) {
         return null;
      }
      int len = s.length();
      int st = 0;
      while ( (st < len) && (s.charAt(st) <= ' ') ) {
         st++;
      }
      return (st > 0) ? s.substring(st, len) : s;
   } // end trimLeading


   /**
     * Removes white space from end this string.
     * <p>
     * All characters that have codes less than or equal to
     * <code>'&#92;u0020'</code> (the space character) are considered to be
     * white space.
     *
     * @return  this string, with trailing white space removed
     */
   public static String trimTrailing(String s) {
      if ( s == null ) {
         return null;
      }
      int len = s.length();
      int origLen = len;
      while ( ( len > 0) && (s.charAt(len - 1) <= ' ') ) {
         len--;
      }
      return (len != origLen) ? s.substring(0, len) : s;
   }  // end trimTrailing

   /**
    * Test harness, used in debugging
    */
   public static void main (String[] args) {
      if ( false ) {
         System.out.println(Misc.parseLongPennies("$5.00"));
         System.out.println(Misc.parseLongPennies("$50"));
         System.out.println(Misc.parseLongPennies("50"));
         System.out.println(Misc.parseLongPennies("$50-"));

         System.out.println(Misc.penniesToString(0));
         System.out.println(Misc.penniesToString(-1));
         System.out.println(Misc.penniesToString(20));
         System.out.println(Misc.penniesToString(302));
         System.out.println(Misc.penniesToString(-100000));
         System.out.println(Misc.toBookTitleCase("handbook to HIGHER consciousness"));
         System.out.println(Misc.toBookTitleCase("THE HISTORY OF THE U.S.A."));
         System.out.println(Misc.toBookTitleCase("THE HISTORY OF THE USA"));

         System.out.println(Misc.rightPad("abc", 6,  true)+"*");
         System.out.println(Misc.rightPad("abc", 2,  true)+"*");
         System.out.println(Misc.rightPad("abc", 2,  false)+"*");
         System.out.println(Misc.rightPad("abc", 3,  true)+"*");
         System.out.println(Misc.rightPad("abc", 3,  false)+"*");
         System.out.println(Misc.rightPad("abc", 0,  true)+"*");
         System.out.println(Misc.rightPad("abc", 20, true)+"*");
         System.out.println(Misc.rightPad("abc", 29, true)+"*");
         System.out.println(Misc.rightPad("abc", 30, true)+"*");
         System.out.println(Misc.rightPad("abc", 31, true)+"*");
         System.out.println(Misc.rightPad("abc", 40, true)+"*");
      }
   } // end main

} // end class Misc


