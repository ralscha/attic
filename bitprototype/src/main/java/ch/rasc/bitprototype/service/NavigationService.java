package ch.rasc.bitprototype.service;

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
import ch.rasc.bitprototype.entity.Role;

@Service
public class NavigationService {

	@Autowired
	private MessageSource messageSource;

	private final MenuNode root;

	public NavigationService() {
		this.root = new MenuNode("root");

		this.root.addChild(new MenuNode("Bedarf", null, "BitP.view.bedarf.List",
				Role.BEDARF, Role.EINKAUF));

		this.root.addChild(new MenuNode("Lieferant", null, "BitP.view.lieferant.List",
				Role.EINKAUF));

		this.root.addChild(
				new MenuNode("Anfrage", null, "BitP.view.anfrage.List", Role.LIEFERANT));

		this.root.addChild(
				new MenuNode("Offerte", null, "BitP.view.offerte.List", Role.LIEFERANT));

		MenuNode administrationNode = new MenuNode("navigation_administration", null,
				true);
		administrationNode.addChild(new MenuNode("user_users",
				"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAArklEQVR4Ac2NMQ6CQBREf2HBAaioiScgnIJYcABiOAgXoaKcDx2FMdpScAQOYYyFsSAbnd3faKxNnCn2ZWb4yL8Je9zo6qvQDGUfcXDFk76I9BFKzcSEHI5xS3qEwZ3U8nXIbVCFeCYdAx1IcyD7mabhQkcqfKwFBx3JaSomTFg0GWI04btmiDXBgsnKGic47DD6O2bSyMSxqQWrj4atVea3ZJXzxlvsNX8k8nu9AB/XjIWC18JAAAAAAElFTkSuQmCC",
				"BitP.view.user.List", Role.ADMIN));
		this.root.addChild(administrationNode);

		MenuNode systemNode = new MenuNode("navigation_system", null, true);
		systemNode.addChild(new MenuNode("accesslog",
				"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA00lEQVQ4y2NYuXJlMxD/JRG/ZoABqAHPgTiBBByBbsBNKDsGiDNIwGboBjwG4v8k4GZsBuxcvXq1GgFsgM+A1SD2qlWrIoDsPHQMFDdav349D0EDgPR0IN6LBYcRZQDQmVpA28zQMVBcglgXHAXi71hwEVEG4APEumA/EH/EgvPWrl3LBzWgEV8sGACxDTIG+t9jzZo1GkA1ZVADokjyAlBuPVIieg40lItUA45CNYMylB9M0ASUaJAMuA3EE7FgmO0/gTgJlw0P8WThj1BDDJD1AAB30hUSZGmhygAAAABJRU5ErkJggg==",
				"BitP.view.accesslog.List", Role.ADMIN));
		systemNode.addChild(new MenuNode("logevents",
				"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA00lEQVQ4y2NYuXJlMxD/JRG/ZoABqAHPgTiBBByBbsBNKDsGiDNIwGboBjwG4v8k4GZsBuxcvXq1GgFsgM+A1SD2qlWrIoDsPHQMFDdav349D0EDgPR0IN6LBYcRZQDQmVpA28zQMVBcglgXHAXi71hwEVEG4APEumA/EH/EgvPWrl3LBzWgEV8sGACxDTIG+t9jzZo1GkA1ZVADokjyAlBuPVIieg40lItUA45CNYMylB9M0ASUaJAMuA3EE7FgmO0/gTgJlw0P8WThj1BDDJD1AAB30hUSZGmhygAAAABJRU5ErkJggg==",
				"BitP.view.logevent.List", Role.ADMIN));
		systemNode.addChild(new MenuNode("config",
				"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAABF0lEQVR4AXXPIUuDURTG8cchY2GILI0xxDCGisG0ZJBhMJnEYBQRm4JVBIMYFg0isrBg+J+7l0WDGIYMP8GCwWCQIbIsY84ruzB4X9Tf4cI9l+ccuJqwAjs2p780U1aQuMRzPQ5GKSVxxYAuPlSXAfXfAR+vZGC87iuc46lFHYb7t+LohKmGAm5C99Sc1gTD8FRTwEXoRrGAy9PG825lyUr08HRcQWqlWxkp4DRMDXlmEG7nUdq2ueXBttysJO7xiWq7eTx93vBUJVmePSvRDsvL7FqRBt6KLkufR02wirM1q9qRLXOAt02r8ElNcbbBCxFdt8SI8BvWFccJ9WaaD1twOfY5s5ySrMIrPe6ijP7jZtyKyyrmB9IlukeQunFqAAAAAElFTkSuQmCC",
				"BitP.view.config.Edit", Role.ADMIN));
		this.root.addChild(systemNode);
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
