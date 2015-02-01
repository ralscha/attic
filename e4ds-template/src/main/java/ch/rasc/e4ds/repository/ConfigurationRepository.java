package ch.rasc.e4ds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.e4ds.entity.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	// nothing here
}
