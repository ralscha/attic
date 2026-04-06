package ch.rasc.caching.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	private final TestService testService;

	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}

	@RequestMapping(value = "/getSomething", produces = "application/json")
	@ResponseBody
	@Cacheable("tenMinutesCache")
	public List<String> getSomething() {
		System.out.println("inside getSomething");
		return Arrays.asList(this.testService.getData("one"),
				this.testService.getData("two"), this.testService.getData("three"),
				this.testService.getData("four"), this.testService.getData("five"),
				this.testService.getData("six"), this.testService.getData("seven"),
				this.testService.getData("eight"), this.testService.getData("nine"),
				this.testService.getData("ten"), this.testService.getData("eleven"));
	}

	@RequestMapping(value = "/cacheEvict", produces = "application/json")
	@ResponseBody
	@CacheEvict(value = "tenMinutesCache", allEntries = true)
	public String cacheEvict() {
		return "CACHE EVICT";
	}
}
