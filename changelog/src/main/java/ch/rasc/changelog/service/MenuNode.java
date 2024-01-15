package ch.rasc.changelog.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuNode {

	private int id;

	private final String text;

	private String view;

	private boolean leaf;

	private boolean expanded;

	private String icon;

	@JsonIgnore
	private EnumSet<Roles> roles;

	private final List<MenuNode> children = new ArrayList<>();

	public MenuNode(String text) {
		this.text = text;
	}

	public MenuNode(String text, String icon, boolean expanded) {
		this.text = text;
		this.icon = icon;
		this.expanded = expanded;
	}

	public MenuNode(String text, String icon, String view, Roles... roles) {
		this.text = text;
		this.icon = icon;
		this.view = view;
		if (roles != null) {
			this.roles = EnumSet.copyOf(Arrays.asList(roles));
		}
		else {
			this.roles = EnumSet.noneOf(Roles.class);
		}
	}

	public static MenuNode copyOf(MenuNode source,
			Collection<? extends GrantedAuthority> authorities, MutableInt mutableInt,
			Locale locale, MessageSource messageSource, HttpServletRequest request) {
		MenuNode menuNode = new MenuNode(messageSource.getMessage(source.getText(), null,
				source.getText(), locale));
		menuNode.id = mutableInt.intValue();
		mutableInt.add(1);
		menuNode.view = source.getView();
		menuNode.expanded = source.isExpanded();

		if (source.getIcon() != null) {
			if (source.getIcon().startsWith("/")) {
				menuNode.icon = request.getContextPath() + source.getIcon();
			}
			else {
				menuNode.icon = request.getContextPath() + "/" + source.getIcon();
			}
		}

		List<MenuNode> children = new ArrayList<>();
		for (MenuNode sourceChild : source.getChildren()) {
			if (hasRole(sourceChild, authorities)) {
				MenuNode copy = MenuNode.copyOf(sourceChild, authorities, mutableInt,
						locale, messageSource, request);
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
		if (node.roles == null || node.roles.isEmpty()) {
			return true;
		}

		for (GrantedAuthority grantedAuthority : authorities) {
			if (node.roles.contains(Roles.valueOf(grantedAuthority.getAuthority()))) {
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