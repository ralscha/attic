package ch.rasc.packt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class Menu extends AbstractPersistable {
	private String menuText;

	private String iconCls;

	private String className;

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Menu parent;

	@OneToMany(mappedBy = "menu")
	private Set<Permission> permissions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
	private Set<Menu> children = new HashSet<>();

	public String getMenuText() {
		return menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

}
