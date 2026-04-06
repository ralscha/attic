package ch.rasc.cors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@CrossOrigin(allowedHeaders = { "x-requested-with", "Content-Type" }, maxAge = 3600,
			origins = "http://localhost:8080")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public List<User> getUsers(@RequestBody String postData) {

		System.out.println(postData);

		List<User> builder = new ArrayList<>();
		builder.add(new User(1, "admin"));
		builder.add(new User(2, "user1"));
		builder.add(new User(3, "user2"));
		return Collections.unmodifiableList(builder);
	}
}
