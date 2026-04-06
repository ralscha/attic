package ch.rasc.maptrack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TrackController {

	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public void getTime(HttpServletResponse response)
			throws IOException, InterruptedException {

		response.setContentType("text/event-stream");

		@SuppressWarnings("resource")
		ServletOutputStream os = response.getOutputStream();

		for (int i = 0; i < 10; i++) {
			if (i == 9) {
				os.write("retry: 1000\n".getBytes());
			}
			os.write(("data: "
					+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
					+ "\n\n").getBytes());
			os.flush();
			TimeUnit.SECONDS.sleep(1);
		}

	}

}
