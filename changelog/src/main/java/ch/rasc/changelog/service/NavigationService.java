package ch.rasc.changelog.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class NavigationService {

	@Autowired
	private MessageSource messageSource;

	private final MenuNode root;

	public NavigationService() {
		this.root = new MenuNode("root");

		MenuNode changelogNode = new MenuNode("navigation_changelog",
				"/resources/images/scroll_small.png", true);
		MenuNode toolsNode = new MenuNode("navigation_tools",
				"/resources/images/gear.png", true);
		MenuNode reportsNode = new MenuNode("navigation_reports",
				"/resources/images/reports.png", false);
		MenuNode administrationNode = new MenuNode("navigation_administration",
				"/resources/images/administration.png", false);
		MenuNode systemNode = new MenuNode("navigation_system",
				"/resources/images/application_server.png", false);

		this.root.addChild(changelogNode);
		this.root.addChild(toolsNode);
		this.root.addChild(reportsNode);
		this.root.addChild(administrationNode);
		this.root.addChild(systemNode);

		changelogNode.addChild(new MenuNode("log", "/resources/images/log.png",
				"Changelog.view.log.View", Roles.ROLE_USER, Roles.ROLE_ADMIN));

		changelogNode
				.addChild(new MenuNode("change_changes", "/resources/images/changes.png",
						"Changelog.view.change.List", Roles.ROLE_USER, Roles.ROLE_ADMIN));

		changelogNode.addChild(new MenuNode("customer_customers",
				"/resources/images/customers.png", "Changelog.view.customer.List",
				Roles.ROLE_USER, Roles.ROLE_ADMIN));

		toolsNode.addChild(new MenuNode("tool_packagebuilder",
				"resources/images/package.png", "Changelog.view.tool.PackageBuilder",
				Roles.ROLE_USER, Roles.ROLE_ADMIN));
		toolsNode.addChild(new MenuNode("tool_features",
				"resources/images/component_green.png", "Changelog.view.tool.Features",
				Roles.ROLE_USER, Roles.ROLE_ADMIN));

		reportsNode.addChild(new MenuNode("report_type", "resources/images/chart_pie.png",
				"Changelog.view.report.TypeChart", Roles.ROLE_USER, Roles.ROLE_ADMIN));
		reportsNode.addChild(
				new MenuNode("report_typePerYear", "resources/images/chart_column.png",
						"Changelog.view.report.TypePerYearChart", Roles.ROLE_USER,
						Roles.ROLE_ADMIN));

		administrationNode
				.addChild(new MenuNode("user_users", "resources/images/users.png",
						"Changelog.view.user.List", Roles.ROLE_ADMIN));

		systemNode.addChild(new MenuNode("accesslog", "resources/images/data_scroll.png",
				"Changelog.view.accesslog.List", Roles.ROLE_ADMIN));
		systemNode.addChild(new MenuNode("logevents", "resources/images/data_scroll.png",
				"Changelog.view.loggingevent.List", Roles.ROLE_ADMIN));
		systemNode.addChild(new MenuNode("config", "resources/images/data_scroll.png",
				"Changelog.view.configuration.Edit", Roles.ROLE_ADMIN));
	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation(Locale locale, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return MenuNode.copyOf(this.root, authentication.getAuthorities(),
				new MutableInt(0), locale, this.messageSource, request);
	}

}
