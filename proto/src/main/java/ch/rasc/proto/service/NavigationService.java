package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.proto.config.security.AppUserDetails;
import ch.rasc.proto.dto.MenuNode;
import ch.rasc.proto.entity.Role;

@Service
public class NavigationService {

	private final MessageSource messageSource;

	private final MenuNode root;

	@Autowired
	public NavigationService(MessageSource messageSource) {
		this.messageSource = messageSource;

		this.root = new MenuNode("root");

		this.root.addChild(new MenuNode("Projects", "resources/images/desk.png",
				"Proto.view.project.Grid", Role.USER, Role.ADMIN));

		this.root.addChild(new MenuNode("Persons", "resources/images/users5.png",
				"Proto.view.person.Grid", Role.USER, Role.ADMIN));

		this.root.addChild(new MenuNode("Gantt", "resources/images/chart_gantt.png",
				"Proto.view.gantt.View", Role.USER, Role.ADMIN));

		MenuNode administrationNode = new MenuNode("navigation_administration", null,
				true);
		administrationNode.addChild(new MenuNode("navigation_administration_users",
				"resources/images/users.png", "Proto.view.user.Grid", Role.ADMIN));

		this.root.addChild(administrationNode);

	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public List<MenuNode> getNavigation(Locale locale, HttpServletRequest request,
			@AuthenticationPrincipal AppUserDetails jpaUserDetails) {

		MenuNode copy = MenuNode.copyOf(this.root, jpaUserDetails.getAuthorities(),
				new AtomicInteger(0), locale, this.messageSource, request);
		if (copy != null) {
			return copy.getChildren();
		}
		return null;
	}

}
