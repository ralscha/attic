package ch.rasc.golb.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ch.rasc.golb.Application;

@Component
public class AppErrorViewResolver implements ErrorViewResolver {
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request,
			org.springframework.http.HttpStatus status, Map<String, Object> model) {
		Application.logger.error("Unknwown path: {} / Status: {}", model.get("path"),
				model.get("status"));
		return null;
	}
}