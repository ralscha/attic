package ch.rasc.pubsub.tail;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

@Component
public class WatchListener {

	@Subscribe
	@AllowConcurrentEvents
	public void handleWatchEvent(PathEvents pathEvents) {
		System.out.println("--------------------------------------------------");
		System.out.println(new Date() + ": " + pathEvents.getWatchedDirectory());
		for (PathEvent event : pathEvents.getEvents()) {
			System.out.print("  ");
			System.out.print(event.getEventTarget());
			System.out.print("  ");
			System.out.println(event.getType());
		}
	}
}
