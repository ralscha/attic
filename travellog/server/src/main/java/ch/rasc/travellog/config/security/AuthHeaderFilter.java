package ch.rasc.travellog.config.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class AuthHeaderFilter extends GenericFilterBean {

	public final static String HEADER_NAME = "x-authentication";

	private final SessionCacheService sessionCacheService;

	public AuthHeaderFilter(SessionCacheService sessionCacheService) {
		this.sessionCacheService = sessionCacheService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String sessionId = httpServletRequest.getHeader(AuthHeaderFilter.HEADER_NAME);

		if (sessionId != null) {
			AppUserAuthentication authentication = this.sessionCacheService
					.getUserAuthentication(sessionId);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

}