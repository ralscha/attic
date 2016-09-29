package ch.rasc.jacksonhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.jacksonhibernate.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	// nothing here
}
