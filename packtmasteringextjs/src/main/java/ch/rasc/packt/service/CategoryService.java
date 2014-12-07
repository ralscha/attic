package ch.rasc.packt.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.packt.entity.Category;
import ch.rasc.packt.entity.QCategory;
import ch.rasc.packt.entity.QFilmCategory;

import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class CategoryService extends BaseCRUDService<Category> {
	@PreAuthorize("isAuthenticated()")
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<Category> readCategory(ExtDirectStoreReadRequest request,
			@RequestParam(required = false) Long filmId) {
		if (filmId != null) {
			return new ExtDirectStoreResult<>(new JPAQuery(entityManager)
					.from(QCategory.category)
					.innerJoin(QCategory.category.filmCategory,
							QFilmCategory.filmCategory)
					.where(QFilmCategory.filmCategory.film.id.eq(filmId))
					.list(QCategory.category));
		}

		return super.read(request);
	}

}
