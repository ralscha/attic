package ch.rasc.pubsub.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

public class ShortMessageSender {

	private final HazelcastInstance hi;

	public ShortMessageSender() {
		Config config = new Config();
		config.setInstanceName("myHazelcastInstance");
		this.hi = Hazelcast.newHazelcastInstance(config);
	}

	public void send(String msg) {

		ITopic<ShortMessageEvent> topic = this.hi.getTopic("my_topic");
		topic.publish(new ShortMessageEvent(msg));
	}

}
