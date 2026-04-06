package ch.rasc.cors;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class TestService {

	@ExtDirectMethod
	public String setCookie(HttpServletResponse response) {
		String accessToken = UUID.randomUUID().toString();
		response.addCookie(new Cookie("access_token", accessToken));
		return accessToken;
	}

	@ExtDirectMethod
	public String readCookie(HttpServletRequest request,
			@CookieValue("access_token") String accessToken) {
		for (Cookie c : request.getCookies()) {
			System.out.println(c.getName() + "=" + c.getValue());
		}
		return "I got this cookie : access_token=" + accessToken;
	}

}
