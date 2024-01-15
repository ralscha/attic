package ch.rasc.messenger.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ch.rasc.messenger.Application;

@Service
public class SseBroadcaster {

	private final Set<SseEmitter> sseEmitters = Collections
			.synchronizedSet(new HashSet<>());

	public SseEmitter subscribe() {
		SseEmitter sseEmitter = new SseEmitter();
		sseEmitter.onCompletion(() -> {
			this.sseEmitters.remove(sseEmitter);
			System.out.println("on completion");
		});

		// sseEmitter.onTimeout(sseEmitter::complete);
		// sseEmitter.onCompletion(sseEmitter::complete);

		this.sseEmitters.add(sseEmitter);
		return sseEmitter;
	}

	public void send(Object o) {
		synchronized (this.sseEmitters) {

			Set<SseEmitter> failedEmitters = new HashSet<>();

			this.sseEmitters.stream().forEach(e -> {
				try {
					e.send(o, MediaType.APPLICATION_JSON);
				}
				catch (Exception ex) {
					failedEmitters.add(e);
					Application.logger.error("send", ex);
				}
			});

			this.sseEmitters.removeAll(failedEmitters);
		}
	}

}
