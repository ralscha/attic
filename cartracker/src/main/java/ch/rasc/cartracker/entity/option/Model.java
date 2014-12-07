package ch.rasc.cartracker.entity.option;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import ch.rasc.edsutil.SortProperty;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@ch.rasc.extclassgenerator.Model(value = "CarTracker.model.option.Model",
		readMethod = "modelOptionService.read",
		createMethod = "modelOptionService.create",
		updateMethod = "modelOptionService.update",
		destroyMethod = "modelOptionService.destroy", paging = true)
public class Model extends BaseOption {
	@ManyToOne
	@JoinColumn(name = "makeId")
	@JsonIgnore
	private Make make;

	@Transient
	@ModelField(useNull = true)
	@SortProperty("make.longName")
	private Long makeId;

	@Transient
	@ModelField(persist = false)
	private String makeName;

	public Make getMake() {
		return make;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	public Long getMakeId() {
		return makeId;
	}

	public void setMakeId(Long makeId) {
		this.makeId = makeId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {
		if (make != null) {
			makeName = make.getLongName();
			makeId = make.getId();
		}
		else {
			makeName = null;
			makeId = null;
		}
	}
}
