package ch.rasc.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.webapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
