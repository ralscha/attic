package ch.rasc.javaplayground.hazelcast;

import java.util.concurrent.BlockingQueue;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class QueueTake {

	public static void main(String[] args) throws InterruptedException {

		Config cfg = new Config();
		cfg.getNetworkConfig().setPort(5901);
		cfg.getNetworkConfig().setPortAutoIncrement(false);
		HazelcastInstance hc = Hazelcast.newHazelcastInstance(cfg);

		BlockingQueue<Integer> q = hc.getQueue("numbers");
		Integer t;
		while ((t = q.take()) != null) {
			System.out.println(t);
		}
	}

}
