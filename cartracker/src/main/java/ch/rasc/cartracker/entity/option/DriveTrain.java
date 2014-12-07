package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.DriveTrain",
		readMethod = "driveTrainOptionService.read",
		createMethod = "driveTrainOptionService.create",
		updateMethod = "driveTrainOptionService.update",
		destroyMethod = "driveTrainOptionService.destroy", paging = true)
public class DriveTrain extends BaseOption {
	// nothing special
}
