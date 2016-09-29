package ch.rasc.jacksonhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.jacksonhibernate.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	// nothing here
}
