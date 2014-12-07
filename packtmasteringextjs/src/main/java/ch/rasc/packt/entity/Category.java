package ch.rasc.packt.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ch.rasc.extclassgenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Packt.model.staticData.Category", disablePagingParameters = true,
		paging = true, createMethod = "categoryService.create",
		readMethod = "categoryService.readCategory",
		updateMethod = "categoryService.update",
		destroyMethod = "categoryService.destroy")
public class Category extends SakilaBaseEntity {

	private String name;

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private Set<FilmCategory> filmCategory;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FilmCategory> getFilmCategory() {
		return filmCategory;
	}

	public void setFilmCategory(Set<FilmCategory> filmCategory) {
		this.filmCategory = filmCategory;
	}

}
