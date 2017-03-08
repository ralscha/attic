package ch.rasc.sse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

@Controller
@Consumer
public class SimpleController {

	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public final EventBus eventBus;

	@Autowired
	public SimpleController(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@RequestMapping(path = "/simplesse", method = RequestMethod.GET)
	public SseEmitter handle() {
		SseEmitter emitter = new SseEmitter();
		this.emitters.add(emitter);

		emitter.onCompletion(() -> this.emitters.remove(emitter));
		emitter.onTimeout(() -> this.emitters.remove(emitter));

		return emitter;
	}

	@Selector("test.topic")
	public void onTestTopic(String message) {
		List<SseEmitter> deadEmitters = new ArrayList<>();
		this.emitters.forEach(emitter -> {
			try {
				emitter.send(message);
			}
			catch (Exception e) {
				deadEmitters.add(emitter);
			}
		});

		this.emitters.remove(deadEmitters);
	}

}
