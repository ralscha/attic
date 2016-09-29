package ch.rasc.jacksonhibernate.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ch.rasc.jacksonhibernate.EntityIdResolver;

@Entity
@JsonIgnoreProperties("new")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
		resolver = EntityIdResolver.class, scope = Player.class)
public class Player extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	private String lastName;

	private String firstName;

	@ManyToOne
	@JoinColumn(name = "teamId", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Team team;

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}