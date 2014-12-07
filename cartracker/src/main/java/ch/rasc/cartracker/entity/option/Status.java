package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Status", readMethod = "statusOptionService.read",
		createMethod = "statusOptionService.create",
		updateMethod = "statusOptionService.update",
		destroyMethod = "statusOptionService.destroy", paging = true)
public class Status extends BaseOption {
	// nothing special
}
