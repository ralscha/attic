package ch.rasc.springmongodb.fulltext;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PostRepository
		extends MongoRepository<Post, String>, QueryDslPredicateExecutor<Post> {
	// nothing here
}