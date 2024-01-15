package ch.rasc.ab.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.mutable.MutableInt;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuNode {

	private int id;

	private final String text;

	private String view;

	private boolean leaf;

	private boolean expanded;

	private String icon;

	@JsonIgnore
	private String role;

	private final List<MenuNode> children = new ArrayList<>();

	public MenuNode(String text) {
		this.text = text;
	}

	public MenuNode(String text, String icon, boolean expanded) {
		this.text = text;
		this.icon = icon;
		this.expanded = expanded;
	}

	public MenuNode(String text, String icon, String view, String role) {
		this.text = text;
		this.icon = icon;
		this.view = view;
		this.role = role;
	}

	public static MenuNode copyOf(MenuNode source,
			Collection<? extends GrantedAuthority> authorities, MutableInt mutableInt) {
		MenuNode menuNode = new MenuNode(source.getText());
		menuNode.id = mutableInt.intValue();
		mutableInt.add(1);
		menuNode.view = source.getView();
		menuNode.expanded = source.isExpanded();
		menuNode.icon = source.getIcon();

		List<MenuNode> children = new ArrayList<>();
		for (MenuNode sourceChild : source.getChildren()) {
			if (hasRole(sourceChild, authorities)) {
				MenuNode copy = MenuNode.copyOf(sourceChild, authorities, mutableInt);
				if (copy != null) {
					children.add(copy);
				}
			}
		}

		if (!children.isEmpty()) {
			menuNode.children.addAll(children);
			return menuNode;
		}

		if (menuNode.view != null) {
			return menuNode;
		}

		return null;
	}

	private static boolean hasRole(MenuNode node,
			Collection<? extends GrantedAuthority> authorities) {
		if (node.role == null) {
			return true;
		}

		for (GrantedAuthority grantedAuthority : authorities) {
			if (node.role.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	public int getId() {
		return this.id;
	}

	public String getText() {
		return this.text;
	}

	public String getView() {
		return this.view;
	}

	public boolean isLeaf() {
		return this.leaf;
	}

	public boolean isExpanded() {
		return this.expanded;
	}

	public String getIcon() {
		return this.icon;
	}

	public List<MenuNode> getChildren() {
		return this.children;
	}

	public void addChild(MenuNode menuNode) {
		this.children.add(menuNode);

	}

}