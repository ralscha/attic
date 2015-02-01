package ch.rasc.e4ds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.e4ds.entity.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
	// nothing here
}
