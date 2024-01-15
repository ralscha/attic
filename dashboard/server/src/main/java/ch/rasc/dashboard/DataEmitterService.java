package ch.rasc.dashboard;

import ch.rasc.sse.eventbus.SseEvent;
import ch.rasc.sse.eventbus.SseEventBus;

//@Component
public class DataEmitterService {
	private final SseEventBus eventBus;

	public DataEmitterService(SseEventBus eventBus) {
		this.eventBus = eventBus;
	}

	//@Scheduled(fixedDelay = 1000)
	public void broadcastEvent() {
		int spieler = (int) (Math.random() * 19) + 1;
		int ries = (int) (Math.random() * 4);
		int punkt = (int) (Math.random() * 30);
		boolean nr = Math.random() < 0.5;

		this.eventBus.handleEvent(SseEvent.ofData(ImmutablePunktEvent.builder()
				.spieler(spieler).ries(ries).punkt(punkt).nr(nr).build()));

	}

}