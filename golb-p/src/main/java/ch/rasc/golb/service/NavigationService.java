package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.golb.config.security.MongoUserDetails;
import ch.rasc.golb.dto.NavigationNode;
import ch.rasc.golb.entity.Authority;

@Service
public class NavigationService {

	private final MessageSource messageSource;

	private final List<NavigationNode> rootNodes = new ArrayList<>();

	public NavigationService(MessageSource messageSource) {
		this.messageSource = messageSource;

		this.rootNodes.add(new NavigationNode("Posts", "post.Container", true, null,
				"x-fa fa-file-text-o", "post", Authority.ADMIN));

		this.rootNodes.add(new NavigationNode("Feedback", "feedback.Container", true,
				null, "x-fa fa-commenting-o", "feedback", Authority.ADMIN));

		this.rootNodes.add(new NavigationNode("Binaries", "binary.Container", true, null,
				"x-fa fa-files-o", "binary", Authority.ADMIN));

		this.rootNodes.add(new NavigationNode("URL Check", "urlcheck.Container", true,
				null, "x-fa fa-stethoscope", "urlcheck", Authority.ADMIN));

		this.rootNodes.add(new NavigationNode("Tags", "tag.Container", true, null,
				"x-fa fa-tags", "tag", Authority.ADMIN));

		this.rootNodes.add(new NavigationNode("user_users", "user.Container", true, null,
				"x-fa fa-users", "users", Authority.ADMIN));

	}

	@ExtDirectMethod(TREE_LOAD)
	public List<NavigationNode> getNavigation(Locale locale,
			@AuthenticationPrincipal MongoUserDetails userDetails) {

		if (userDetails != null && !userDetails.isPreAuth()) {
			return this.rootNodes.stream()
					.map(n -> NavigationNode.copyOf(n, userDetails.getAuthorities(),
							locale, this.messageSource))
					.filter(Objects::nonNull).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

}
