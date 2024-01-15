package ch.ralscha.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/myController")
public class TestController {

	@RequestMapping("/doSomething")
	@ResponseBody
	public User doSomething() {

		User user = new User();
		user.setName("aName");
		Calendar cal = new GregorianCalendar(1980, Calendar.MARCH, 22);
		user.setBirthDay(cal);

		return user;

	}
}
