package ch.rasc.springmongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PersonRepository
		extends MongoRepository<Person, String>, QueryDslPredicateExecutor<Person> {
	// nothing here
}