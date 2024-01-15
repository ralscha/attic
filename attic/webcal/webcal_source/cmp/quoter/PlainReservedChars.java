package cmp.quoter;

public class PlainReservedChars {
   /**
    * Removes embedded control chars, except \n
    * @param raw raw text to be cooked.  Must not be null.
    * @param table translate table of array of 256 strings to translate chars to.
    */
   public static String toQuoted (String raw, String[] table) {
      if ( raw == null || raw.length() == 0 ) {
         return null;
      }
      if ( table == null ) {
         table = latin1;
      }
      StringBuffer cooked = new StringBuffer(raw.length());
      //
      for ( int i=0; i< raw.length(); i++ ) {
         char c = raw.charAt(i);
         if ( c < 256 ) {
            cooked.append(table[c]);
         } else {
            cooked.append('?');
         }
      }
      return cooked.toString();

   } // end toQuoted

   // translation table for char to non-control char.
   public static final String[] latin1 = new String [256];
   static {

      // initialize the latin1 array
      for ( int i=0; i<32; i++ ) {
         latin1[i] = "";
      }

      for ( int i=32; i<256; i++ ) {
         latin1[i] = String.valueOf((char)i).intern();
      }

      // overrides
      latin1[7] = "\t";      /* keep tabs */
      latin1[10] = "\n";     /* newline  left alone */
      latin1[13] = "";       /* \r removed */
      latin1[160] = " ";     /* non-breaking space -> ordinary space */

   } // end static init




   // translation table for char to to non-control char.
   public static final String[] win = new String [256];
   static {

      // initialize the win array
      for ( int i=0; i<32; i++ ) {
         win[i] = "";
      }

      for ( int i=32; i<256; i++ ) {
         win[i] = String.valueOf((char)i).intern();
      }

      // overrides
      win[7] = "\t";      /* keep tabs */
      win[10] = "\n";     /* newline  left alone */
      win[13] = "";       /* discard \r */
      win[130] = ",";
      win[131] = "f";
      win[132] = ",,";
      win[133] = "...";
      win[134] = "|";
      win[135] = "=|=";
      win[136] = "^";
      win[137] = "%o";
      win[138] = "S";
      win[139] = "<";
      win[140] = "OE";
      win[145] = "`";
      win[146] = "\'";
      win[147] = "\"";
      win[148] = "\"";
      win[149] = ".";
      win[150] = "-";
      win[151] = "-";
      win[152] = "~";
      win[153] = "(r)";
      win[154] = "s";
      win[155] = ">";
      win[156] = "oe";
      win[159] = "Y";
      win[160] = " ";

   } // end static init



