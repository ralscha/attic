// cmp.Quoter.Align
package cmp.quoter;

/**
  * Align converts tabs and other control chars to spaces, and aligns columns.
  * No special treatment for Java comments, 'xxx'.
  * Columns are comma, tab, or space delimited.
  * Text enclosed in quotes "abc def" is considered a single column.
  */
public class Align {

   static final boolean debugging = false;


   /**
    * Aligns text into columns.
    * Columns are delimited by space, tabs, commas or other control characters.
    * Column breaking IGNORE the
    * Java source code conventions, e.g. string literals and comments.
    * I.e. There is No special treatment for Java comments, 'xxx'.
    * Text enclosed in quotes "abc def" is considered a single column.*
    * @param raw input to be aligned.  String typically with embedded \ns.
    * May or may not have a terminal \n.
    *
    * @return raw input aligned in columns.  If raw had a terminal \n, result will too,
    * otherwise it will not. No commas will be added or removed.
    */
   String align ( String raw ) {
      if ( raw == null ) {
         return null;
      }
      this.raw = raw;
      cooked = new StringBuffer(raw.length() * 2);

      /* calculate how wide each column is and store it in
       * biggestWidth[colIndex] */

      /* zero out column widths */
      for ( int colIndex = 0; colIndex < 40; colIndex++ )
         biggestWidth[colIndex] = 0;
      cols = 0;

      pass=1;
      doAPass();

      /* pad the column widths to put a little space between
       * the columns */
      for ( int colIndex = 0; colIndex < cols; colIndex++ )
         biggestWidth[colIndex] += padding;

      /* repass the file, this time
       * appending to the cooked StringBuffer */
      pass=2;
      doAPass();
      return cooked.toString();

   } // end align

   /* ==================================== */

   // enumerations for blankState
   static final int IN_LEADING = 0;
   static final int IN_MIDDLE = 1;
   static final int IN_TRAILING = 2;
   /**
    * Calculate how wide each column is and store it in
    * biggestWidth[colIndex] on pass1.  First column in index
    * 0.
    * On pass2, generate the output by appending to cooked StringBuffer.
    */
   void  doAPass() {

      /*
       blankState - we implement the algorithm as a finite state machine.
       Don't confuse it with quotedState inside categorise.
      =IN_LEADING reading leading blanks on a field.
      =IN_MIDDLE reading non-blanks or quotes in middle of a field.
      =IN_TRAILING reading trailing blanks.

        when call endField
    ----------o---o--o--------o
    ____xxxx__xx__,__,__xxx___
    ----o-----o---o--o--------
        when call startField
        */
      int blankState = IN_LEADING;
      quotedState = OUTSIDEQUOTES;
      startLine();
      int rawLen = raw.length();
      for ( int i=0; i<rawLen; i++ ) {
         char c = raw.charAt(i);
         switch ( categorise(c) ) {
         case WHITESPACE:        /* blanks */
            switch ( blankState ) {
            case IN_LEADING:
               blankState = IN_LEADING;
               break;
            case IN_MIDDLE:
               blankState = IN_TRAILING;
               break;
            case IN_TRAILING:
               blankState = IN_TRAILING;
               break;
            }
            break;

         case COMMA:
            switch ( blankState ) {  /* comma */
            case IN_LEADING:
               startField();       /* null field */
               inField(c);
               endField();
               blankState = IN_LEADING;
               break;
            case IN_MIDDLE:
               inField(c);
               endField();
               blankState = IN_LEADING;
               break;
            case IN_TRAILING:
               inField(c);
               endField();
               blankState = IN_LEADING;
               break;
            }  // end switch
            break;

         case ORDINARY:
            switch ( blankState ) {  /* ordinary non-blank */
            case IN_LEADING:
               startField();
               inField(c);
               blankState = IN_MIDDLE;
               break;
            case IN_MIDDLE:
               inField(c);
               blankState = IN_MIDDLE;
               break;
            case IN_TRAILING:
               endField();
               startField();
               inField(c);
               blankState = IN_MIDDLE;
               break;
            }
            break;

         case QUOTED:             /* something in quoted string */
            switch ( blankState ) {
            case IN_LEADING:
               startField();
               inField(c);
               blankState = IN_MIDDLE;
               break;
            case IN_MIDDLE:
               inField(c);
               blankState = IN_MIDDLE;
               break;
            case IN_TRAILING:
               endField();
               startField();
               inField(c);
               blankState = IN_MIDDLE;
               break;
            }
            break;

         case NEWLINE:           /* new line */
            switch ( blankState ) {
            case IN_LEADING:
               break;
            case IN_MIDDLE:
               endField();
               break;
            case IN_TRAILING:
               endField();
               break;
            }
            endLine();
            startLine();
            blankState = IN_LEADING;
            break;
         } // end switch on char
      }  // end for

      // nothing special needed to simulate NEWLINE processing
      // if there isn't one at the very end.
      // if input is missing terminal \n, so will the output.
   }  // end doAPass

