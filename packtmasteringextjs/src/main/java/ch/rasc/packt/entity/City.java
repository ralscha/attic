package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Model(value = "Packt.model.staticData.City", disablePagingParameters = true,
		paging = true, createMethod = "cityService.create",
		readMethod = "cityService.read", updateMethod = "cityService.update",
		destroyMethod = "cityService.destroy")
public class City extends SakilaBaseEntity {

	private String city;

	@ManyToOne
	@JoinColumn(name = "countryId")
	private Country country;

	@Transient
	@ModelField(convert = "null", useNull = true)
	private Long countryId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@PostLoad
	@PostUpdate
	@PostPersist
	private void populate() {
		if (country != null) {
			countryId = country.getId();
		}
		else {
			countryId = null;
		}
	}
}
