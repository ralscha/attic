package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Category",
		readMethod = "categoryOptionService.read",
		createMethod = "categoryOptionService.create",
		updateMethod = "categoryOptionService.update",
		destroyMethod = "categoryOptionService.destroy", paging = true)
public class Category extends BaseOption {
	// nothing special
}
