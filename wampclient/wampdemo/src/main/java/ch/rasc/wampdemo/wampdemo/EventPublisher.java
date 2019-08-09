package ch.rasc.wampdemo.wampdemo;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.rasc.wamp2spring.WampPublisher;
import ch.rasc.wamp2spring.pubsub.SubscriptionRegistry;

@Service
public class EventPublisher {

	private final SubscriptionRegistry subscriptionRegistry;

	private final WampPublisher wampPublisher;

	public EventPublisher(WampPublisher wampPublisher,
			SubscriptionRegistry subscriptionRegistry) {
		this.wampPublisher = wampPublisher;
		this.subscriptionRegistry = subscriptionRegistry;
	}

	@Scheduled(fixedDelay = 5_000)
	public void sendData() {
		if (Math.random() < 0.5) {
			if (this.subscriptionRegistry.hasSubscribers("event1")) {
				this.wampPublisher.publishToAll("event1", "event1:" + Instant.now());
			}
		}
		else {
			if (this.subscriptionRegistry.hasSubscribers("event2")) {
				this.wampPublisher.publishToAll("event2", "event2:" + Instant.now());
			}
		}
	}

}
