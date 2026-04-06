package ch.rasc.pubsub.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class SimpleSender {

	public static void main(String[] args) {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("localhost");

		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		client.getTopic("my_topic")
				.publish(new ShortMessageEvent("message from a simple hazelcast client"));

	}

}
