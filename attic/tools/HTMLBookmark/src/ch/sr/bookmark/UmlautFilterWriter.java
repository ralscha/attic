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
			case '�': out.write("&ouml;"); break;
			case '�': out.write("&auml;"); break;
			case '�': out.write("&uuml;"); break;
			case '�': out.write("&Auml;"); break;
			case '�': out.write("&Ouml;"); break;
			case '�': out.write("&Uuml;"); break;
			case '�': out.write("&eacute;"); break;
			case '�': out.write("&agrave;"); break;
			case '�': out.write("&egrave;"); break;
			case '�': out.write("&reg;"); break;
			case '�': out.write("&aelig;"); break;
			//case 161: out.write("iexcl;"); break;
			//usw..
			default : out.write(c);
		}
	}
	
	public void write(String str, int off, int len) throws IOException  {
		write(str.toCharArray(), off, len);
	}
		
}