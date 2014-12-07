package ch.rasc.phoenix.entity;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Phoenix.model.AffectedItem", readMethod = "affectedItemService.read")
public class AffectedItem extends BaseOption {
	// nothing here
}
