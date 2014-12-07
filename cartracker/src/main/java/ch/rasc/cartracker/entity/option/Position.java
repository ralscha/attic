package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Position",
		readMethod = "positionOptionService.read",
		createMethod = "positionOptionService.create",
		updateMethod = "positionOptionService.update",
		destroyMethod = "positionOptionService.destroy", paging = true)
public class Position extends BaseOption {
	// nothing special
}
