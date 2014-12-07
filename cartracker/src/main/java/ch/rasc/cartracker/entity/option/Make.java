package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Make", readMethod = "makeOptionService.read",
		createMethod = "makeOptionService.create",
		updateMethod = "makeOptionService.update",
		destroyMethod = "makeOptionService.destroy", paging = true)
public class Make extends BaseOption {
	// nothing special
}
