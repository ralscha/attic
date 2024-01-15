package ch.rasc.ab.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Ab.model.ContactGroup", readMethod = "contactGroupService.read",
		createMethod = "contactGroupService.create",
		updateMethod = "contactGroupService.update",
		destroyMethod = "contactGroupService.destroy", paging = true,
		disablePagingParameters = true)
public class ContactGroup extends AbstractPersistable {

	@NotEmpty(message = "Please enter Name")
	@Size(max = 255)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
