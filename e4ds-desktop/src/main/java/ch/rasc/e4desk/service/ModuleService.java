package ch.rasc.e4desk.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.e4desk.security.JpaUserDetails;
import ch.rasc.e4desk.util.Util;

@Service
public class ModuleService {

	@Autowired
	private MessageSource messageSource;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public ImmutableList<Module> read(Locale locale) {
		JpaUserDetails user = Util.getLoggedInUser();
		if (user != null) {
			ImmutableList.Builder<Module> builder = ImmutableList.builder();

			builder.add(new Module("E4desk.view.module.OnlineUsers", "Online Users",
					"onlineusers", true));
			builder.add(new Module("E4desk.view.module.Notepad", "Notepad", "notepad",
					false));
			builder.add(new Module("E4desk.view.module.TabWindow", "Tab Window", "tabs",
					false));
			builder.add(new Module("E4desk.view.module.GridWindow", "Grid Window", "grid",
					true));
			builder.add(new Module("E4desk.view.module.SystemStatus", "System Status",
					"systemstatus", true, "system"));

			if (Util.hasRole("ROLE_ADMIN")) {
				builder.add(
						new Submenu(this.messageSource.getMessage("system", null, locale),
								"settings", "system"));
				builder.add(new Module("E4desk.view.UsersWindow",
						this.messageSource.getMessage("user", null, locale), "users",
						true, "system"));
				builder.add(new Module("E4desk.view.LoggingEventsWindow",
						this.messageSource.getMessage("logevents", null, locale),
						"loggingevents", true, "system"));
				builder.add(new Module("E4desk.view.AccessLogWindow",
						this.messageSource.getMessage("accesslog", null, locale),
						"accesslog", true, "system"));
				builder.add(new Module("E4desk.view.ConfigurationWindow",
						this.messageSource.getMessage("configuration", null, locale),
						"configuration", false, "system"));
			}

			return builder.build();
		}

		return ImmutableList.of();

	}
}
