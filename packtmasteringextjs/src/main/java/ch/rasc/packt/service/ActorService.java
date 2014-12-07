package ch.rasc.packt.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.packt.entity.Actor;
import ch.rasc.packt.entity.Category;
import ch.rasc.packt.entity.QActor;
import ch.rasc.packt.entity.QCategory;
import ch.rasc.packt.entity.QFilm;
import ch.rasc.packt.entity.QFilmActor;
import ch.rasc.packt.entity.QFilmCategory;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class ActorService extends BaseCRUDService<Actor> {

	@PreAuthorize("isAuthenticated()")
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<Actor> readActor(ExtDirectStoreReadRequest request,
			@RequestParam(required = false) Long filmId,
			@RequestParam(required = false) Long id) {
		if (filmId != null) {
			return new ExtDirectStoreResult<>(new JPAQuery(entityManager)
					.from(QActor.actor)
					.innerJoin(QActor.actor.filmActor, QFilmActor.filmActor)
					.where(QFilmActor.filmActor.film.id.eq(filmId)).list(QActor.actor));
		}
		else if (id != null) {
			return new ExtDirectStoreResult<>(entityManager.find(Actor.class, id));
		}

		if (StringUtils.hasText(request.getQuery())) {
			JPQLQuery query = new JPAQuery(entityManager).from(QActor.actor);
			addPagingAndSorting(request, query, createPathBuilder());
			query.where(QActor.actor.firstName.startsWithIgnoreCase(request.getQuery())
					.or(QActor.actor.lastName.startsWithIgnoreCase(request.getQuery())));
			SearchResults<Actor> searchResult = query.listResults(QActor.actor);
			ExtDirectStoreResult<Actor> result = new ExtDirectStoreResult<>(
					searchResult.getTotal(), searchResult.getResults());

			for (Actor actor : result.getRecords()) {

				query = new JPAQuery(entityManager).from(QCategory.category);
				query.innerJoin(QCategory.category.filmCategory,
						QFilmCategory.filmCategory);
				query.innerJoin(QFilmCategory.filmCategory.film.filmActor,
						QFilmActor.filmActor);
				query.where(QFilmActor.filmActor.actor.eq(actor));

				StringBuilder sb = new StringBuilder(255);
				for (Category category : query.list(QCategory.category)) {
					sb.append(category.getName());
					sb.append(": ");

					for (String title : new JPAQuery(entityManager)
							.from(QFilm.film)
							.innerJoin(QFilm.film.filmCategory,
									QFilmCategory.filmCategory)
							.innerJoin(QFilm.film.filmActor, QFilmActor.filmActor)
							.where(QFilmCategory.filmCategory.category.eq(category),
									QFilmActor.filmActor.actor.eq(actor))
							.list(QFilm.film.title)) {
						sb.append(title);

					}
					sb.append("<br>");

					// P_CONCAT(f.title ORDER BY f.title SEPARATOR ', ')
					// FROM sakila.film f
					// INNER JOIN sakila.film_category fc
					// ON f.film_id = fc.film_id
					// INNER JOIN sakila.film_actor fa
					// ON f.film_id = fa.film_id
					// WHERE fc.category_id = c.category_id
					// AND fa.actor_id = a.actor_id
				}

				actor.setFilmInfo(sb.toString());
			}
			return result;
		}

		return super.read(request);

	}

}
