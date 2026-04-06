package ch.rasc.session;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoService {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> get(HttpSession session) {
		session.setAttribute("user", 1);
		return Collections.singletonMap("user", 1);
	}

	@RequestMapping(value = "/string", method = RequestMethod.GET)
	public String aString(HttpSession session) {
		session.setAttribute("user", 1);
		return "aString";
	}

	@RequestMapping(value = "/big", method = RequestMethod.GET)
	public Map<String, Object> big(HttpServletResponse response, HttpSession session) {
		System.out.println("Response Buffer Size: " + response.getBufferSize());
		session.setAttribute("user", 1);
		return Collections.singletonMap("large",
				new String(new char[8192 - 12]).replace('\0', '='));
	}

	@RequestMapping(value = "/bigger", method = RequestMethod.GET)
	public Map<String, Object> bigger(HttpSession session) {
		session.setAttribute("user", 1);
		// response.setBufferSize(9000);
		return Collections.singletonMap("large",
				new String(new char[8192 - 12 + 1]).replace('\0', '='));
	}
}
