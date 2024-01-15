package ch.sr.bookmark;

import java.io.*;

public class UmlautFilterWriter extends FilterWriter {


	public UmlautFilterWriter(Writer out) throws IOException {
   		super(out);
	}

	public void write(char[] cbuf, int off, int len) throws IOException  {
		for (int i = 0; i < len; i++) 
			write(cbuf[i+off]);
	}
	
	public void write(int c) throws IOException {
		switch(c) {
			case 'ö': out.write("&ouml;"); break;
			case 'ä': out.write("&auml;"); break;
			case 'ü': out.write("&uuml;"); break;
			case 'Ä': out.write("&Auml;"); break;
			case 'Ö': out.write("&Ouml;"); break;
			case 'Ü': out.write("&Uuml;"); break;
			case 'é': out.write("&eacute;"); break;
			case 'à': out.write("&agrave;"); break;
			case 'è': out.write("&egrave;"); break;
			case '®': out.write("&reg;"); break;
			case 'æ': out.write("&aelig;"); break;
			//case 161: out.write("iexcl;"); break;
			//usw..
			default : out.write(c);
		}
	}
	
	public void write(String str, int off, int len) throws IOException  {
		write(str.toCharArray(), off, len);
	}
		
}