package ch.rasc.packt.entity;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Packt.model.staticData.Language", disablePagingParameters = true,
		paging = true, createMethod = "languageService.create",
		readMethod = "languageService.read", updateMethod = "languageService.update",
		destroyMethod = "languageService.destroy")
public class Language extends SakilaBaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
