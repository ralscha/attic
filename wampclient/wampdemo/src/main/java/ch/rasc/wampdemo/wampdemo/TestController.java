package ch.rasc.wampdemo.wampdemo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.Instant;

import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

	@GetMapping("/method7")
	public String method7() {
		return "method7:" + Instant.now();
	}

}
