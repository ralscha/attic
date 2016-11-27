package ch.rasc.e4desk.service;

import ch.rasc.extclassgenerator.Model;

@Model(value = "E4desk.model.Module", readMethod = "moduleService.read")
public class Module {

	private final String id;

	private final String name;

	private final String iconCls;

	private final boolean showOnDesktop;

	private final String menuPath;

	public Module(String id, String name, String iconCls, boolean showOnDesktop,
			String menuPath) {
		this.id = id;
		this.name = name;
		this.iconCls = iconCls;
		this.showOnDesktop = showOnDesktop;
		this.menuPath = menuPath;
	}

	public Module(String id, String name, String iconCls, boolean showOnDesktop) {
		this(id, name, iconCls, showOnDesktop, null);
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public boolean isShowOnDesktop() {
		return this.showOnDesktop;
	}

	public String getMenuPath() {
		return this.menuPath;
	}

}