   /* ==================================== */

   /**
    * We have just started a new line
    */
   void startLine() {
      colIndex = -1;
      width = 0;
   }

   /* ==================================== */

   void endLine() {
      switch ( pass ) {
      case 1:
         break;
      case 2:
         cooked.append('\n');
         break;
      }
   }

   /* ==================================== */

   /**
    * Field may have lead and trail spaces on it.  We have just
    * hit the first non-blank. */

   void startField() {
      switch ( pass ) {
      case 1:
         width = 0;
         if ( ++colIndex > (cols - 1) )
            cols = colIndex + 1;
         break;
      case 2:
         width = 0;
         ++colIndex;
         break;
      }

   }  // end startField

   /* ==================================== */


   /**
    * Field may have lead and trail spaces on it. This is
    * called to process non-space chars in the middle of a
    * field.
    */
   void inField(char c)

   {
      switch ( pass ) {
      case 1:
         if ( ++width > biggestWidth[colIndex] )
            biggestWidth[colIndex] = width;
         break;
      case 2:
         ++width;
         cooked.append(c);
         break;
      }
   } // end inField

   /* ==================================== */


   /**
    * Field may have lead and trail spaces on it.   We just hit
    * the first space etc. after the last non-blank.
    */

   void endField() {
      switch ( pass ) {
      case 1:
         break;
      case 2:
         /* pad all but the last column
          * with spaces */
         if ( colIndex < (cols - 1) ) {

            for (
                width = biggestWidth[colIndex] - width;  // how many chars too short we are.
                width>0;
                width-- ) {

               /* pad column on right
                * with spaces */
               cooked.append(' ');
            } // end for
         } // end if
         break;
      } // end switch
   } // end endField


   /* ==================================== */

   /**
    * categorise the character, in a context sensitive way,
    * thinking in terms of parsing Java source code.
    * @param c char to categorise.
    * @return  COMMENT, QUOTED, ORDINARY, WHITESPACE, NEWLINE
    */
   int categorise(char c)

   /** accept a character and categorise it.
    *
    * COMMENT -- inside // or /* comment
    * QUOTED   -- inside single or double quote string
    * ORDINARY    -- normal code
    * WHITESPACE -- whitespace in code.
    *              Whitespace in comments and quotes counts as comment or
    *              quote.
    * NEWLINE -- newline character.  Newline inside comment counts
    *              as comment.
    *
    * Comments require two chars to start them.  The first char will be
    * considered code, and only the second as comment.
    */

