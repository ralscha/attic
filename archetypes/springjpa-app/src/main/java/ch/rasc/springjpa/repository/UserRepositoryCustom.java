package ch.rasc.springjpa.repository;

import java.util.List;

import ch.rasc.springjpa.entity.User;

public interface UserRepositoryCustom {
	List<User> filter(String name, String firstName, String email);
}
