package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.Log.LOG;
import static ch.rasc.travellog.db.tables.Travel.TRAVEL;

import java.time.Instant;
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
import ch.rasc.travellog.dto.LogSync;
import ch.rasc.travellog.dto.SyncRequest;
import ch.rasc.travellog.dto.SyncResponse;
import ch.rasc.travellog.dto.SyncResponse.NewId;

@RestController
@RequestMapping("/be")
class LogController {

	private final DSLContext dsl;

	public LogController(DSLContext dsl) {
		this.dsl = dsl;
	}

	@GetMapping("log_syncview")
	public Map<Long, Long> getSyncView(
			@AuthenticationPrincipal AppUserDetail userDetails) {

		Map<Long, Long> result = new HashMap<>();

		Set<Long> travelIds = this.dsl.select(TRAVEL.ID).from(TRAVEL)
				.where(TRAVEL.APP_USER_ID.eq(userDetails.getAppUserId()))
				.fetchSet(TRAVEL.ID);

		this.dsl.select(LOG.ID, LOG.UPDATED).from(LOG).where(LOG.TRAVEL_ID.in(travelIds))
				.fetch().forEach(record -> result.put(record.get(LOG.ID),
						record.get(LOG.UPDATED).toEpochSecond(ZoneOffset.UTC)));

		return result;
	}

	@PostMapping("log_sync")
	public SyncResponse<LogSync> sync(@RequestBody SyncRequest<LogSync> sync,
			@AuthenticationPrincipal AppUserDetail userDetails) {
		Set<Long> removed = null;
		Map<Long, Long> updated = null;
		Map<Long, NewId> inserted = null;
		List<LogSync> get = new ArrayList<>();

		long loggedInUserId = userDetails.getAppUserId();

		Set<Long> travelIds = this.dsl.select(TRAVEL.ID).from(TRAVEL)
				.where(TRAVEL.APP_USER_ID.eq(loggedInUserId)).fetchSet(TRAVEL.ID);

		// delete
		if (sync.getRemoved() != null && !sync.getRemoved().isEmpty()) {
			removed = new HashSet<>();
			for (Long id : sync.getRemoved()) {
				int noOfDeleted = this.dsl.delete(LOG)
						.where(LOG.ID.eq(id).and(LOG.TRAVEL_ID.in(travelIds))).execute();
				if (noOfDeleted == 1) {
					removed.add(id);
				}
			}
		}

		// update
		if (sync.getUpdated() != null && !sync.getUpdated().isEmpty()) {
			updated = new HashMap<>();
			for (LogSync clientLog : sync.getUpdated()) {
				var record = this.dsl
						.select(LOG.ID, LOG.UPDATED, LOG.TRAVEL_ID, LOG.CREATED, LOG.LAT,
								LOG.LNG, LOG.LOCATION, LOG.REPORT)
						.from(LOG).where(LOG.ID.eq(clientLog.getId())
								.and(LOG.TRAVEL_ID.in(travelIds)))
						.fetchOne();

				if (record != null) {
					if (record.get(LOG.UPDATED).toEpochSecond(ZoneOffset.UTC) > clientLog
							.getTs()) {
						// db log is newer than the version sent from the client. ignore
						// client
						// update
						get.add(new LogSync(record.get(LOG.ID),
								record.get(LOG.UPDATED).toEpochSecond(ZoneOffset.UTC),
								record.get(LOG.TRAVEL_ID),
								record.get(LOG.CREATED).toEpochSecond(ZoneOffset.UTC),
								record.get(LOG.LAT), record.get(LOG.LNG),
								record.get(LOG.LOCATION), record.get(LOG.REPORT)));
					}
					else {
						LocalDateTime now = LocalDateTime.now();
						int noOfUpdated = this.dsl.update(LOG).set(LOG.CREATED,
								LocalDateTime.ofInstant(
										Instant.ofEpochSecond(clientLog.getCreated()),
										ZoneOffset.UTC))
								.set(LOG.LAT, clientLog.getLat())
								.set(LOG.LNG, clientLog.getLng())
								.set(LOG.LOCATION, clientLog.getLocation())
								.set(LOG.REPORT, clientLog.getReport())
								.set(LOG.UPDATED, now).where(LOG.ID.eq(clientLog.getId())
										.and(LOG.TRAVEL_ID.in(travelIds)))
								.execute();
						if (noOfUpdated == 1) {
							updated.put(clientLog.getId(),
									now.toEpochSecond(ZoneOffset.UTC));
						}
					}
				}
			}
		}

		// insert
		if (sync.getInserted() != null && !sync.getInserted().isEmpty()) {
			inserted = new HashMap<>();
			for (LogSync clientLog : sync.getInserted()) {
				LocalDateTime now = LocalDateTime.now();

				long id = this.dsl
						.insertInto(LOG, LOG.CREATED, LOG.LAT, LOG.LNG, LOG.LOCATION,
								LOG.REPORT, LOG.UPDATED, LOG.TRAVEL_ID)
						.values(LocalDateTime.ofInstant(
								Instant.ofEpochSecond(clientLog.getCreated()),
								ZoneOffset.UTC), clientLog.getLat(), clientLog.getLng(),
								clientLog.getLocation(), clientLog.getReport(), now,
								clientLog.getTravelId())
						.returning(LOG.ID).fetchOne().getId();

				inserted.put(clientLog.getId(),
						new NewId(id, now.toEpochSecond(ZoneOffset.UTC)));
			}
		}

		// gets
		if (sync.getGets() != null) {
			for (Long id : sync.getGets()) {
				var record = this.dsl
						.select(LOG.ID, LOG.UPDATED, LOG.TRAVEL_ID, LOG.CREATED, LOG.LAT,
								LOG.LNG, LOG.LOCATION, LOG.REPORT)
						.from(LOG).where(LOG.ID.eq(id).and(LOG.TRAVEL_ID.in(travelIds)))
						.fetchOne();

				if (record != null) {
					get.add(new LogSync(record.get(LOG.ID),
							record.get(LOG.UPDATED).toEpochSecond(ZoneOffset.UTC),
							record.get(LOG.TRAVEL_ID),
							record.get(LOG.CREATED).toEpochSecond(ZoneOffset.UTC),
							record.get(LOG.LAT), record.get(LOG.LNG),
							record.get(LOG.LOCATION), record.get(LOG.REPORT)));
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
