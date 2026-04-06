package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.darylteo.nio.DirectoryChangedSubscriber;
import com.darylteo.nio.DirectoryWatcher;
import com.darylteo.nio.ThreadPoolDirectoryWatchService;

public class DirectoryWatchService {
	private final ThreadPoolDirectoryWatchService factory;
	private DirectoryWatcher watcher;
	private DirectoryChangedSubscriber subscriber;
	private final WorkQueue queue;

	public DirectoryWatchService(WorkQueue queue) throws IOException {
		this.factory = new ThreadPoolDirectoryWatchService();
		this.queue = queue;
	}

	public void watch(Path basePath) throws IOException {
		cleanupWatcher();

		this.watcher = this.factory.newWatcher(basePath);
		this.watcher.include("**");

		this.subscriber = new DirectoryChangedSubscriber() {
			@Override
			public void directoryChanged(DirectoryWatcher dw, Path p) {
				if (!Files.isDirectory(dw.getPath().resolve(p))) {
					DirectoryWatchService.this.queue.add(dw.getPath(), p);
				}
			}
		};
		this.watcher.subscribe(this.subscriber);
	}

	private void cleanupWatcher() {
		if (this.watcher != null) {
			this.watcher.unsubscribe(this.subscriber);
		}
	}

	public void shutdown() {
		cleanupWatcher();
		if (this.factory != null) {
			try {
				this.factory.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
