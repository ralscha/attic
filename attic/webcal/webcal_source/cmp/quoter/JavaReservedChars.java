package cmp.quoter;

public class JavaReservedChars
{
    /**
     * Converts text to Java String literals by quoting dangerous characters
     * e.g. " ==> \"
     * @param raw raw text to be cooked.  Must not be null.
     * @param table translate table of array of 256 strings to translate chars to.
     */
    public static String toQuoted (String raw, String[] table)
    {
        if ( raw == null || raw.length() == 0 )
        {
            return null;
        }
        if ( table == null )
        {
            table = latin1;
        }
        StringBuffer cooked = new StringBuffer(raw.length() * 120 / 100 );
        //
        cooked.append("  \"");
        for ( int i=0; i< raw.length(); i++ )
        {
            char c = raw.charAt(i);
            if ( c < 256 )
            {
                cooked.append(table[c]);
            }
            else
            {
                cooked.append("\\u"
                              + Integer.toHexString(c + 0x10000).substring(0,4));
            }
        }
        // get rid of trailing junk, we may have appended too much.
        cooked.append("\"");
        String cookedString = cooked.toString();
        if ( cookedString.endsWith("+ \"\"") )
        {
            cookedString = cookedString.substring(0,cookedString.length()-4);
        }
        return cookedString;
    } // end toQuoted

