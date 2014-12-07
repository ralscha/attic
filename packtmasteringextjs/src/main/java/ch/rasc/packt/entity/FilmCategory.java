package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FilmCategory extends SakilaBaseEntity {

	@ManyToOne
	@JoinColumn(name = "filmId")
	private Film film;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
