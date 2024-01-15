package ch.rasc.messenger.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import ch.rasc.messenger.MongoDb;
import ch.rasc.messenger.domain.CUser;
import ch.rasc.messenger.domain.Location;
import ch.rasc.messenger.domain.User;

@RestController
@RequestMapping("/user")
public class UserController {

	private final MongoDb mongoDb;

	public UserController(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {
		this.mongoDb.getCollection(User.class).insertOne(user);
		return user;
	}

	@GetMapping
	public List<User> list() {
		return MongoDb.toList(this.mongoDb.getCollection(User.class).find());
	}

	@GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
	public List<User> findByBoundingBox(@PathVariable Double xMin,
			@PathVariable Double yMin, @PathVariable Double xMax,
			@PathVariable Double yMax) {

		return MongoDb.toList(this.mongoDb.getCollection(User.class)
				.find(Filters.geoWithinBox(CUser.location, xMin, yMin, xMax, yMax)));
	}

	@PutMapping("/{userName}/location/{x},{y}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateLocation(@PathVariable String userName, @PathVariable Double x,
			@PathVariable Double y) {
		Location newLocation = new Location(x, y);
		this.mongoDb.getCollection(User.class).updateOne(
				Filters.eq(CUser.userName, userName),
				Updates.set(CUser.location, newLocation));
	}

}
