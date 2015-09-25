#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myController")
public class TestController {

	@RequestMapping("/doSomething")
	public Map<String, Object> doSomething(@AuthenticationPrincipal UserDetails user) {
		Map<String, Object> response = new HashMap<>();
		response.put("today", new Date());
		response.put("me", user.getUsername());
		return response;
	}
}
