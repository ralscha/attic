package ch.rasc.proto.entity;

import java.time.LocalDate;

import ch.rasc.edsutil.jackson.ISO8601LocalDateDeserializer;
import ch.rasc.edsutil.jackson.ISO8601LocalDateSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Model(value = "Proto.model.Project", readMethod = "projectService.read",
		createMethod = "projectService.update", updateMethod = "projectService.update",
		destroyMethod = "projectService.destroy", rootProperty = "records",
		identifier = "negative")
public class Project extends AbstractPersistable {

	private String name;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms1;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms2;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms3;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms4;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms5;

	@JsonSerialize(using = ISO8601LocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@ModelField(dateFormat = "Y-m-d")
	private LocalDate ms6;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getMs1() {
		return this.ms1;
	}

	public void setMs1(LocalDate ms1) {
		this.ms1 = ms1;
	}

	public LocalDate getMs2() {
		return this.ms2;
	}

	public void setMs2(LocalDate ms2) {
		this.ms2 = ms2;
	}

	public LocalDate getMs3() {
		return this.ms3;
	}

	public void setMs3(LocalDate ms3) {
		this.ms3 = ms3;
	}

	public LocalDate getMs4() {
		return this.ms4;
	}

	public void setMs4(LocalDate ms4) {
		this.ms4 = ms4;
	}

	public LocalDate getMs5() {
		return this.ms5;
	}

	public void setMs5(LocalDate ms5) {
		this.ms5 = ms5;
	}

	public LocalDate getMs6() {
		return this.ms6;
	}

	public void setMs6(LocalDate ms6) {
		this.ms6 = ms6;
	}

}
