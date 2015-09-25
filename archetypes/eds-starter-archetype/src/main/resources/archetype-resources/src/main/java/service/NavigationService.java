#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ${package}.dto.MenuNode;
import ${package}.entity.Role;

@Service
public class NavigationService {

	private final MessageSource messageSource;

	private final MenuNode root;

	@Autowired
	public NavigationService(MessageSource messageSource) {
		this.messageSource = messageSource;

		root = new MenuNode("root");

		MenuNode businessNode = new MenuNode("navigation_business", null, true);
		businessNode
				.addChild(new MenuNode(
						"chart_title",
						"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAA50lEQVR4Ab2Qr0vHUBTFxxjDP8AgpgWDGEQMBpNBFgxLYjSZxSSysCYiBoN5UeRz3wTBIMMoQ5aMYhgLhmEwyoKM5+UxvyD2ncE4755zf3oTogjZ4Yxr/c5JzNxfMeCIDktDyQNvyj45MeEoyyLPfHNllmSNnBuJTcQFPS8SOZmGRtaVrVBxwAcDib6WeeVdLVR0suApNCvT/z2W0lWep6X2OGRgfzT0VNiZIeabVAmXDBzf+driy8muBXv0mFvfjUnKwJNs/Q4pq4AlL4LZomxSY+l4pKR1C+/+O5VskLlDnbKtpafDD+DijjGiKOJHAAAAAElFTkSuQmCC",
						"${jsAppNamespace}.view.poll.PollChart", Role.USER, Role.ADMIN));
		root.addChild(businessNode);

		MenuNode administrationNode = new MenuNode("navigation_administration", null,
				true);
		administrationNode
				.addChild(new MenuNode(
						"user_users",
						"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAArklEQVR4Ac2NMQ6CQBREf2HBAaioiScgnIJYcABiOAgXoaKcDx2FMdpScAQOYYyFsSAbnd3faKxNnCn2ZWb4yL8Je9zo6qvQDGUfcXDFk76I9BFKzcSEHI5xS3qEwZ3U8nXIbVCFeCYdAx1IcyD7mabhQkcqfKwFBx3JaSomTFg0GWI04btmiDXBgsnKGic47DD6O2bSyMSxqQWrj4atVea3ZJXzxlvsNX8k8nu9AB/XjIWC18JAAAAAAElFTkSuQmCC",
						"${jsAppNamespace}.view.user.Container", Role.ADMIN));
		root.addChild(administrationNode);

		MenuNode systemNode = new MenuNode("navigation_system", null, true);
		systemNode
				.addChild(new MenuNode(
						"accesslog",
						"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA00lEQVQ4y2NYuXJlMxD/JRG/ZoABqAHPgTiBBByBbsBNKDsGiDNIwGboBjwG4v8k4GZsBuxcvXq1GgFsgM+A1SD2qlWrIoDsPHQMFDdav349D0EDgPR0IN6LBYcRZQDQmVpA28zQMVBcglgXHAXi71hwEVEG4APEumA/EH/EgvPWrl3LBzWgEV8sGACxDTIG+t9jzZo1GkA1ZVADokjyAlBuPVIieg40lItUA45CNYMylB9M0ASUaJAMuA3EE7FgmO0/gTgJlw0P8WThj1BDDJD1AAB30hUSZGmhygAAAABJRU5ErkJggg==",
						"${jsAppNamespace}.view.accesslog.TabPanel", Role.ADMIN));
		systemNode
				.addChild(new MenuNode(
						"logevents",
						"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA00lEQVQ4y2NYuXJlMxD/JRG/ZoABqAHPgTiBBByBbsBNKDsGiDNIwGboBjwG4v8k4GZsBuxcvXq1GgFsgM+A1SD2qlWrIoDsPHQMFDdav349D0EDgPR0IN6LBYcRZQDQmVpA28zQMVBcglgXHAXi71hwEVEG4APEumA/EH/EgvPWrl3LBzWgEV8sGACxDTIG+t9jzZo1GkA1ZVADokjyAlBuPVIieg40lItUA45CNYMylB9M0ASUaJAMuA3EE7FgmO0/gTgJlw0P8WThj1BDDJD1AAB30hUSZGmhygAAAABJRU5ErkJggg==",
						"${jsAppNamespace}.view.loggingevent.List", Role.ADMIN));
		systemNode
				.addChild(new MenuNode(
						"config",
						"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAABF0lEQVR4AXXPIUuDURTG8cchY2GILI0xxDCGisG0ZJBhMJnEYBQRm4JVBIMYFg0isrBg+J+7l0WDGIYMP8GCwWCQIbIsY84ruzB4X9Tf4cI9l+ccuJqwAjs2p780U1aQuMRzPQ5GKSVxxYAuPlSXAfXfAR+vZGC87iuc46lFHYb7t+LohKmGAm5C99Sc1gTD8FRTwEXoRrGAy9PG825lyUr08HRcQWqlWxkp4DRMDXlmEG7nUdq2ueXBttysJO7xiWq7eTx93vBUJVmePSvRDsvL7FqRBt6KLkufR02wirM1q9qRLXOAt02r8ElNcbbBCxFdt8SI8BvWFccJ9WaaD1twOfY5s5ySrMIrPe6ijP7jZtyKyyrmB9IlukeQunFqAAAAAElFTkSuQmCC",
						"${jsAppNamespace}.view.config.Edit", Role.ADMIN));
		root.addChild(systemNode);
	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation(Locale locale, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return MenuNode.copyOf(root, authentication.getAuthorities(),
				new AtomicInteger(0), locale, messageSource, request);
	}

}
