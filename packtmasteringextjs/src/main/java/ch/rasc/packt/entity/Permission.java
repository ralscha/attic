package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class Permission extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "appGroupId")
	private AppGroup appGroup;

	@ManyToOne
	@JoinColumn(name = "menuId")
	private Menu menu;

	public AppGroup getAppGroup() {
		return appGroup;
	}

	public void setAppGroup(AppGroup appGroup) {
		this.appGroup = appGroup;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