    // translation table for char to Java literal string form.
    public static final String[] latin1 = new String [256];
    static {

        // initialize the latin1 array
        for ( int i=0; i<32; i++ )
        {
            latin1[i] = "?";
        }

        for ( int i=32; i<127; i++ )
        {
            latin1[i] = String.valueOf((char)i).intern();
        }

        for ( int i=127; i<256; i++ )
        {
            latin1[i] = "?";
        }

        // override with chars that have special HTML representations
        latin1[9] = "\t";
        latin1[10] = "\\n\"\n + \"";     /* newline -> \n" newline + "  */
        latin1[13] = "";                 /* discard \r */
        latin1[26] = "";
        latin1[34] = "\\\"";            /* " -> \" */
        latin1[39] = "\\'";             /* ' -> \' */
        latin1[92] = "\\\\";            /* \ -> \\ */
        latin1[160] = " ";
        latin1[161] = "\\u00a1";     /* &iexcl; */
        latin1[162] = "\\u00a2";     /* &cent; */
        latin1[163] = "\\u00a3";     /* &pound; */
        latin1[164] = "\\u00a4";     /* &curren; */
        latin1[165] = "\\u00a5";     /* &yen; */
        latin1[166] = "\\u00a6";     /* &brvbar; */
        latin1[167] = "\\u00a7";     /* &sect; */
        latin1[168] = "\\u00a8";     /* &uml; */
        latin1[169] = "\\u00a9";     /* &copy; */
        latin1[170] = "\\u00aa";     /* &ordf; */
        latin1[171] = "\\u00ab";     /* &laquo; */
        latin1[172] = "\\u00ac";     /* &not; */
        latin1[173] = "\\u00ad";     /* &shy; */
        latin1[174] = "\\u00ae";     /* &reg; */
        latin1[175] = "\\u00af";     /* &macr; */
        latin1[176] = "\\u00b0";     /* &deg; */
        latin1[177] = "\\u00b1";     /* &plusmn; */
        latin1[178] = "\\u00b2";     /* &sup2; */
        latin1[179] = "\\u00b3";     /* &sup3; */
        latin1[180] = "\\u00b4";     /* &acute; */
        latin1[181] = "\\u00b5";     /* &micro; */
        latin1[182] = "\\u00b6";     /* &para; */
        latin1[183] = "\\u00b7";     /* &middot; */
        latin1[184] = "\\u00b8";     /* &cedil; */
        latin1[185] = "\\u00b9";     /* &sup1; */
        latin1[186] = "\\u00ba";     /* &ordm; */
        latin1[187] = "\\u00bb";     /* &raquo; */
        latin1[188] = "\\u00bc";     /* &frac14; */
        latin1[189] = "\\u00bd";     /* &frac12; */
        latin1[190] = "\\u00be";     /* &frac34; */
        latin1[191] = "\\u00bf";     /* &iquest; */
        latin1[192] = "\\u00c0";     /* &Agrave; */
        latin1[193] = "\\u00c1";     /* &Aacute; */
        latin1[194] = "\\u00c2";     /* &Acirc; */
        latin1[195] = "\\u00c3";     /* &Atilde; */
        latin1[196] = "\\u00c4";     /* &Auml; */
        latin1[197] = "\\u00c5";     /* &Aring; */
        latin1[198] = "\\u00c6";     /* &AElig; */
        latin1[199] = "\\u00c7";     /* &Ccedil; */
        latin1[200] = "\\u00c8";     /* &Egrave; */
        latin1[201] = "\\u00c9";     /* &Eacute; */
        latin1[202] = "\\u00ca";     /* &Ecirc; */
        latin1[203] = "\\u00cb";     /* &Euml; */
        latin1[204] = "\\u00cc";     /* &Igrave; */
        latin1[205] = "\\u00cd";     /* &Iacute; */
        latin1[206] = "\\u00ce";     /* &Icirc; */
        latin1[207] = "\\u00cf";     /* &Iuml; */
        latin1[208] = "\\u00d0";     /* &ETH; */
        latin1[209] = "\\u00d1";     /* &Ntilde; */
        latin1[210] = "\\u00d2";     /* &Ograve; */
        latin1[211] = "\\u00d3";     /* &Oacute; */
        latin1[212] = "\\u00d4";     /* &Ocirc; */
        latin1[213] = "\\u00d5";     /* &Otilde; */
        latin1[214] = "\\u00d6";     /* &Ouml; */
        latin1[215] = "\\u00d7";     /* &times; */
        latin1[216] = "\\u00d8";     /* &Oslash; */
        latin1[217] = "\\u00d9";     /* &Ugrave; */
        latin1[218] = "\\u00da";     /* &Uacute; */
        latin1[219] = "\\u00db";     /* &Ucirc; */
        latin1[220] = "\\u00dc";     /* &Uuml; */
        latin1[221] = "\\u00dd";     /* &Yacute; */
        latin1[222] = "\\u00de";     /* &THORN; */
        latin1[223] = "\\u00df";     /* &szlig; */
        latin1[224] = "\\u00e0";     /* &agrave; */
        latin1[225] = "\\u00e1";     /* &aacute; */
        latin1[226] = "\\u00e2";     /* &acirc; */
        latin1[227] = "\\u00e3";     /* &atilde; */
        latin1[228] = "\\u00e4";     /* &auml; */
        latin1[229] = "\\u00e5";     /* &aring; */
        latin1[230] = "\\u00e6";     /* &aelig; */
        latin1[231] = "\\u00e7";     /* &ccedil; */
        latin1[232] = "\\u00e8";     /* &egrave; */
        latin1[233] = "\\u00e9";     /* &eacute; */
        latin1[234] = "\\u00ea";     /* &ecirc; */
        latin1[235] = "\\u00eb";     /* &euml; */
        latin1[236] = "\\u00ec";     /* &igrave; */
        latin1[237] = "\\u00ed";     /* &iacute; */
        latin1[238] = "\\u00ee";     /* &icirc; */
        latin1[239] = "\\u00ef";     /* &iuml; */
        latin1[240] = "\\u00f0";     /* &eth; */
        latin1[241] = "\\u00f1";     /* &ntilde; */
        latin1[242] = "\\u00f2";     /* &ograve; */
        latin1[243] = "\\u00f3";     /* &oacute; */
        latin1[244] = "\\u00f4";     /* &ocirc; */
        latin1[245] = "\\u00f5";     /* &otilde; */
        latin1[246] = "\\u00f6";     /* &ouml; */
        latin1[247] = "\\u00f7";     /* &divide; */
        latin1[248] = "\\u00f8";     /* &oslash; */
        latin1[249] = "\\u00f9";     /* &ugrave; */
        latin1[250] = "\\u00fa";     /* &uacute; */
        latin1[251] = "\\u00fb";     /* &ucirc; */
        latin1[252] = "\\u00fc";     /* &uuml; */
        latin1[253] = "\\u00fd";     /* &yacute; */
        latin1[254] = "\\u00fe";     /* &thorn; */
        latin1[255] = "\\u00ff";     /* &yuml; */

    } // end static init




