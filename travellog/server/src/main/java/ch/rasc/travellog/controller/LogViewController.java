package ch.rasc.travellog.controller;

import static ch.rasc.travellog.db.tables.Log.LOG;
import static ch.rasc.travellog.db.tables.Travel.TRAVEL;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.travellog.dto.LogSync;

@RestController
@RequestMapping("/be")
class LogViewController {

	private final DSLContext dsl;

	public LogViewController(DSLContext dsl) {
		this.dsl = dsl;
	}

	@GetMapping("logview_name/{id}")
	public String logviewname(@PathVariable("id") long travelId) {
		return this.dsl.select(TRAVEL.NAME).from(TRAVEL).where(TRAVEL.ID.eq(travelId))
				.fetchOne(TRAVEL.NAME);
	}

	@GetMapping("logview/{id}")
	public List<LogSync> logs(@PathVariable("id") long travelId) {

		return this.dsl
				.select(LOG.ID, LOG.CREATED, LOG.LAT, LOG.LNG, LOG.LOCATION, LOG.REPORT)
				.from(LOG).where(LOG.TRAVEL_ID.eq(travelId)).orderBy(LOG.CREATED.desc())
				.fetch().stream()
				.map(record -> new LogSync(record.get(LOG.ID), 0, 0,
						record.get(LOG.CREATED).toEpochSecond(ZoneOffset.UTC),
						record.get(LOG.LAT), record.get(LOG.LNG),
						record.get(LOG.LOCATION), record.get(LOG.REPORT)))
				.collect(Collectors.toList());
	}

}
