package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.dto.MenuNode;
import ch.rasc.eds.starter.entity.Role;

@Service
public class NavigationService {

	private final MessageSource messageSource;

	private final MenuNode root;

	@Autowired
	public NavigationService(MessageSource messageSource) {
		this.messageSource = messageSource;

		this.root = new MenuNode("root");

		MenuNode inventoryNode = new MenuNode("navigation_inventory", null, true);
		inventoryNode.addChild(new MenuNode("sop_trans",
				"resources/images/data_scroll.png", "Starter.view.sop.Container",
				Role.ADMIN,Role.USER));
		inventoryNode.addChild(new MenuNode("setup_inventory",
				"resources/images/data_scroll.png", "Starter.view.inventory.Container",
				Role.ADMIN,Role.USER));
		this.root.addChild(inventoryNode);

		MenuNode setupNode = new MenuNode("navigation_setup", null, true);
		setupNode.addChild(new MenuNode("setup_department",
				"resources/images/data_scroll.png","Starter.view.department.Container",Role.ADMIN,Role.USER));
		setupNode.addChild(new MenuNode("setup_section",
				"resources/images/data_scroll.png","Starter.view.section.Container",Role.ADMIN,Role.USER));
		setupNode.addChild(new MenuNode("setup_location",
				"resources/images/data_scroll.png","Starter.view.location.Container",Role.ADMIN,Role.USER));
		setupNode.addChild(new MenuNode("setup_uom",
				"resources/images/data_scroll.png","Starter.view.uom.Container",Role.ADMIN,Role.USER));
		setupNode.addChild(new MenuNode("setup_item",
				"resources/images/data_scroll.png","Starter.view.item.Container",Role.ADMIN,Role.USER));
		this.root.addChild(setupNode);

		MenuNode administrationNode = new MenuNode("navigation_administration", null,
				true);
		administrationNode.addChild(new MenuNode("navigation_administration_users",
				"resources/images/users.png", "Starter.view.user.Container", Role.ADMIN));
		this.root.addChild(administrationNode);

		MenuNode systemNode = new MenuNode("navigation_security", null, true);
		systemNode.addChild(new MenuNode("navigation_security_loggedin",
				"resources/images/users5.png", "Starter.view.loggedin.Grid", Role.ADMIN));
		systemNode.addChild(new MenuNode("navigation_security_accesslog",
				"resources/images/data_scroll.png", "Starter.view.accesslog.TabPanel",
				Role.ADMIN));
		systemNode.addChild(new MenuNode("navigation_security_logevents",
				"resources/images/data_scroll.png", "Starter.view.loggingevent.Grid",
				Role.ADMIN));
		systemNode.addChild(new MenuNode("navigation_security_config",
				"resources/images/gearwheels.png", "Starter.view.config.Form",
				Role.ADMIN));
		this.root.addChild(systemNode);


	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public List<MenuNode> getNavigation(Locale locale, HttpServletRequest request,
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {

		MenuNode copy = MenuNode.copyOf(this.root, jpaUserDetails.getAuthorities(),
				new AtomicInteger(0), locale, this.messageSource, request);
		if (copy != null) {
			return copy.getChildren();
		}
		return null;
	}

}
