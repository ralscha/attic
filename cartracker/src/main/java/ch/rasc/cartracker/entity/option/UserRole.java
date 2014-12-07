package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "CarTracker.model.option.UserRole",
		readMethod = "userRoleOptionService.read", paging = true)
public class UserRole extends BaseOption {
	// nothing special
}
