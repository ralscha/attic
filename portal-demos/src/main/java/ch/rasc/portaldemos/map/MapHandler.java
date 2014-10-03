package ch.rasc.portaldemos.map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.Destroy;
import com.github.flowersinthesand.portal.Init;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Wire;
import com.google.common.collect.ImmutableList;

@Bean
public class MapHandler {

	private ScheduledExecutorService threadPool;

	@Wire
	Room hall;

	@Init
	public void init() {
		threadPool = Executors.newScheduledThreadPool(2);
		threadPool.scheduleWithFixedDelay(new Car("driveBlue", Route.routeBlue), 1, 1,
				TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(new Car("driveRed", Route.routeRed), 2000,
				1200, TimeUnit.MILLISECONDS);
	}

	@Destroy
	public void destroy() {
		threadPool.shutdownNow();
	}

	private class Car implements Runnable {

		private int index = 0;

		private final String event;

		private final ImmutableList<LatLng> route;

		public Car(String event, ImmutableList<LatLng> route) {
			this.event = event;
			this.route = route;
		}

		@Override
		public void run() {
			if (hall.size() > 0) {
				hall.send(event, route.get(index));
				index++;
				if (index >= route.size()) {
					index = 0;
				}
			}
		}
	}
}
