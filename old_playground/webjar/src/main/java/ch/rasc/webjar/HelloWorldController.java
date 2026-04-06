package ch.rasc.webjar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

	@RequestMapping("/")
	public String welcome() {
		return "index";
	}

	@RequestMapping(value = "/sayHello", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(@RequestParam(value = "name", required = false,
			defaultValue = "Mister X") String name) {
		return "Hello " + name + ". My name is Server";
	}

}
