package ch.rasc.eds.starter.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class JsonSavedRequestAwareAuthenticationSuccessHandler
		extends SimpleUrlAuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
					throws ServletException, IOException {

		SavedRequest savedRequest = this.requestCache.getRequest(request, response);

		if (savedRequest == null) {
			String targetUrl = determineTargetUrl(request, response);
			response.getWriter()
					.print("{\"success\":true,\"target\":\"" + targetUrl + "\"}");
			response.getWriter().flush();
			clearAuthenticationAttributes(request);

			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() || targetUrlParameter != null
				&& StringUtils.hasText(request.getParameter(targetUrlParameter))) {
			this.requestCache.removeRequest(request, response);
			String targetUrl = determineTargetUrl(request, response);
			response.getWriter()
					.print("{\"success\":true,\"target\":\"" + targetUrl + "\"}");
			response.getWriter().flush();
			clearAuthenticationAttributes(request);
			return;
		}

		clearAuthenticationAttributes(request);

		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		response.getWriter().print("{\"success\":true,\"target\":\"" + targetUrl + "\"}");
		response.getWriter().flush();
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
}