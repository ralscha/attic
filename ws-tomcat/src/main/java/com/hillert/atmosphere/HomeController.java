package com.hillert.atmosphere;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillert.atmosphere.model.TwitterMessage;

@Controller
public class HomeController {

	static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/websockets", method = RequestMethod.GET)
	@ResponseBody
	public void websockets(Meteor m) {

		// for (Broadcaster br : BroadcasterFactory.getDefault().lookupAll()) {
		// System.out.println(br);
		// }

		final Broadcaster b = BroadcasterFactory.getDefault().lookup("/*");
		m.setBroadcaster(b);
		m.suspend(-1);

		// final HttpServletRequest req = event.getRequest();
		// final HttpServletResponse res = event.getResponse();

		final ObjectMapper mapper = new ObjectMapper();

		// event.suspend();
		//
		// final Broadcaster bc = event.getBroadcaster();
		//

		b.scheduleFixedBroadcast(new Callable<String>() {

			private long sinceId = 0;

			@Override
			public String call() throws Exception {
				final TwitterTemplate twitterTemplate = new TwitterTemplate("key");
				final SearchResults results = twitterTemplate.searchOperations().search(
						"world", 20);

				logger.info("sinceId: " + sinceId + "; maxId: "
						+ results.getSearchMetadata().getMaxId());

				sinceId = results.getSearchMetadata().getMaxId();

				List<TwitterMessage> twitterMessages = new ArrayList<>();

				for (Tweet tweet : results.getTweets()) {
					twitterMessages.add(new TwitterMessage(tweet.getId(), tweet
							.getCreatedAt(), tweet.getText(), tweet.getFromUser(), tweet
							.getProfileImageUrl()));
				}

				String s = mapper.writeValueAsString(twitterMessages);
				return s;
			}

		}, 10, TimeUnit.SECONDS);

		// bc.delayBroadcast("Underlying Response now suspended");

	}

}
