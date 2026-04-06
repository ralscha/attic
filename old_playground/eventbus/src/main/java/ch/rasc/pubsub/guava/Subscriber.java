package ch.rasc.pubsub.guava;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

@Service
public class Subscriber {

	@Subscribe
	public void handleMsgEvent(MsgEvent event) {
		System.out.println("MsgEvent: " + event.getMessage());
	}

	@Subscribe
	public void handleSpecialMsgEvent(SpecialMsgEvent event) {
		System.out.println(
				"SpecialMsgEvent: " + event.getMessage() + " User: " + event.getUser());
		try {
			TimeUnit.SECONDS.sleep(5);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Subscribe
	public void handleDeadEvents(DeadEvent deadEvent) {
		System.out.print("Event without a subscriber: ");
		System.out.println(deadEvent.getEvent());
	}

}
