package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.Travel.TRAVEL;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jooq.DSLContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.travellog.config.security.AppUserDetail;
import ch.rasc.travellog.dto.SyncRequest;
import ch.rasc.travellog.dto.SyncResponse;
import ch.rasc.travellog.dto.SyncResponse.NewId;
import ch.rasc.travellog.dto.TravelSync;

@RestController
@RequestMapping("/be")
class TravelController {

	private final DSLContext dsl;

	public TravelController(DSLContext dsl) {
		this.dsl = dsl;
	}

	@GetMapping("travel_syncview")
	public Map<Long, Long> getSyncView(
			@AuthenticationPrincipal AppUserDetail userDetails) {

		Map<Long, Long> result = new HashMap<>();

		this.dsl.select(TRAVEL.ID, TRAVEL.UPDATED).from(TRAVEL)
				.where(TRAVEL.APP_USER_ID.eq(userDetails.getAppUserId())).fetch()
				.forEach(record -> result.put(record.get(TRAVEL.ID),
						record.get(TRAVEL.UPDATED).toEpochSecond(ZoneOffset.UTC)));

		return result;
	}

	@PostMapping("travel_sync")
	public SyncResponse<TravelSync> sync(@RequestBody SyncRequest<TravelSync> sync,
			@AuthenticationPrincipal AppUserDetail userDetails) {
		Set<Long> removed = null;
		Map<Long, Long> updated = null;
		Map<Long, NewId> inserted = null;
		List<TravelSync> get = new ArrayList<>();

		long loggedInUserId = userDetails.getAppUserId();

		// delete
		if (sync.getRemoved() != null && !sync.getRemoved().isEmpty()) {
			removed = new HashSet<>();
			for (Long id : sync.getRemoved()) {
				int noOfDeleted = this.dsl.delete(TRAVEL).where(
						TRAVEL.ID.eq(id).and(TRAVEL.APP_USER_ID.eq(loggedInUserId)))
						.execute();
				if (noOfDeleted == 1) {
					removed.add(id);
				}
			}
		}

		// update
		if (sync.getUpdated() != null && !sync.getUpdated().isEmpty()) {
			updated = new HashMap<>();
			for (TravelSync clientTravel : sync.getUpdated()) {
				var record = this.dsl.select(TRAVEL.ID, TRAVEL.UPDATED, TRAVEL.NAME)
						.from(TRAVEL).where(TRAVEL.ID.eq(clientTravel.getId())
								.and(TRAVEL.APP_USER_ID.eq(loggedInUserId)))
						.fetchOne();

				if (record != null) {
					if (record.get(TRAVEL.UPDATED)
							.toEpochSecond(ZoneOffset.UTC) > clientTravel.getTs()) {
						// db travel is newer than the version sent from the client.
						// ignore client
						// update
						get.add(new TravelSync(record.get(TRAVEL.ID),
								record.get(TRAVEL.UPDATED).toEpochSecond(ZoneOffset.UTC),
								record.get(TRAVEL.NAME)));
					}
					else {
						LocalDateTime now = LocalDateTime.now();
						int noOfUpdated = this.dsl.update(TRAVEL)
								.set(TRAVEL.NAME, clientTravel.getName())
								.set(TRAVEL.UPDATED, now)
								.where(TRAVEL.ID.eq(clientTravel.getId())
										.and(TRAVEL.APP_USER_ID.eq(loggedInUserId)))
								.execute();
						if (noOfUpdated == 1) {
							updated.put(clientTravel.getId(),
									now.toEpochSecond(ZoneOffset.UTC));
						}
					}
				}
			}
		}

		// insert
		if (sync.getInserted() != null && !sync.getInserted().isEmpty()) {
			inserted = new HashMap<>();
			for (TravelSync clientTravel : sync.getInserted()) {
				LocalDateTime now = LocalDateTime.now();

				long id = this.dsl
						.insertInto(TRAVEL, TRAVEL.NAME, TRAVEL.UPDATED,
								TRAVEL.APP_USER_ID)
						.values(clientTravel.getName(), now, loggedInUserId)
						.returning(TRAVEL.ID).fetchOne().getId();

				inserted.put(clientTravel.getId(),
						new NewId(id, now.toEpochSecond(ZoneOffset.UTC)));
			}
		}

		// gets
		if (sync.getGets() != null) {
			for (Long id : sync.getGets()) {
				var record = this.dsl.select(TRAVEL.ID, TRAVEL.UPDATED, TRAVEL.NAME)
						.from(TRAVEL).where(TRAVEL.ID.eq(id)
								.and(TRAVEL.APP_USER_ID.eq(loggedInUserId)))
						.fetchOne();

				if (record != null) {
					get.add(new TravelSync(record.get(TRAVEL.ID),
							record.get(TRAVEL.UPDATED).toEpochSecond(ZoneOffset.UTC),
							record.get(TRAVEL.NAME)));
				}
			}
		}

		if (get.isEmpty()) {
			get = null;
		}
		if (removed != null && removed.isEmpty()) {
			removed = null;
		}
		if (updated != null && updated.isEmpty()) {
			updated = null;
		}

		return new SyncResponse<>(get, inserted, updated, removed);
	}

}