    // translation table for char to Java string form.
    public static final String[] win = new String [256];
    static {

        // initialize the win array
        for ( int i=0; i<32; i++ )
        {
            win[i] = "?";
        }

        for ( int i=32; i<127; i++ )
        {
            win[i] = String.valueOf((char)i).intern();
        }

        for ( int i=127; i<256; i++ )
        {
            win[i] = "?";
        }

        // override with chars that have special HTML representations
        win[9] = "\t";
        win[10] = "\\n\"\n + \"";     /* newline -> \n" newline + "  */
        win[13] = "";                 /* discard \r */
        win[26] = "";
        win[34] = "\\\"";            /* " -> \" */
        win[39] = "\\'";             /* ' -> \' */
        win[92] = "\\\\";            /* \ -> \\ */


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

        win[161] = "\\u00a1";     /* &iexcl; */
        win[162] = "\\u00a2";     /* &cent; */
        win[163] = "\\u00a3";     /* &pound; */
        win[164] = "\\u00a4";     /* &curren; */
        win[165] = "\\u00a5";     /* &yen; */
        win[166] = "\\u00a6";     /* &brvbar; */
        win[167] = "\\u00a7";     /* &sect; */
        win[168] = "\\u00a8";     /* &uml; */
        win[169] = "\\u00a9";     /* &copy; */
        win[170] = "\\u00aa";     /* &ordf; */
        win[171] = "\\u00ab";     /* &laquo; */
        win[172] = "\\u00ac";     /* &not; */
        win[173] = "\\u00ad";     /* &shy; */
        win[174] = "\\u00ae";     /* &reg; */
        win[175] = "\\u00af";     /* &macr; */
        win[176] = "\\u00b0";     /* &deg; */
        win[177] = "\\u00b1";     /* &plusmn; */
        win[178] = "\\u00b2";     /* &sup2; */
        win[179] = "\\u00b3";     /* &sup3; */
        win[180] = "\\u00b4";     /* &acute; */
        win[181] = "\\u00b5";     /* &micro; */
        win[182] = "\\u00b6";     /* &para; */
        win[183] = "\\u00b7";     /* &middot; */
        win[184] = "\\u00b8";     /* &cedil; */
        win[185] = "\\u00b9";     /* &sup1; */
        win[186] = "\\u00ba";     /* &ordm; */
        win[187] = "\\u00bb";     /* &raquo; */
        win[188] = "\\u00bc";     /* &frac14; */
        win[189] = "\\u00bd";     /* &frac12; */
        win[190] = "\\u00be";     /* &frac34; */
        win[191] = "\\u00bf";     /* &iquest; */
        win[192] = "\\u00c0";     /* &Agrave; */
        win[193] = "\\u00c1";     /* &Aacute; */
        win[194] = "\\u00c2";     /* &Acirc; */
        win[195] = "\\u00c3";     /* &Atilde; */
        win[196] = "\\u00c4";     /* &Auml; */
        win[197] = "\\u00c5";     /* &Aring; */
        win[198] = "\\u00c6";     /* &AElig; */
        win[199] = "\\u00c7";     /* &Ccedil; */
        win[200] = "\\u00c8";     /* &Egrave; */
        win[201] = "\\u00c9";     /* &Eacute; */
        win[202] = "\\u00ca";     /* &Ecirc; */
        win[203] = "\\u00cb";     /* &Euml; */
        win[204] = "\\u00cc";     /* &Igrave; */
        win[205] = "\\u00cd";     /* &Iacute; */
        win[206] = "\\u00ce";     /* &Icirc; */
        win[207] = "\\u00cf";     /* &Iuml; */
        win[208] = "\\u00d0";     /* &ETH; */
        win[209] = "\\u00d1";     /* &Ntilde; */
        win[210] = "\\u00d2";     /* &Ograve; */
        win[211] = "\\u00d3";     /* &Oacute; */
        win[212] = "\\u00d4";     /* &Ocirc; */
        win[213] = "\\u00d5";     /* &Otilde; */
        win[214] = "\\u00d6";     /* &Ouml; */
        win[215] = "\\u00d7";     /* &times; */
        win[216] = "\\u00d8";     /* &Oslash; */
        win[217] = "\\u00d9";     /* &Ugrave; */
        win[218] = "\\u00da";     /* &Uacute; */
        win[219] = "\\u00db";     /* &Ucirc; */
        win[220] = "\\u00dc";     /* &Uuml; */
        win[221] = "\\u00dd";     /* &Yacute; */
        win[222] = "\\u00de";     /* &THORN; */
        win[223] = "\\u00df";     /* &szlig; */
        win[224] = "\\u00e0";     /* &agrave; */
        win[225] = "\\u00e1";     /* &aacute; */
        win[226] = "\\u00e2";     /* &acirc; */
        win[227] = "\\u00e3";     /* &atilde; */
        win[228] = "\\u00e4";     /* &auml; */
        win[229] = "\\u00e5";     /* &aring; */
        win[230] = "\\u00e6";     /* &aelig; */
        win[231] = "\\u00e7";     /* &ccedil; */
        win[232] = "\\u00e8";     /* &egrave; */
        win[233] = "\\u00e9";     /* &eacute; */
        win[234] = "\\u00ea";     /* &ecirc; */
        win[235] = "\\u00eb";     /* &euml; */
        win[236] = "\\u00ec";     /* &igrave; */
        win[237] = "\\u00ed";     /* &iacute; */
        win[238] = "\\u00ee";     /* &icirc; */
        win[239] = "\\u00ef";     /* &iuml; */
        win[240] = "\\u00f0";     /* &eth; */
        win[241] = "\\u00f1";     /* &ntilde; */
        win[242] = "\\u00f2";     /* &ograve; */
        win[243] = "\\u00f3";     /* &oacute; */
        win[244] = "\\u00f4";     /* &ocirc; */
        win[245] = "\\u00f5";     /* &otilde; */
        win[246] = "\\u00f6";     /* &ouml; */
        win[247] = "\\u00f7";     /* &divide; */
        win[248] = "\\u00f8";     /* &oslash; */
        win[249] = "\\u00f9";     /* &ugrave; */
        win[250] = "\\u00fa";     /* &uacute; */
        win[251] = "\\u00fb";     /* &ucirc; */
        win[252] = "\\u00fc";     /* &uuml; */
        win[253] = "\\u00fd";     /* &yacute; */
        win[254] = "\\u00fe";     /* &thorn; */
        win[255] = "\\u00ff";     /* &yuml; */

    } // end static init



