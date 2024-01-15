
public class HTMLReservedChars extends Translator {
  /**
    * Converts text to HTML by quoting dangerous characters
    * e.g. " ==> &quot;  < ==> &lt;
    * @param raw raw text to be cooked.  Must not be null.
    * @param table translate table of array of 256 strings to translate chars to.
    */
  public String process (String raw) {
    return process(raw, '?', false);
  } // end process

  // translation table for char to HTML string form.
  { 
  
    latin1 = new String [256];
    // initialize the latin1 array
    for (int i = 0; i < 32; i++) {
      latin1[i] = "?";
    }

    for (int i = 32; i < 127; i++) {
      latin1[i] = String.valueOf((char) i).intern();
    }

    for (int i = 127; i < 256; i++) {
      latin1[i] = "?";
    }

    // override with chars that have special HTML representations
    latin1[9] = "\t";
    latin1[10] = "\n";
    latin1[13] = ""; // discard \r
    latin1[26] = "";
    latin1[38] = "&amp;";
    latin1[34] = "&quot;";
    latin1[60] = "&lt;";
    latin1[62] = "&gt;";
    latin1[160] = "&nbsp;";
    latin1[161] = "&iexcl;";
    latin1[162] = "&cent;";
    latin1[163] = "&pound;";
    latin1[164] = "&curren;";
    latin1[165] = "&yen;";
    latin1[166] = "&brvbar;";
    latin1[167] = "&sect;";
    latin1[168] = "&uml;";
    latin1[169] = "&copy;";
    latin1[170] = "&ordf;";
    latin1[171] = "&laquo;";
    latin1[172] = "&not;";
    latin1[173] = "&shy;";
    latin1[174] = "&reg;";
    latin1[175] = "&macr;";
    latin1[176] = "&deg;";
    latin1[177] = "&plusmn;";
    latin1[178] = "&sup2;";
    latin1[179] = "&sup3;";
    latin1[180] = "&acute;";
    latin1[181] = "&micro;";
    latin1[182] = "&para;";
    latin1[183] = "&middot;";
    latin1[184] = "&cedil;";
    latin1[185] = "&sup1;";
    latin1[186] = "&ordm;";
    latin1[187] = "&raquo;";
    latin1[188] = "&frac14;";
    latin1[189] = "&frac12;";
    latin1[190] = "&frac34;";
    latin1[191] = "&iquest;";
    latin1[192] = "&Agrave;";
    latin1[193] = "&Aacute;";
    latin1[194] = "&Acirc;";
    latin1[195] = "&Atilde;";
    latin1[196] = "&Auml;";
    latin1[197] = "&Aring;";
    latin1[198] = "&AElig;";
    latin1[199] = "&Ccedil;";
    latin1[200] = "&Egrave;";
    latin1[201] = "&Eacute;";
    latin1[202] = "&Ecirc;";
    latin1[203] = "&Euml;";
    latin1[204] = "&Igrave;";
    latin1[205] = "&Iacute;";
    latin1[206] = "&Icirc;";
    latin1[207] = "&Iuml;";
    latin1[208] = "&ETH;";
    latin1[209] = "&Ntilde;";
    latin1[210] = "&Ograve;";
    latin1[211] = "&Oacute;";
    latin1[212] = "&Ocirc;";
    latin1[213] = "&Otilde;";
    latin1[214] = "&Ouml;";
    latin1[215] = "&times;";
    latin1[216] = "&Oslash;";
    latin1[217] = "&Ugrave;";
    latin1[218] = "&Uacute;";
    latin1[219] = "&Ucirc;";
    latin1[220] = "&Uuml;";
    latin1[221] = "&Yacute;";
    latin1[222] = "&THORN;";
    latin1[223] = "&szlig;";
    latin1[224] = "&agrave;";
    latin1[225] = "&aacute;";
    latin1[226] = "&acirc;";
    latin1[227] = "&atilde;";
    latin1[228] = "&auml;";
    latin1[229] = "&aring;";
    latin1[230] = "&aelig;";
    latin1[231] = "&ccedil;";
    latin1[232] = "&egrave;";
    latin1[233] = "&eacute;";
    latin1[234] = "&ecirc;";
    latin1[235] = "&euml;";
    latin1[236] = "&igrave;";
    latin1[237] = "&iacute;";
    latin1[238] = "&icirc;";
    latin1[239] = "&iuml;";
    latin1[240] = "&eth;";
    latin1[241] = "&ntilde;";
    latin1[242] = "&ograve;";
    latin1[243] = "&oacute;";
    latin1[244] = "&ocirc;";
    latin1[245] = "&otilde;";
    latin1[246] = "&ouml;";
    latin1[247] = "&divide;";
    latin1[248] = "&oslash;";
    latin1[249] = "&ugrave;";
    latin1[250] = "&uacute;";
    latin1[251] = "&ucirc;";
    latin1[252] = "&uuml;";
    latin1[253] = "&yacute;";
    latin1[254] = "&thorn;";
    latin1[255] = "&yuml;";
  }
  {
    // translation table for char to HTML string form.
    win = new String [256];


    // initialize the win array
  for (int i = 0; i < 32; i++) {
      win[i] = "?";
    }

  for (int i = 32; i < 127; i++) {
      win[i] = String.valueOf((char) i).intern();
    }

  for (int i = 127; i < 256; i++) {
      win[i] = "?";
    }

    // override with chars that have special HTML representations
    win[9] = "\t";
    win[10] = "\n";
    win[13] = ""; // discard \r
    win[26] = "";
    win[38] = "&amp;";
    win[34] = "&quot;";
    win[60] = "&lt;";
    win[62] = "&gt;";
    win[130] = ",";
    win[131] = "<i>f</i>";
    win[132] = ",,";
    win[133] = "...";
    win[134] = "<strike>|</strike>";
    win[135] = "=|=";
    win[136] = "^";
    win[137] = "%<small>o</small>";
    win[138] = "S";
    win[139] = "&lt;";
    win[140] = "OE";
    win[145] = "`";
    win[146] = "'";
    win[147] = "&quot;";
    win[148] = "&quot;";
    win[149] = "&middot";
    win[150] = "-";
    win[151] = "-";
    win[152] = "~";
    win[153] = "&reg;";
    win[154] = "s";
    win[155] = "&gt;";
    win[156] = "oe";
    win[159] = "Y";
    win[160] = "&nbsp;";
    win[161] = "&iexcl;";
    win[162] = "&cent;";
    win[163] = "&pound;";
    win[164] = "&curren;";
    win[165] = "&yen;";
    win[166] = "&brvbar;";
    win[167] = "&sect;";
    win[168] = "&uml;";
    win[169] = "&copy;";
    win[170] = "&ordf;";
    win[171] = "&laquo;";
    win[172] = "&not;";
    win[173] = "&shy;";
    win[174] = "&reg;";
    win[175] = "&macr;";
    win[176] = "&deg;";
    win[177] = "&plusmn;";
    win[178] = "&sup2;";
    win[179] = "&sup3;";
    win[180] = "&acute;";
    win[181] = "&micro;";
    win[182] = "&para;";
    win[183] = "&middot;";
    win[184] = "&cedil;";
    win[185] = "&sup1;";
    win[186] = "&ordm;";
    win[187] = "&raquo;";
    win[188] = "&frac14;";
    win[189] = "&frac12;";
    win[190] = "&frac34;";
    win[191] = "&iquest;";
    win[192] = "&Agrave;";
    win[193] = "&Aacute;";
    win[194] = "&Acirc;";
    win[195] = "&Atilde;";
    win[196] = "&Auml;";
    win[197] = "&Aring;";
    win[198] = "&AElig;";
    win[199] = "&Ccedil;";
    win[200] = "&Egrave;";
    win[201] = "&Eacute;";
    win[202] = "&Ecirc;";
    win[203] = "&Euml;";
    win[204] = "&Igrave;";
    win[205] = "&Iacute;";
    win[206] = "&Icirc;";
    win[207] = "&Iuml;";
    win[208] = "&ETH;";
    win[209] = "&Ntilde;";
    win[210] = "&Ograve;";
    win[211] = "&Oacute;";
    win[212] = "&Ocirc;";
    win[213] = "&Otilde;";
    win[214] = "&Ouml;";
    win[215] = "&times;";
    win[216] = "&Oslash;";
    win[217] = "&Ugrave;";
    win[218] = "&Uacute;";
    win[219] = "&Ucirc;";
    win[220] = "&Uuml;";
    win[221] = "&Yacute;";
    win[222] = "&THORN;";
    win[223] = "&szlig;";
    win[224] = "&agrave;";
    win[225] = "&aacute;";
    win[226] = "&acirc;";
    win[227] = "&atilde;";
    win[228] = "&auml;";
    win[229] = "&aring;";
    win[230] = "&aelig;";
    win[231] = "&ccedil;";
    win[232] = "&egrave;";
    win[233] = "&eacute;";
    win[234] = "&ecirc;";
    win[235] = "&euml;";
    win[236] = "&igrave;";
    win[237] = "&iacute;";
    win[238] = "&icirc;";
    win[239] = "&iuml;";
    win[240] = "&eth;";
    win[241] = "&ntilde;";
    win[242] = "&ograve;";
    win[243] = "&oacute;";
    win[244] = "&ocirc;";
    win[245] = "&otilde;";
    win[246] = "&ouml;";
    win[247] = "&divide;";
    win[248] = "&oslash;";
    win[249] = "&ugrave;";
    win[250] = "&uacute;";
    win[251] = "&ucirc;";
    win[252] = "&uuml;";
    win[253] = "&yacute;";
    win[254] = "&thorn;";
    win[255] = "&yuml;";

  }

