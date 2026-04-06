package ch.ess.propertiestool;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class LineReader {
	public LineReader(InputStream inStream) {
		this.inStream = inStream;
		this.inByteBuf = new byte[8192];
	}

	public LineReader(Reader reader) {
		this.reader = reader;
		this.inCharBuf = new char[8192];
	}

	byte[] inByteBuf;

	char[] inCharBuf;

	char[] lineBuf = new char[1024];

	int inLimit = 0;

	int inOff = 0;

	InputStream inStream;

	Reader reader;

	int readLine() throws IOException {
		int len = 0;
		char c = 0;

		boolean skipWhiteSpace = true;
		boolean isCommentLine = false;
		boolean isNewLine = true;
		boolean appendedLineBegin = false;
		boolean precedingBackslash = false;
		boolean skipLF = false;

		while (true) {
			if (this.inOff >= this.inLimit) {
				this.inLimit = this.inStream == null ? this.reader.read(this.inCharBuf)
						: this.inStream.read(this.inByteBuf);
				this.inOff = 0;
				if (this.inLimit <= 0) {
					if (len == 0 || isCommentLine) {
						return -1;
					}
					return len;
				}
			}
			if (this.inStream != null) {
				// The line below is equivalent to calling a
				// ISO8859-1 decoder.
				c = (char) (0xff & this.inByteBuf[this.inOff++]);
			}
			else {
				c = this.inCharBuf[this.inOff++];
			}
			if (skipLF) {
				skipLF = false;
				if (c == '\n') {
					continue;
				}
			}
			if (skipWhiteSpace) {
				if (c == ' ' || c == '\t' || c == '\f') {
					continue;
				}
				if (!appendedLineBegin && (c == '\r' || c == '\n')) {
					continue;
				}
				skipWhiteSpace = false;
				appendedLineBegin = false;
			}
			if (isNewLine) {
				isNewLine = false;
				if (c == '#' || c == '!') {
					isCommentLine = true;
					continue;
				}
			}

			if (c != '\n' && c != '\r') {
				this.lineBuf[len++] = c;
				if (len == this.lineBuf.length) {
					int newLength = this.lineBuf.length * 2;
					if (newLength < 0) {
						newLength = Integer.MAX_VALUE;
					}
					char[] buf = new char[newLength];
					System.arraycopy(this.lineBuf, 0, buf, 0, this.lineBuf.length);
					this.lineBuf = buf;
				}
				// flip the preceding backslash flag
				if (c == '\\') {
					precedingBackslash = !precedingBackslash;
				}
				else {
					precedingBackslash = false;
				}
			}
			else {
				// reached EOL
				if (isCommentLine || len == 0) {
					isCommentLine = false;
					isNewLine = true;
					skipWhiteSpace = true;
					len = 0;
					continue;
				}
				if (this.inOff >= this.inLimit) {
					this.inLimit = this.inStream == null
							? this.reader.read(this.inCharBuf)
							: this.inStream.read(this.inByteBuf);
					this.inOff = 0;
					if (this.inLimit <= 0) {
						return len;
					}
				}
				if (precedingBackslash) {
					len -= 1;
					// skip the leading whitespace characters in following line
					skipWhiteSpace = true;
					appendedLineBegin = true;
					precedingBackslash = false;
					if (c == '\r') {
						skipLF = true;
					}
				}
				else {
					return len;
				}
			}
		}
	}
}