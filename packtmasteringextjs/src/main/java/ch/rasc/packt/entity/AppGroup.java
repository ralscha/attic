package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Model(value = "Packt.model.security.Group", disablePagingParameters = true,
		paging = true, createMethod = "groupService.create",
		readMethod = "groupService.read", updateMethod = "groupService.update",
		destroyMethod = "groupService.destroy")
public class AppGroup extends AbstractPersistable {
	private String name;

	@Transient
	@ModelField
	private Long[] permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long[] getPermissions() {
		return permissions;
	}

	public void setPermissions(Long[] permissions) {
		this.permissions = permissions;
	}

}