   {

      /** quotedState remembered between calls.  We implement this as yet another
          finite state automaton. Don't confuse it with blankState used by doAPass.
       = OUTSIDEQUOTES normal C code
       = INSIDEQUOTES inside a " "
       */

      switch ( c ) {

      case '\n':  /* new line */
         switch ( quotedState ) {
         case OUTSIDEQUOTES:         /* normal code */
            quotedState = OUTSIDEQUOTES;
            return (NEWLINE);
         case INSIDEQUOTES:         /* inside a " " */
            quotedState = OUTSIDEQUOTES;
            return (NEWLINE);
         }

      case ' ':                   /* blanks */
      case  0x00:                 /* all control chars, except =\n */
      case  0x01:
      case  0x02:
      case  0x03:
      case  0x04:
      case  0x05:
      case  0x06:
      case  0x07:  /* \n */
      case  0x08:
      case  0x09:
         // not  0x0A:  \n
      case  0x0B:
      case  0x0C:
      case  0x0D:   /* \r */
      case  0x0E:
      case  0x0F:
      case  0x10:
      case  0x11:
      case  0x12:
      case  0x13:
      case  0x14:
      case  0x15:
      case  0x16:
      case  0x17:
      case  0x18:
      case  0x19:
      case  0x1A:
      case  0x1B:
      case  0x1C:
      case  0x1D:
      case  0x1E:
      case  0x1F:

         switch ( quotedState ) {
         case OUTSIDEQUOTES:         /* normal code */
            quotedState = OUTSIDEQUOTES;
            return (WHITESPACE);
         case INSIDEQUOTES:         /* inside a " " */
            quotedState = INSIDEQUOTES;
            return (QUOTED);
         }

      case '\"':                  /* double quote */
         switch ( quotedState ) {
         case OUTSIDEQUOTES:         /* normal code */
            quotedState = INSIDEQUOTES;
            return (QUOTED);
         case INSIDEQUOTES:         /* inside a " " */
            quotedState = OUTSIDEQUOTES;
            return (QUOTED);
         }

      case ',': /* comma, very similar to default */
         switch ( quotedState ) {
         case OUTSIDEQUOTES:         /* normal code */
            quotedState = OUTSIDEQUOTES;
            return (COMMA);
         case INSIDEQUOTES:         /* inside a " " */
            quotedState = INSIDEQUOTES;
            return (QUOTED);
         }


      default:                    /* non blank */

         switch ( quotedState ) {
         case OUTSIDEQUOTES:         /* normal code */
            quotedState = OUTSIDEQUOTES;
            return (ORDINARY);
         case INSIDEQUOTES:         /* inside a " " */
            quotedState = INSIDEQUOTES;
            return (QUOTED);
         } // end switch
      }  /* end outer switch(c) */

      // Theoretically you can't get here
      throw   new IllegalArgumentException("categorise bug");

   }   /* end categorise */

   // quotedState enumerations
   static final int INSIDEQUOTES = 0;
   static final int OUTSIDEQUOTES = 1;

   // character category enumerations:

   static final int COMMENT=0;
   static final int QUOTED=1;
   static final int ORDINARY=2;
   static final int COMMA=3;
   static final int WHITESPACE=4;
   static final int NEWLINE=5;



   /**
    * the raw text we are processing
    */
   String raw;

   /* which field/column we are
    * working on. 0 is first */
   int colIndex;

   /* width of current column */
   int width;


   /* how many columns there are */
   int cols;

   /* pass=1 when deciding col
    * widths, and pass=2 when
    * outputting */
   int pass = 1;



   // how much blank space to put between the columns

   static final int padding = 2;

   // widths of the columns

   int[] biggestWidth = new int[40];


   /**
    * state of finite state automaton that categorises characters
    */
   int quotedState;

   /**
    * where we accumulate the output
    */
   StringBuffer cooked;


   public static void main(String[] args) {
      if ( debugging ) {

         Align a = new Align();
         String raw="  abc def 999\n"
                    + "    abc     def 999\n";
         System.out.println(raw);
         System.out.println(a.align(raw));

         raw = "  abc, def,999\n";
         System.out.println(raw);
         System.out.println(a.align(raw));

         raw = "  \"abc  88\", def,999 0000000000000000    \n"
               + "    abc     def 999 /* this is a comment*/  followed\n"
               + " abc // stuff to do\n"
               + "    abc     def 999\n";
         System.out.println(raw);
         System.out.println(a.align(raw));

         raw = "  \"abc ,, 88\"   , ,def,999 0000000000000000    \n"
               + "    abc     def 999 /* this is a comment*/  followed\n"
               + " abc // stuff to do\n";
         System.out.println(raw);
         System.out.println(a.align(raw));


         raw = " abc def ghi\n";
         System.out.println(raw);
         System.out.println(a.align(raw));


         raw = " abc,def, ghi,    hef";
         System.out.println(raw);
         System.out.println(a.align(raw));



      } // end if debugging
   } // end main

} // end class Align


