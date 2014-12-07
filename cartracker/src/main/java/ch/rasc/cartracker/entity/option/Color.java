package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Color", readMethod = "colorOptionService.read",
		createMethod = "colorOptionService.create",
		updateMethod = "colorOptionService.update",
		destroyMethod = "colorOptionService.destroy", paging = true)
public class Color extends BaseOption {
	// nothing special
}
