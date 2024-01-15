package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.ab.security.JpaUserDetails;

@Service
public class NavigationService {

	@Autowired
	private MessageSource messageSource;

	private final MenuNode root;

	public NavigationService() {
		this.root = new MenuNode("root");

		MenuNode contactsNode = new MenuNode("Contacts", "resources/images/people.png",
				"Addressbook.view.ContactsList", "ROLE_USER");
		this.root.addChild(contactsNode);

		MenuNode administrationNode = new MenuNode("Administration",
				"resources/images/customizing.png", true);
		administrationNode.addChild(new MenuNode("Users", "resources/images/users.png",
				"Addressbook.view.AppUsersList", "ROLE_ADMIN"));
		administrationNode.addChild(new MenuNode("Contact Groups",
				"resources/images/group.png", "ContactGroups", "ROLE_ADMIN"));
		this.root.addChild(administrationNode);

	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return MenuNode.copyOf(this.root, authentication.getAuthorities(),
				new MutableInt(0));
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public String getLoggedOnUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			JpaUserDetails userDetail = (JpaUserDetails) principal;
			return userDetail.getFullName();

		}
		return principal.toString();
	}

}
