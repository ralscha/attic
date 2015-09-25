package ch.rasc.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import ch.rasc.webapp.entity.User;

public interface UserRepository
		extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
	User findByUserName(String userName);
}
