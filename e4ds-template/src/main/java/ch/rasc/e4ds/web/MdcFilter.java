package ch.rasc.e4ds.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MdcFilter implements Filter {

	@Override
	public void init(FilterConfig config) {
		// no action
	}

	@Override
	public void destroy() {
		// no action
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null) {
			MDC.put("userName", authentication.getName());
		}

		MDC.put("ip", request.getRemoteAddr());
		MDC.put("userAgent", ((HttpServletRequest) request).getHeader("User-Agent"));

		try {
			chain.doFilter(request, response);
		}
		finally {
			MDC.remove("userName");
			MDC.remove("ip");
			MDC.remove("userAgent");
		}
	}
}