package ch.rasc.pubsub.tail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;

@Service
public class DirectoryWatcher {

	EventBus eventBus;

	WatchService watchService;

	volatile boolean watching;

	@Autowired
	public DirectoryWatcher(EventBus eventBus) {
		this.watching = false;
		this.eventBus = eventBus;
		try {
			this.watchService = FileSystems.getDefault().newWatchService();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void start() {
		new Thread(() -> {

			while (this.watching) {
				try {
					final WatchKey watchKey = this.watchService.poll(10,
							TimeUnit.SECONDS);
					if (watchKey != null) {
						final List<WatchEvent<?>> events = watchKey.pollEvents();

						ImmutableList.Builder<PathEvent> pathEventsBuilder = ImmutableList
								.builder();
						for (WatchEvent<?> event : events) {
							pathEventsBuilder.add(
									new PathEvent((Path) event.context(), event.kind()));
						}

						watchKey.reset();
						this.eventBus.post(new PathEvents((Path) watchKey.watchable(),
								pathEventsBuilder.build()));
					}
				}
				catch (InterruptedException e) {
					this.watching = false;
				}
			}

		}).start();
	}

	@PreDestroy
	public void stop() {
		System.out.println("Stopping...");
		this.watching = false;
	}

	public void startWatch(Path path) {
		try {
			path.register(this.watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			if (!this.watching) {
				this.watching = true;
				start();
			}

		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
