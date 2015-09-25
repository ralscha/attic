package ch.rasc.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import ch.rasc.springjpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,
		QueryDslPredicateExecutor<User>, UserRepositoryCustom {
	User findByUserName(String userName);
}
