package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.Feature",
		readMethod = "featureOptionService.read",
		createMethod = "featureOptionService.create",
		updateMethod = "featureOptionService.update",
		destroyMethod = "featureOptionService.destroy", paging = true)
public class Feature extends BaseOption {
	// nothing special
}
