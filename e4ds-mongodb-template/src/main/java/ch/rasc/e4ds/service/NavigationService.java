package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

@Service
public class NavigationService {

	@Autowired
	private MessageSource messageSource;

	private final MenuNode root;

	public NavigationService() throws JsonParseException, JsonMappingException, IOException {
		Resource menu = new ClassPathResource("/menu.json");
		ObjectMapper mapper = new ObjectMapper();
		root = mapper.readValue(menu.getInputStream(), MenuNode.class);
	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation(Locale locale) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		MenuNode copyOfRoot = new MenuNode(root, authentication.getAuthorities());
		upateIdAndLeaf(new MutableInt(0), copyOfRoot, locale);

		return copyOfRoot;
	}

	private void upateIdAndLeaf(MutableInt id, MenuNode parent, Locale locale) {
		parent.setId(id.intValue());
		parent.setText(messageSource.getMessage(parent.getText(), null, parent.getText(), locale));
		id.add(1);

		parent.setLeaf(parent.getChildren().isEmpty());

		Set<MenuNode> removeChildren = Sets.newHashSet();
		for (MenuNode child : parent.getChildren()) {
			// Remove child if it has no children and it's not a leaf
			if (child.getView() == null && child.getChildren().isEmpty()) {
				removeChildren.add(child);
			} else {
				upateIdAndLeaf(id, child, locale);
			}
		}

		parent.getChildren().removeAll(removeChildren);
	}

}
