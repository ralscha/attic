package ch.ralscha.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.ralscha.model.User;
import ch.ralscha.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserService userService;

	@RequestMapping(value = "/findUsersWithString", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findUsersWithString(@RequestParam String birthDay) throws ParseException {
		return userService.findUsers(birthDay);
	}

	@RequestMapping(value = "/findUsersWithLocalDate", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findUsersWithLocalDate(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate birthDay) {
		return userService.findUsers(birthDay);
	}

	@RequestMapping(value = "/findUsersWithDate", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findUsersWithDate(@RequestParam @DateTimeFormat(iso = ISO.DATE) Date birthDay) {
		return userService.findUsers(birthDay);
	}

	@RequestMapping(value = "/findUsersWithCalendar", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findUsersWithCalendar(@RequestParam @DateTimeFormat(iso = ISO.DATE) Calendar birthDay) {
		return userService.findUsers(birthDay);
	}
}
