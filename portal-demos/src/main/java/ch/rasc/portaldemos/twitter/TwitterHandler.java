package ch.rasc.portaldemos.twitter;

import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ch.rasc.s4ws.twitter.Tweet;

import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.On;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Socket;
import com.github.flowersinthesand.portal.Wire;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MinMaxPriorityQueue;
import com.hazelcast.core.ITopic;

@Bean
@Component
public class TwitterHandler {

	final static String LAST_RECEIVED_TWEETS_KEY = "LAST_RECEIVED_TWEETS";

	@Wire
	Room hall;

	@Autowired
	private Environment environment;

	@Autowired
	private ITopic<Tweet> hazelcastTopic;

	@On
	public void open(Socket socket) {
		Queue<Tweet> lastTweets = (Queue<Tweet>) hall.get(LAST_RECEIVED_TWEETS_KEY);
		if (lastTweets != null) {
			socket.send("newTweets", ImmutableList.copyOf(lastTweets));
		}
	}

	public void init() {
		MinMaxPriorityQueue<Tweet> lastTweets = MinMaxPriorityQueue.maximumSize(10)
				.create();
		hall.set(LAST_RECEIVED_TWEETS_KEY, lastTweets);

		hazelcastTopic.addMessageListener(new TweetMessageListener(hall));
	}

}
