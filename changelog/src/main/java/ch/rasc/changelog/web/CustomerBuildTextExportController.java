package ch.rasc.changelog.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.rasc.changelog.entity.Change;
import ch.rasc.changelog.entity.ChangeType;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.service.LogService;

@Controller
public class CustomerBuildTextExportController {

	@Autowired
	private LogService logService;

	@Autowired
	private MessageSource messageSource;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping("/changeTextExport.txt")
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public void export(
			@RequestParam(value = "customerBuildId",
					required = false) final Long customerBuildId,
			final Locale locale,
			@RequestParam(value = "customerId", required = false) final Long customerId,
			final HttpServletResponse response) throws IOException {

		List<Change> changes = new ArrayList<>();
		Customer customer;
		CustomerBuild build = null;

		if (customerBuildId != null) {
			build = this.entityManager.find(CustomerBuild.class, customerBuildId);
			changes = this.logService.loadChanges(customerBuildId, null);
			customer = build.getCustomer();
		}
		else {
			changes = this.logService.loadChanges(null, customerId);
			customer = this.entityManager.find(Customer.class, customerId);
		}

		response.setContentType("text/plain");
		response.setHeader("extension", "txt");

		try (OutputStream out = response.getOutputStream();
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out))) {

			pw.println(this.messageSource.getMessage("customer", null, locale) + "   : "
					+ customer.getLongName());
			pw.print(this.messageSource.getMessage("customer_build", null, locale)
					+ " : ");

			if (build != null) {
				pw.println(build.getVersionNumber());
			}
			else {
				pw.println("*");
			}

			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			pw.print(this.messageSource.getMessage("customer_build_date", null, locale)
					+ "   : ");
			if (build != null) {
				pw.println(df.format(build.getVersionDate()));
			}
			else {
				pw.println(df.format(new Date()));
			}

			pw.println();

			writeLog(changes, pw, ChangeType.FIX,
					this.messageSource.getMessage("change_typ_fix", null, locale));
			pw.println();
			writeLog(changes, pw, ChangeType.ENHANCEMENT, this.messageSource
					.getMessage("change_typ_enhancement", null, locale));
			pw.println();
			writeLog(changes, pw, ChangeType.NEW,
					this.messageSource.getMessage("change_typ_new", null, locale));

		}

	}

	private static void writeLog(List<Change> changes, PrintWriter pw, ChangeType typ,
			String title) {
		pw.println(title);
		for (Change change : changes) {
			if (change.getTyp() == typ) {

				if (StringUtils.isNotBlank(change.getModule())) {
					pw.print(StringUtils.rightPad(change.getModule(), 20));
				}
				else {
					pw.print("                    ");
				}

				if (StringUtils.isNotBlank(change.getBugNumber())) {
					pw.print(StringUtils.rightPad(change.getBugNumber(), 6));
				}
				else {
					pw.print("      ");
				}

				String description = StringUtils.replace(change.getDescription(), "\r",
						" ");
				description = StringUtils.replace(description, "\n", " ");
				pw.print(description);
				pw.println();
			}
		}
	}

}