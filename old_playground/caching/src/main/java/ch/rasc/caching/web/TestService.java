package ch.rasc.caching.web;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Cacheable("maxSizeCache")
	public String getData(String param) {
		System.out.println("inside getData() : " + param);
		return param.toUpperCase();
	}
}
