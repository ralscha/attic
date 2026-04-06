package ch.rasc.pubsub.tail;

import java.nio.file.Path;

import com.google.common.collect.ImmutableList;

public class PathEvents {

	private final ImmutableList<PathEvent> pathEvents;

	private final Path watchedDirectory;

	public PathEvents(Path watchedDirectory, ImmutableList<PathEvent> events) {
		this.watchedDirectory = watchedDirectory;
		this.pathEvents = events;
	}

	public Path getWatchedDirectory() {
		return this.watchedDirectory;
	}

	public ImmutableList<PathEvent> getEvents() {
		return this.pathEvents;
	}

}