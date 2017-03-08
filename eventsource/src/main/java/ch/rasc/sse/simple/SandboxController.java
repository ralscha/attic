package ch.rasc.sse.simple;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SandboxController {

	@RequestMapping(value = "/simple", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String simple() {

		StringBuilder sb = new StringBuilder();
		sb.append("data:");
		sb.append("the data");
		sb.append("\n");
		sb.append("\n");

		return sb.toString();
	}

	@RequestMapping(value = "/multiline", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String multiline() {

		StringBuilder sb = new StringBuilder();
		sb.append("data:");
		sb.append("line1");
		sb.append("\n");

		sb.append("data:");
		sb.append("line2");
		sb.append("\n");

		sb.append("data:");
		sb.append("line3");
		sb.append("\n");

		sb.append("\n");

		return sb.toString();
	}

	@RequestMapping(value = "/event", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String event() {

		StringBuilder sb = new StringBuilder();
		sb.append("event:");
		sb.append("event1");
		sb.append("\n");
		sb.append("data:");
		sb.append("data of event1");
		sb.append("\n");
		sb.append("\n");

		sb.append("event:");
		sb.append("event2");
		sb.append("\n");
		sb.append("data:");
		sb.append("data of event2");
		sb.append("\n");
		sb.append("\n");

		sb.append("event:");
		sb.append("close");
		sb.append("\n");
		sb.append("data:");
		sb.append("the close event");
		sb.append("\n");
		sb.append("\n");

		return sb.toString();
	}

	private final static AtomicInteger counter = new AtomicInteger(1);

	@RequestMapping(value = "/lastId", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String lastId(@RequestHeader(value = "Last-Event-ID",
			required = false) String lastEventId) {
		System.out.println("Last Event Id: " + lastEventId);

		StringBuilder sb = new StringBuilder();
		sb.append("id:");
		sb.append(counter.incrementAndGet());
		sb.append("\n");
		sb.append("data:");
		sb.append("line1");
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}
}
