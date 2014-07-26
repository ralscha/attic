package ch.rasc.e4ds.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ch.rasc.e4ds.domain.User;
import ch.rasc.e4ds.util.Util;

@Repository
public class UserCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public Page<User> findWithFilter(String filterValue, Pageable pageable) {

		Query query = null;

		if (StringUtils.hasText(filterValue)) {
			query = new Query(new Criteria().orOperator(Criteria.where("userName").regex(filterValue, "i"), Criteria
					.where("name").regex(filterValue, "i"), Criteria.where("firstName").regex(filterValue, "i"),
					Criteria.where("email").regex(filterValue, "i")));
		} else {
			query = new Query();
		}

		Long count = mongoTemplate.count(query, User.class);
		Util.applyPagination(query, pageable);
		List<User> users = mongoTemplate.find(query, User.class);
		return new PageImpl<>(users, pageable, count);
	}

}
