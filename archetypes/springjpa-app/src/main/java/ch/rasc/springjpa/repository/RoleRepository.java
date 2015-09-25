package ch.rasc.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.rasc.springjpa.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
