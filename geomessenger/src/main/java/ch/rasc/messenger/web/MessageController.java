package ch.rasc.messenger.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.mongodb.client.model.Filters;

import ch.rasc.messenger.MongoDb;
import ch.rasc.messenger.domain.CMessage;
import ch.rasc.messenger.domain.Message;

@RestController
@RequestMapping("/message")
public class MessageController {

	private final SseBroadcaster sseBroadcaster;

	private final MongoDb mongoDb;

	public MessageController(MongoDb mongoDb, SseBroadcaster sseBroadcaster) {
		this.mongoDb = mongoDb;
		this.sseBroadcaster = sseBroadcaster;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Message create(@RequestBody Message message) {
		this.mongoDb.getCollection(Message.class).insertOne(message);
		this.sseBroadcaster.send(message);
		return message;
	}

	@GetMapping
	public List<Message> list() {
		return MongoDb.toList(this.mongoDb.getCollection(Message.class).find());
	}

	@GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
	public List<Message> findByBoundingBox(@PathVariable Double xMin,
			@PathVariable Double yMin, @PathVariable Double xMax,
			@PathVariable Double yMax) {
		return MongoDb.toList(this.mongoDb.getCollection(Message.class)
				.find(Filters.geoWithinBox(CMessage.location, xMin, yMin, xMax, yMax)));
	}

	@GetMapping("/subscribe")
	public SseEmitter subscribe() {
		return this.sseBroadcaster.subscribe();
	}

}
