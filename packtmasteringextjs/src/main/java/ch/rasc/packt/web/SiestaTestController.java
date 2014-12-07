package ch.rasc.packt.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.rasc.edsutil.optimizer.WebResourceProcessor;

@RestController
public class SiestaTestController {

	@Autowired
	private Environment environment;

	@RequestMapping(value = "/test.html", method = RequestMethod.GET)
	public String harness() {
		return "<!DOCTYPE html><html><head>"
				+ "<link rel=\"stylesheet\" href=\"http://cdn.sencha.io/ext-4.2.0-gpl/resources/css/ext-all.css\">"
				+ "<link rel=\"stylesheet\" href=\"resources/siesta/resources/css/siesta-all.css\">"
				+ "<script src=\"http://cdn.sencha.io/ext-4.2.0-gpl/ext-all.js\"></script>"
				+ "<script src=\"resources/siesta/siesta-all.js\"></script>"
				+ "<script src=\"test/harness.js\"></script></head><body></body></html>";
	}

	@RequestMapping(value = "/test/harness.js", method = RequestMethod.GET)
	public String harness(HttpServletResponse response, HttpSession session) {
		response.setContentType("application/javascript");
		response.setCharacterEncoding(ExtDirectSpringUtil.UTF8_CHARSET.name());

		StringBuilder sb = new StringBuilder();

		sb.append("var Harness = Siesta.Harness.Browser.ExtJS;").append("\n");

		sb.append("Harness.configure({").append("\n");
		sb.append("    title       : 'Packt Test Suite',").append("\n");

		sb.append("    preload     : [").append("\n");

		WebResourceProcessor processor = new WebResourceProcessor(
				session.getServletContext(), !environment.acceptsProfiles("development"));
		processor.setResourceServletPath("/resources/");
		processor.ignoreJsResourceFromReordering("/app/model/menu/Item.js");
		List<String> resources = processor.getJsAndCssResources();

		boolean first = true;
		for (String resource : resources) {
			if (!first) {
				sb.append(",\n");
			}
			else {
				first = false;
			}
			sb.append("        '").append(resource).append("'");
		}

		sb.append("\n    ]").append("\n");
		sb.append("});").append("\n");

		first = true;
		Set<String> testResourcesSet = session.getServletContext().getResourcePaths(
				"/test/");
		if (testResourcesSet != null) {

			List<String> testResourcesList = new ArrayList<>(testResourcesSet);
			Collections.sort(testResourcesList);

			sb.append("Harness.start(").append("\n");
			for (String testResource : testResourcesList) {
				if (!first) {
					sb.append(",\n");
				}
				else {
					first = false;
				}
				sb.append("    '").append(testResource.substring(1)).append("'");
			}
			sb.append(");").append("\n");
		}

		return sb.toString();
	}

}
