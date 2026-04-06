package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WorkQueue {

	private final int processingDelayInSeconds;

	private final Set<WorkJob> inbox = new HashSet<>();

	public WorkQueue() {
		this(10);
	}

	public WorkQueue(int processingDelayInSeconds) {
		this.processingDelayInSeconds = processingDelayInSeconds;
	}

	public synchronized void add(Path baseDir, Path file) {
		this.inbox.add(new WorkJob(baseDir, file));
	}

	public void add(Set<WorkJob> paths) {
		this.inbox.addAll(paths);
	}

	public void add(WorkJob job) {
		this.inbox.add(job);
	}

	// return all paths that have a modified date older than 10 seconds
	public synchronized Set<WorkJob> getPaths() throws IOException {
		Set<WorkJob> result = new HashSet<>();
		Set<WorkJob> nonExistingPaths = new HashSet<>();
		long now = System.currentTimeMillis() / 1000;

		for (WorkJob job : this.inbox) {
			Path path = job.getFile();
			if (Files.exists(path)) {
				FileTime time = Files.getLastModifiedTime(path);
				if (time.to(TimeUnit.SECONDS) + this.processingDelayInSeconds <= now) {
					result.add(job);
				}
			}
			else {
				nonExistingPaths.add(job);
			}
		}

		this.inbox.removeAll(result);
		this.inbox.removeAll(nonExistingPaths);

		return result;
	}

}