   // translation table for char to HTML string form.
   public static final String[] ibmoem = new String [256];
   static {

      // initialize the ibmoem array

      // unprintable ascii
      for ( int i=0; i<32; i++ ) {
         ibmoem[i] = "";
      }

      // printable ascii
      for ( int i=32; i<127; i++ ) {
         ibmoem[i] = String.valueOf((char)i).intern();
      }

      // high ascii
      for ( int i=127; i<256; i++ ) {
         ibmoem[i] = "?";
      }

      // line drawing set
      for ( int i=176; i<224; i++ ) {
         ibmoem[i] = "*";
      }


      // overrides
      ibmoem[7] = "\t";      /* keep tabs */
      ibmoem[10] = "\n";     /* newline  left alone */
      ibmoem[13] = "";       /* discard \r */


      ibmoem[14] = "(para)";
      ibmoem[15] = "(sect)";

      ibmoem[128] = "\u00c7";     /* &Ccedil; */
      ibmoem[129] = "\u00fc";     /* &uuml; */
      ibmoem[130] = "\u00e9";     /* &eacute; */
      ibmoem[131] = "\u00e2";     /* &acirc; */
      ibmoem[132] = "\u00e4";     /* &auml; */
      ibmoem[133] = "\u00e0";     /* &agrave; */
      ibmoem[134] = "\u00e5";     /* &aring; */
      ibmoem[135] = "\u00e7";     /* &ccedil; */
      ibmoem[136] = "\u00ea";     /* &ecirc; */
      ibmoem[137] = "\u00eb";     /* &euml; */
      ibmoem[138] = "\u00e8";     /* &egrave; */
      ibmoem[139] = "\u00ef";     /* &iuml; */
      ibmoem[140] = "\u00ee";     /* &icirc; */
      ibmoem[141] = "\u00ec";     /* &igrave; */
      ibmoem[142] = "\u00c4";     /* &Auml; */
      ibmoem[143] = "\u00c5";     /* &Aring; */
      ibmoem[144] = "\u00c9";     /* &Eacute; */
      ibmoem[145] = "\u00e6";     /* &aelig; */
      ibmoem[146] = "\u00c6";     /* &AElig; */
      ibmoem[147] = "\u00f4";     /* &ocirc; */
      ibmoem[148] = "\u00f6";     /* &ouml; */
      ibmoem[149] = "\u00f2";     /* &ograve; */
      ibmoem[150] = "\u00fb";     /* &ucirc; */
      ibmoem[151] = "\u00f9";     /* &ugrave; */
      ibmoem[152] = "\u00ff";     /* &yuml; */
      ibmoem[153] = "\u00d6";     /* &Ouml; */
      ibmoem[154] = "\u00dc";     /* &Uuml; */
      ibmoem[155] = "\u00a2";     /* &cent; */
      ibmoem[156] = "\u00a3";     /* &pound; */
      ibmoem[157] = "\u00a5";     /* &yen; */
      ibmoem[158] = "Pt";
      ibmoem[159] = "f";
      ibmoem[160] = "\u00e1";     /* &aacute; */
      ibmoem[161] = "\u00ed";     /* &iacute; */
      ibmoem[162] = "\u00f3";     /* &oacute; */
      ibmoem[163] = "\u00fa";     /* &uacute; */
      ibmoem[164] = "\u00f1";     /* &ntilde; */
      ibmoem[165] = "\u00d1";     /* &Ntilde; */
      ibmoem[166] = "\u00aa";     /* &ordf; */
      ibmoem[167] = "\u00ba";     /* &ordm; */
      ibmoem[168] = "\u00bf";     /* &iquest; */
      ibmoem[169] = "\u00ac";     /* &not; */
      ibmoem[170] = "\u00ac";     /* &not; */
      ibmoem[171] = "\u00bd";     /* &frac12; */
      ibmoem[172] = "\u00bc";     /* &frac14; */
      ibmoem[173] = "\u00a1";     /* &iexcl; */
      ibmoem[174] = "\u00ab";     /* &laquo; */
      ibmoem[175] = "\u00bb";     /* &raquo; */
      ibmoem[224] = "a";
      ibmoem[225] = "\u00df";     /* &szlig; */
      ibmoem[230] = "\u00b5";     /* &micro; */
      ibmoem[231] = "y";
      ibmoem[232] = "\u00d8";     /* &Oslash; */
      ibmoem[233] = "-O-";
      ibmoem[234] = "O";
      ibmoem[235] = "s";
      ibmoem[236] = "oo";
      ibmoem[237] = "\u00f8";     /* &oslash; */
      ibmoem[238] = "(C-)";
      ibmoem[239] = "n";
      ibmoem[240] = "=";
      ibmoem[241] = "\u00b1";     /* &plusmn; */
      ibmoem[242] = ">";
      ibmoem[243] = "<";
      ibmoem[244] = "|";
      ibmoem[245] = "|";
      ibmoem[246] = "\u00f7";     /* &divide; */
      ibmoem[247] = "=~=>";
      ibmoem[248] = "\u00b0";     /* &deg; */
      ibmoem[249] = "\u00b7";     /* &middot; */
      ibmoem[250] = "\u00b7";     /* &middot; */
      ibmoem[251] = "V";
      ibmoem[252] = "n>";
      ibmoem[253] = "2;";
      ibmoem[254] = "*";
      ibmoem[255] = " ";
   } // end static init


} // end class PlainReservedChars


