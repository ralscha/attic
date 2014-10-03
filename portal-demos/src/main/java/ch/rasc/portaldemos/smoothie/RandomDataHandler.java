package ch.rasc.portaldemos.smoothie;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.Destroy;
import com.github.flowersinthesand.portal.Init;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Wire;

@Bean
public class RandomDataHandler {

	private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);

	@Wire
	Room hall;

	@Init
	public void init() {
		threadPool = Executors.newScheduledThreadPool(1);
		threadPool.scheduleWithFixedDelay(new RandomDataGenerator(), 1, 1,
				TimeUnit.SECONDS);
	}

	@Destroy
	public void destroy() {
		threadPool.shutdownNow();
	}

	private class RandomDataGenerator implements Runnable {
		private final Random random = new Random();

		@Override
		public void run() {
			if (hall.size() > 0) {
				CpuData cpuData = new CpuData();
				cpuData.setHost1(new double[] { random.nextDouble(), random.nextDouble(),
						random.nextDouble(), random.nextDouble() });
				cpuData.setHost2(new double[] { random.nextDouble(), random.nextDouble(),
						random.nextDouble(), random.nextDouble() });
				cpuData.setHost3(new double[] { random.nextDouble(), random.nextDouble(),
						random.nextDouble(), random.nextDouble() });
				cpuData.setHost4(new double[] { random.nextDouble(), random.nextDouble(),
						random.nextDouble(), random.nextDouble() });
				hall.send("cpu", cpuData);
			}
		}
	}
}
