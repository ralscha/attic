package ch.rasc.pubsub.tail;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

@Component
public class Tailer {

	private final Path path;

	private long position;

	private final ByteBuffer buffer = ByteBuffer.allocate(128);

	private final String encoding = System.getProperty("file.encoding");

	public Tailer() {
		this.path = Paths.get("c:/temp/t.txt");
	}

	@Subscribe
	public void handleWatchEvent(PathEvents pathEvents) {
		for (PathEvent event : pathEvents.getEvents()) {
			if (this.path.endsWith(event.getEventTarget())) {
				if (event.getType() == StandardWatchEventKinds.ENTRY_DELETE) {
					System.out.println("TAIL: entry deleted");
				}
				else if (event.getType() == StandardWatchEventKinds.ENTRY_MODIFY) {
					System.out.println("TAIL: modified");
					try {
						printTail();
					}
					catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	private void printTail() throws IOException {

		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(this.path,
				StandardOpenOption.READ)) {
			if (seekableByteChannel.size() < this.position) {
				this.position = 0;
			}

			seekableByteChannel.position(this.position);
			this.buffer.clear();

			while (seekableByteChannel.read(this.buffer) > 0) {
				this.buffer.flip();
				System.out.print(Charset.forName(this.encoding).decode(this.buffer));
				this.buffer.clear();
			}

			this.position = seekableByteChannel.position();
		}

	}

}