    // translation table for char to HTML string form.
    public static final String[] ibmoem = new String [256];
    static {

        // initialize the ibmoem array

        // unprintable ascii
        for ( int i=0; i<32; i++ )
        {
            ibmoem[i] = "?";
        }

        // printable ascii
        for ( int i=32; i<127; i++ )
        {
            ibmoem[i] = String.valueOf((char)i).intern();
        }

        // high ascii
        for ( int i=127; i<256; i++ )
        {
            ibmoem[i] = "?";
        }

        // line drawing set
        for ( int i=176; i<224; i++ )
        {
            ibmoem[i] = "*";
        }


        // override with chars that have special HTML representations

        ibmoem[9] = "\t";
        ibmoem[10] = "\\n\"\n + \""; /* newline -> \n" newline + "  */
        ibmoem[13] = "";             /* discard \r */
        ibmoem[14] = "(para)";
        ibmoem[15] = "(sect)";
        ibmoem[26] = "";
        ibmoem[34] = "\\\"";         /* " -> \" */
        ibmoem[39] = "\\'";          /* ' -> \' */
        ibmoem[92] = "\\\\";         /* \ -> \\ */
        ibmoem[128] = "\\u00c7";     /* &Ccedil; */
        ibmoem[129] = "\\u00fc";     /* &uuml; */
        ibmoem[130] = "\\u00e9";     /* &eacute; */
        ibmoem[131] = "\\u00e2";     /* &acirc; */
        ibmoem[132] = "\\u00e4";     /* &auml; */
        ibmoem[133] = "\\u00e0";     /* &agrave; */
        ibmoem[134] = "\\u00e5";     /* &aring; */
        ibmoem[135] = "\\u00e7";     /* &ccedil; */
        ibmoem[136] = "\\u00ea";     /* &ecirc; */
        ibmoem[137] = "\\u00eb";     /* &euml; */
        ibmoem[138] = "\\u00e8";     /* &egrave; */
        ibmoem[139] = "\\u00ef";     /* &iuml; */
        ibmoem[140] = "\\u00ee";     /* &icirc; */
        ibmoem[141] = "\\u00ec";     /* &igrave; */
        ibmoem[142] = "\\u00c4";     /* &Auml; */
        ibmoem[143] = "\\u00c5";     /* &Aring; */
        ibmoem[144] = "\\u00c9";     /* &Eacute; */
        ibmoem[145] = "\\u00e6";     /* &aelig; */
        ibmoem[146] = "\\u00c6";     /* &AElig; */
        ibmoem[147] = "\\u00f4";     /* &ocirc; */
        ibmoem[148] = "\\u00f6";     /* &ouml; */
        ibmoem[149] = "\\u00f2";     /* &ograve; */
        ibmoem[150] = "\\u00fb";     /* &ucirc; */
        ibmoem[151] = "\\u00f9";     /* &ugrave; */
        ibmoem[152] = "\\u00ff";     /* &yuml; */
        ibmoem[153] = "\\u00d6";     /* &Ouml; */
        ibmoem[154] = "\\u00dc";     /* &Uuml; */
        ibmoem[155] = "\\u00a2";     /* &cent; */
        ibmoem[156] = "\\u00a3";     /* &pound; */
        ibmoem[157] = "\\u00a5";     /* &yen; */
        ibmoem[158] = "Pt";
        ibmoem[159] = "f";
        ibmoem[160] = "\\u00e1";     /* &aacute; */
        ibmoem[161] = "\\u00ed";     /* &iacute; */
        ibmoem[162] = "\\u00f3";     /* &oacute; */
        ibmoem[163] = "\\u00fa";     /* &uacute; */
        ibmoem[164] = "\\u00f1";     /* &ntilde; */
        ibmoem[165] = "\\u00d1";     /* &Ntilde; */
        ibmoem[166] = "\\u00aa";     /* &ordf; */
        ibmoem[167] = "\\u00ba";     /* &ordm; */
        ibmoem[168] = "\\u00bf";     /* &iquest; */
        ibmoem[169] = "\\u00ac";     /* &not; */
        ibmoem[170] = "\\u00ac";     /* &not; */
        ibmoem[171] = "\\u00bd";     /* &frac12; */
        ibmoem[172] = "\\u00bc";     /* &frac14; */
        ibmoem[173] = "\\u00a1";     /* &iexcl; */
        ibmoem[174] = "\\u00ab";     /* &laquo; */
        ibmoem[175] = "\\u00bb";     /* &raquo; */
        ibmoem[224] = "a";
        ibmoem[225] = "\\u00df";     /* &szlig; */
        ibmoem[230] = "\\u00b5";     /* &micro; */
        ibmoem[231] = "y";
        ibmoem[232] = "\\u00d8";     /* &Oslash; */
        ibmoem[233] = "-O-";
        ibmoem[234] = "O";
        ibmoem[235] = "s";
        ibmoem[236] = "oo";
        ibmoem[237] = "\\u00f8";     /* &oslash; */
        ibmoem[238] = "(C-)";
        ibmoem[239] = "n";
        ibmoem[240] = "=";
        ibmoem[241] = "\\u00b1";     /* &plusmn; */
        ibmoem[242] = ">";
        ibmoem[243] = "<";
        ibmoem[244] = "|";
        ibmoem[245] = "|";
        ibmoem[246] = "\\u00f7";     /* &divide; */
        ibmoem[247] = "=~=>";
        ibmoem[248] = "\\u00b0";     /* &deg; */
        ibmoem[249] = "\\u00b7";     /* &middot; */
        ibmoem[250] = "\\u00b7";     /* &middot; */
        ibmoem[251] = "V";
        ibmoem[252] = "n>";
        ibmoem[253] = "2;";
        ibmoem[254] = "*";
        ibmoem[255] = " ";
    } // end static init


} // end class JavaReservedChars


