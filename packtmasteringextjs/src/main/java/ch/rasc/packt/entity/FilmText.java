package ch.rasc.packt.entity;

import javax.persistence.Entity;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class FilmText extends AbstractPersistable {

	private String title;

	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