  {
    // translation table for char to HTML string form.
    ibmoem = new String [256];

    // initialize the ibmoem array

    // unprintable ascii
    for (int i = 0; i < 32; i++) {
      ibmoem[i] = "?";
    }

    // printable ascii
    for (int i = 32; i < 127; i++) {
      ibmoem[i] = String.valueOf((char) i).intern();
    }

    // high ascii
    for (int i = 127; i < 256; i++) {
      ibmoem[i] = "?";
    }

    // line drawing set
    for (int i = 176; i < 224; i++) {
      ibmoem[i] = "*";
    }


    // override with chars that have special HTML representations
    ibmoem[9] = "\t";
    ibmoem[10] = "\n";
    ibmoem[13] = ""; // discard \r
    ibmoem[14] = "&para;";
    ibmoem[15] = "&sect;";
    ibmoem[26] = "";
    ibmoem[34] = "&quot;";
    ibmoem[38] = "&amp;";
    ibmoem[60] = "&lt;";
    ibmoem[62] = "&gt;";
    ibmoem[128] = "&Ccedil;";
    ibmoem[129] = "&uuml;";
    ibmoem[130] = "&eacute;";
    ibmoem[131] = "&acirc;";
    ibmoem[132] = "&auml;";
    ibmoem[133] = "&agrave;";
    ibmoem[134] = "&aring;";
    ibmoem[135] = "&ccedil;";
    ibmoem[136] = "&ecirc;";
    ibmoem[137] = "&euml;";
    ibmoem[138] = "&egrave;";
    ibmoem[139] = "&iuml;";
    ibmoem[140] = "&icirc;";
    ibmoem[141] = "&igrave;";
    ibmoem[142] = "&Auml;";
    ibmoem[143] = "&Aring;";
    ibmoem[144] = "&Eacute;";
    ibmoem[145] = "&aelig;";
    ibmoem[146] = "&AElig;";
    ibmoem[147] = "ocirc;";
    ibmoem[148] = "ouml;";
    ibmoem[149] = "ograve;";
    ibmoem[150] = "ucirc;";
    ibmoem[151] = "ugrave;";
    ibmoem[152] = "yuml;";
    ibmoem[153] = "Ouml;";
    ibmoem[154] = "Uuml;";
    ibmoem[155] = "&cent;";
    ibmoem[156] = "&pound;";
    ibmoem[157] = "&yen;";
    ibmoem[158] = "Pt";
    ibmoem[159] = "<i>f</i>";
    ibmoem[160] = "&aacute;";
    ibmoem[161] = "&iacute;";
    ibmoem[162] = "&oacute;";
    ibmoem[163] = "&uacute;";
    ibmoem[164] = "&ntilde;";
    ibmoem[165] = "&Ntilde;";
    ibmoem[166] = "&ordf;";
    ibmoem[167] = "&ordm;";
    ibmoem[168] = "&iquest;";
    ibmoem[169] = "&not;";
    ibmoem[170] = "&not;";
    ibmoem[171] = "&frac12;";
    ibmoem[172] = "&frac14;";
    ibmoem[173] = "&iexcl;";
    ibmoem[174] = "&laquo;";
    ibmoem[175] = "&raquo;";
    ibmoem[224] = "<i>a</i>";
    ibmoem[225] = "&szlig;";
    ibmoem[230] = "&micro;";
    ibmoem[231] = "<i>y</i>";
    ibmoem[232] = "&Oslash;";
    ibmoem[233] = "<strike>O</strike>";
    ibmoem[234] = "<u>O</u>";
    ibmoem[235] = "<i>s</i>";
    ibmoem[236] = "oo";
    ibmoem[237] = "&oslash;";
    ibmoem[238] = "<strike>C</strike>";
    ibmoem[239] = "<big>n</big>";
    ibmoem[240] = "<u>=</u>";
    ibmoem[241] = "&plusmn;";
    ibmoem[242] = "<u>&gt;</u>";
    ibmoem[243] = "<u>&lt;</u>";
    ibmoem[244] = "<big>|</big>";
    ibmoem[245] = "<big>|</big>";
    ibmoem[246] = "&divide;";
    ibmoem[247] = "<i>=~=</i>";
    ibmoem[248] = "&deg;";
    ibmoem[249] = "&middot;";
    ibmoem[250] = "&middot;";
    ibmoem[251] = "<big>V</big>";
    ibmoem[252] = "<sup>n</sup>";
    ibmoem[253] = "&sup2;";
    ibmoem[254] = "*";
    ibmoem[255] = " ";
  } // end init


} // end class HTMLReservedChars


