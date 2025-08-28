package ch.rasc.travellog.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitInterceptor implements HandlerInterceptor {

	private final Bucket bucket;

	private final int numTokens;

	public RateLimitInterceptor(Bucket bucket, int numTokens) {
		this.bucket = bucket;
		this.numTokens = numTokens;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		if (this.bucket.tryConsume(this.numTokens)) {
			return true;
		}

		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
		return false;

	}

}
