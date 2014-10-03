package ch.rasc.portaldemos.twitter;

import java.util.Queue;

import ch.rasc.s4ws.twitter.Tweet;

import com.github.flowersinthesand.portal.Room;
import com.google.common.collect.ImmutableList;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class TweetMessageListener implements MessageListener<Tweet> {

	private final Room hall;

	public TweetMessageListener(Room hall) {
		this.hall = hall;
	}

	@Override
	public void onMessage(Message<Tweet> message) {
		Queue<Tweet> lastTweets = (Queue<Tweet>) hall
				.get(TwitterHandler.LAST_RECEIVED_TWEETS_KEY);

		Tweet tweet = message.getMessageObject();
		lastTweets.offer(tweet);
		hall.send("newTweets", ImmutableList.of(tweet));
	}

}
