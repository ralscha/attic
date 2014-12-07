package ch.rasc.packt.service;

import java.util.Date;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import ch.rasc.packt.entity.Actor;
import ch.rasc.packt.entity.Category;
import ch.rasc.packt.entity.Film;
import ch.rasc.packt.entity.FilmActor;
import ch.rasc.packt.entity.FilmCategory;
import ch.rasc.packt.entity.Language;

@Service
public class FilmService extends BaseCRUDService<Film> {

	@Override
	protected void preModify(Film entity) {

		if (entity.getId() != null) {
			Film dbFilm = entityManager.find(Film.class, entity.getId());

			entity.setFilmActor(dbFilm.getFilmActor());
			entity.setFilmCategory(dbFilm.getFilmCategory());

			entity.getFilmActor().clear();
			entity.getFilmCategory().clear();
		}
		else {
			entity.setFilmActor(new HashSet<FilmActor>());
			entity.setFilmCategory(new HashSet<FilmCategory>());
		}

		if (entity.getFilmActorIds() != null) {
			for (Long actorId : entity.getFilmActorIds()) {
				FilmActor filmActor = new FilmActor();
				filmActor.setFilm(entity);
				filmActor.setActor(entityManager.getReference(Actor.class, actorId));
				filmActor.setLastUpdate(new Date());
				entity.getFilmActor().add(filmActor);
			}
		}
		if (entity.getFilmCategoryIds() != null) {
			for (Long categoryId : entity.getFilmCategoryIds()) {
				FilmCategory filmCategory = new FilmCategory();
				filmCategory.setFilm(entity);
				filmCategory.setCategory(entityManager.getReference(Category.class,
						categoryId));
				filmCategory.setLastUpdate(new Date());
				entity.getFilmCategory().add(filmCategory);
			}
		}

		if (entity.getLanguageId() != null) {
			entity.setLanguage(entityManager.getReference(Language.class,
					entity.getLanguageId()));
		}
		else {
			entity.setLanguage(null);
		}

		if (entity.getOriginalLanguageId() != null) {
			entity.setOriginalLanguage(entityManager.getReference(Language.class,
					entity.getOriginalLanguageId()));
		}
		else {
			entity.setOriginalLanguage(null);
		}

	}

	// @Override
	// protected void postModify(Film entity) {
	//
	// }

}
