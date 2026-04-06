package ch.rasc.pubsub;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReadFile {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:/temp", "t.txt");
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path,
				StandardOpenOption.READ)) {
			ByteBuffer buffer = ByteBuffer.allocate(128);
			String encoding = System.getProperty("file.encoding");
			buffer.clear();
			while (seekableByteChannel.read(buffer) > 0) {
				buffer.flip();
				System.out.print(Charset.forName(encoding).decode(buffer));
				buffer.clear();
			}
		}

	}

}
