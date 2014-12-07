package ch.rasc.packt.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ch.rasc.extclassgenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Packt.model.staticData.Actor", paging = true,
		createMethod = "actorService.create", readMethod = "actorService.readActor",
		updateMethod = "actorService.update", destroyMethod = "actorService.destroy")
public class Actor extends SakilaBaseEntity {

	private String firstName;

	private String lastName;

	@Transient
	private String filmInfo;

	@OneToMany(mappedBy = "actor")
	@JsonIgnore
	private Set<FilmActor> filmActor;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFilmInfo() {
		return filmInfo;
	}

	public void setFilmInfo(String filmInfo) {
		this.filmInfo = filmInfo;
	}

	public Set<FilmActor> getFilmActor() {
		return filmActor;
	}

	public void setFilmActor(Set<FilmActor> filmActor) {
		this.filmActor = filmActor;
	}

}
