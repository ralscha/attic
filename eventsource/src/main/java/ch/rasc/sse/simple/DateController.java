package ch.rasc.sse.simple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.rasc.sse.config.SSEvent;

@Controller
public class DateController {

	private final static AtomicInteger counter = new AtomicInteger(1);

	@RequestMapping(value = "/date", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public SSEvent getTime(@RequestHeader(value = "Last-Event-ID",
			required = false) String lastEventId) {
		System.out.println("Last Event Id: " + lastEventId);

		SSEvent event = new SSEvent();
		event.setData(LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS")));
		event.setId(String.valueOf(counter.incrementAndGet()));
		event.setRetry((ThreadLocalRandom.current().nextInt(10) + 3) * 1000);
		return event;
	}

	@RequestMapping(value = "/datedata", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String getTime() {
		return LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS"));
	}

	// @RequestMapping(value = "/dateString", method = RequestMethod.GET,
	// produces = "text/event-stream")
	// @ResponseBody
	// public String getTimeString(@RequestHeader(value = "Last-Event-ID",
	// required = false) String lastEventId, HttpServletResponse response)
	// throws IOException {
	// response.setContentType("text/event-stream");
	// response.setCharacterEncoding(StandardCharsets.UTF_8.name());
	// response.setStatus(200);
	// PrintWriter out = response.getWriter();
	// out.write("data:test1\n\n");
	// out.flush();
	//
	//
	// System.out.println("Last Event Id: " + lastEventId);
	//
	// StringBuilder sb = new StringBuilder();
	// sb.append("data:");
	// sb.append(DateTime.now().toString("dd.MM.yyyy HH:mm:ss.SSS"));
	// sb.append("\n");
	//
	// sb.append("id:");
	// sb.append(counter.incrementAndGet());
	// sb.append("\n");
	//
	// sb.append("retry:");
	// sb.append((ThreadLocalRandom.current().nextInt(10) + 3) * 1000);
	// sb.append("\n");
	//
	// System.out.println(sb);
	//
	// return sb.toString();
	// }

}
