package ch.rasc.springjpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import ch.rasc.springjpa.entity.QUser;
import ch.rasc.springjpa.entity.User;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

public class UserRepositoryImpl implements UserRepositoryCustom {

	private final EntityManager entityManager;

	@Autowired
	public UserRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<User> filter(String name, String firstName, String email) {

		JPQLQuery query = new JPAQuery(this.entityManager);
		query.from(QUser.user);

		if (StringUtils.hasText(name)) {
			query.where(QUser.user.name.eq(name));
		}

		if (StringUtils.hasText(firstName)) {
			query.where(QUser.user.firstName.eq(firstName));
		}

		if (StringUtils.hasText(email)) {
			query.where(QUser.user.email.eq(email));
		}

		return query.list(QUser.user);

	}

}
