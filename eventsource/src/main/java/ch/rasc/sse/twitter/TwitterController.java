package ch.rasc.sse.twitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TwitterController {

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	TwitterReader twitterReader;

	AtomicInteger i = new AtomicInteger();

	@RequestMapping(value = "/twittersse", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public Object getTweets(
			@RequestHeader(value = "Last-Event-ID",
					required = false) final String lastEventId)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (this.i.incrementAndGet() % 2 == 0) {
			System.out.println("RUNNING IN CALLABLE");
			return new Callable<String>() {
				@Override
				public String call() throws Exception {
					return generateResult(lastEventId);
				}

			};
		}

		System.out.println("DIRECT");
		return generateResult(lastEventId);
	}

	String generateResult(final String lastEventId)
			throws IOException, JsonGenerationException, JsonMappingException {

		System.out.println("Last Event Id: " + lastEventId);
		long lastId = 0;
		if (StringUtils.hasText(lastEventId)) {
			lastId = Long.parseLong(lastEventId);
		}

		List<Tweet> tweets = this.twitterReader.getTweetsSinceId(lastId);
		for (Tweet tweet : tweets) {
			if (lastId < tweet.getId()) {
				lastId = tweet.getId();
			}
		}

		String resultJson = this.mapper.writeValueAsString(tweets);
		StringBuilder sb = new StringBuilder();

		sb.append("id:");
		sb.append(lastId);
		sb.append("\n");

		sb.append("data:");
		sb.append(resultJson);
		sb.append("\n");

		sb.append("retry:6000");
		sb.append("\n\n");

		// System.out.println(sb.toString());

		return sb.toString();
	}

}
