package common.io;

import java.io.*;

public class TagFilterReader extends FilterReader {

	public TagFilterReader(Reader in) {
		super(in);
	}

	public int read() throws IOException {
		boolean open = false;
		boolean openspec = false;

		int i;
		while ((i = in.read()) != -1) {
			if (open) {
				if (i == '>')
					open = false;
			} else {
				if (i == '<')
					open = true;
				else {
					if (openspec) {
						if (i == ';')
							openspec = false;
					} else {
						if (i == '&')
							openspec = true;
						else
							return(i);
					}
				}
			}
		}
		return(-1);
	}

	public int read(char cbuf[]) throws IOException {
		return(read(cbuf, 0, cbuf.length));
	}

	public int read(char cbuf[], int off, int len) throws IOException {
		int r;

		r = read();
		if (r == -1)
			return(-1);

		for (int i = 0; i < len; i++) {
			cbuf[i + off] = (char) r;
			r = read();
			if (r == -1)
				return(i + 1);
		}
		return(len);
	}

	public boolean markSupported() {
		return(false);
	}
}