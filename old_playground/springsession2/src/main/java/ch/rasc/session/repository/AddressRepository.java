package ch.rasc.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.session.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	// nothing here
}
