package ch.rasc.javaplayground.hazelcast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class QueueOffer {

	public static void main(String[] args) throws InterruptedException {

		Config cfg = new Config();
		cfg.getNetworkConfig().setPort(5900);
		cfg.getNetworkConfig().setPortAutoIncrement(false);
		HazelcastInstance hc = Hazelcast.newHazelcastInstance(cfg);

		BlockingQueue<Integer> q = hc.getQueue("numbers");

		for (int i = 0; i < 10000; i++) {
			q.put(i);
			TimeUnit.SECONDS.sleep(5);
		}

	}

}
