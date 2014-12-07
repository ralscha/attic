package ch.rasc.phoenix.entity;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Phoenix.model.Probability", readMethod = "probabilityService.read")
public class Probability extends BaseOption {
	// nothing here
}
