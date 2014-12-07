package ch.rasc.packt.entity;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Packt.model.staticData.Country", disablePagingParameters = true,
		paging = true, createMethod = "countryService.create",
		readMethod = "countryService.read", updateMethod = "countryService.update",
		destroyMethod = "countryService.destroy")
public class Country extends SakilaBaseEntity {

	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
