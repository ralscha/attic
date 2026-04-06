package ch.rasc.pubsub.tail;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class PathEvent {
	private final Path eventTarget;

	private final WatchEvent.Kind<?> type;

	PathEvent(Path eventTarget, WatchEvent.Kind<?> type) {
		this.eventTarget = eventTarget;
		this.type = type;
	}

	public Path getEventTarget() {
		return this.eventTarget;
	}

	public WatchEvent.Kind<?> getType() {
		return this.type;
	}
}