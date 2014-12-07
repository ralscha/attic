package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FilmActor extends SakilaBaseEntity {

	@ManyToOne
	@JoinColumn(name = "filmId")
	private Film film;

	@ManyToOne
	@JoinColumn(name = "actorId")
	private Actor actor;

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
