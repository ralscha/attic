package ch.rasc.nosql.rethink;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public final class SevenZFileInputStream extends InputStream {

	private final SevenZFile sevenZFile;

	public SevenZFileInputStream(SevenZFile sevenZFile) {
		this.sevenZFile = sevenZFile;
	}

	@Override
	public int read() throws IOException {
		return this.sevenZFile.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return this.sevenZFile.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return this.sevenZFile.read(b, off, len);
	}
}