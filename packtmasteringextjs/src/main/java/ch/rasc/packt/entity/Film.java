package ch.rasc.packt.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Packt.model.film.Film", paging = true,
		createMethod = "filmService.create", readMethod = "filmService.read",
		updateMethod = "filmService.update", destroyMethod = "filmService.destroy")
public class Film extends SakilaBaseEntity {

	private String title;

	private String description;

	@ModelField(useNull = true, convert = "null")
	private Integer releaseYear;

	@ManyToOne
	@JoinColumn(name = "languageId")
	@JsonIgnore
	private Language language;

	@ManyToOne
	@JoinColumn(name = "originalLanguageId")
	@JsonIgnore
	private Language originalLanguage;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long languageId;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long originalLanguageId;

	@ModelField(useNull = true, convert = "null")
	private Integer rentalDuration;

	@ModelField(useNull = true, convert = "null")
	private BigDecimal rentalRate;

	@ModelField(useNull = true, convert = "null")
	private Integer length;

	@ModelField(useNull = true, convert = "null")
	private BigDecimal replacementCost;

	private String rating;

	private String specialFeatures;

	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<FilmActor> filmActor;

	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<FilmCategory> filmCategory;

	@Transient
	@ModelField
	private Long[] filmCategoryIds;

	@Transient
	@ModelField
	private Long[] filmActorIds;

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

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(Language originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public BigDecimal getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Long getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(Long originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public Set<FilmActor> getFilmActor() {
		return filmActor;
	}

	public void setFilmActor(Set<FilmActor> filmActor) {
		this.filmActor = filmActor;
	}

	public Set<FilmCategory> getFilmCategory() {
		return filmCategory;
	}

	public void setFilmCategory(Set<FilmCategory> filmCategory) {
		this.filmCategory = filmCategory;
	}

	public Long[] getFilmCategoryIds() {
		return filmCategoryIds;
	}

	public void setFilmCategoryIds(Long[] filmCategoryIds) {
		this.filmCategoryIds = filmCategoryIds;
	}

	public Long[] getFilmActorIds() {
		return filmActorIds;
	}

	public void setFilmActorIds(Long[] filmActorIds) {
		this.filmActorIds = filmActorIds;
	}

	@PostLoad
	@PostUpdate
	@PostPersist
	private void populate() {
		if (language != null) {
			languageId = language.getId();
		}
		else {
			languageId = null;
		}

		if (originalLanguage != null) {
			originalLanguageId = originalLanguage.getId();
		}
		else {
			originalLanguageId = null;
		}
	}

}
