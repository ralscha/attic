package ch.rasc.webapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import ch.rasc.webapp.controller.TestController;

public class TestControllerTest {

	@Test
	public void test() {
		TestController testController = new TestController();

		User user = new User("admin", "admin",
				AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
		Map<String, Object> result = testController.doSomething(user);

		assertTrue(result.get("today") instanceof Date);
		assertEquals("admin", result.get("me"));

	}
}
