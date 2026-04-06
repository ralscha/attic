package ch.rasc.pubsub.hazelcast;

import javax.annotation.PostConstruct;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class ShortMessageListener implements MessageListener<ShortMessageEvent> {

	@PostConstruct
	public void register() {
		Config config = new Config();
		config.setInstanceName("myHazelcastInstance");
		HazelcastInstance hi = Hazelcast.newHazelcastInstance(config);
		hi.<ShortMessageEvent>getTopic("my_topic").addMessageListener(this);
	}

	@Override
	public void onMessage(Message<ShortMessageEvent> message) {
		System.out.println(message.getMessageObject().getMsg());
	}

}
