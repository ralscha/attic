package ch.rasc.nosql.redis;

import redis.clients.jedis.JedisPubSub;

public class MyPubSub extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {
		System.out.println("Received: " + message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// nothing here
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// nothing here
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// nothing here
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// nothing here
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// nothing here
	}

}
