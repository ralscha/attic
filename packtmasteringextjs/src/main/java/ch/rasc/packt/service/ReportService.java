package ch.rasc.packt.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.packt.entity.QCategory;
import ch.rasc.packt.entity.QFilm;
import ch.rasc.packt.entity.QFilmCategory;
import ch.rasc.packt.entity.QPayment;
import ch.rasc.packt.entity.QRental;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class ReportService {

	@PersistenceContext
	protected EntityManager entityManager;

	@PreAuthorize("isAuthenticated()")
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> read() {
		JPAQuery query = new JPAQuery(entityManager).from(QPayment.payment);
		query.innerJoin(QPayment.payment.rental, QRental.rental);
		query.innerJoin(QRental.rental.film, QFilm.film);
		query.innerJoin(QFilm.film.filmCategory, QFilmCategory.filmCategory);
		query.innerJoin(QFilmCategory.filmCategory.category, QCategory.category);
		query.groupBy(QCategory.category.name);
		query.orderBy(QPayment.payment.amount.sum().desc());
		List<Tuple> result = query.list(QCategory.category.name,
				QPayment.payment.amount.sum());

		ImmutableList.Builder<Map<String, Object>> r = ImmutableList.builder();
		for (Tuple tuple : result) {
			r.add(ImmutableMap.<String, Object> of("category",
					tuple.get(QCategory.category.name), "totalSales",
					tuple.get(QPayment.payment.amount.sum())));
		}

		return r.build();

	}

}
