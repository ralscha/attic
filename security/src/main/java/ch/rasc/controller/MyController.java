package ch.rasc.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

	@Autowired
	private SubService subService;

	@Autowired
	private SubPreService subPreService;

	@RequestMapping(value = "/callSubMethod", method = RequestMethod.GET)
	@ResponseBody
	public Date callSubMethod() {
		return subService.callSubMethod();
	}

	@RequestMapping(value = "/callSuperMethod", method = RequestMethod.GET)
	@ResponseBody
	public Date callSuperMethod() {
		return subService.callSuperMethod();
	}

	@RequestMapping(value = "/callSubPreMethod", method = RequestMethod.GET)
	@ResponseBody
	public Date callSubPreMethod() {
		return subPreService.callSubMethod();
	}

	@RequestMapping(value = "/callSuperPreMethod", method = RequestMethod.GET)
	@ResponseBody
	public Date callSuperPreMethod() {
		return subPreService.callSuperMethod();
	}

}
