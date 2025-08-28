package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.AppUser.APP_USER;

import java.util.List;
import java.util.stream.Collectors;

import org.hashids.Hashids;
import org.jooq.DSLContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.travellog.config.security.SessionCacheInvalidateEvent;
import ch.rasc.travellog.dto.User;
import jakarta.validation.constraints.NotEmpty;

@RestController
@Validated
@RequestMapping("/be/admin")
class AdminController {

	private final DSLContext dsl;

	private final Hashids hashids;

	private final ApplicationEventPublisher publisher;

	public AdminController(DSLContext dsl, ApplicationEventPublisher publisher) {
		this.dsl = dsl;
		this.hashids = new Hashids();
		this.publisher = publisher;
	}

	@GetMapping("/users")
	public List<User> fetchUsers() {
		var result = this.dsl.selectFrom(APP_USER).fetch();
		return result.stream()
				.map(user -> new User(this.hashids.encode(user.getId()), user))
				.collect(Collectors.toList());
	}

	@PostMapping("/activate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@RequestBody @NotEmpty String hashedId) {
		long userId = this.hashids.decode(hashedId)[0];
		this.dsl.update(APP_USER).setNull(APP_USER.EXPIRED).where(APP_USER.ID.eq(userId))
				.execute();
		this.publisher.publishEvent(SessionCacheInvalidateEvent.ofUserId(userId));
	}

	@PostMapping("/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestBody @NotEmpty String hashedId) {
		long userId = this.hashids.decode(hashedId)[0];
		this.dsl.delete(APP_USER).where(APP_USER.ID.eq(userId)).execute();
		this.publisher.publishEvent(SessionCacheInvalidateEvent.ofUserId(userId));
	}

	@PostMapping("/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enable(@RequestBody @NotEmpty String hashedId) {
		long userId = this.hashids.decode(hashedId)[0];
		this.dsl.update(APP_USER).set(APP_USER.ENABLED, true)
				.where(APP_USER.ID.eq(userId)).execute();
		this.publisher.publishEvent(SessionCacheInvalidateEvent.ofUserId(userId));
	}

	@PostMapping("/disable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disable(@RequestBody @NotEmpty String hashedId) {
		long userId = this.hashids.decode(hashedId)[0];
		this.dsl.update(APP_USER).set(APP_USER.ENABLED, false)
				.where(APP_USER.ID.eq(userId)).execute();
		this.publisher.publishEvent(SessionCacheInvalidateEvent.ofUserId(userId));
	}

}
