package ch.rasc.pubsub.spring;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener implements ApplicationListener<AppEvent> {

	@Override
	public void onApplicationEvent(AppEvent event) {
		System.out.println(Thread.currentThread().getName() + ": 1st: " + new Date()
				+ ": " + event.getMessage());
		try {
			TimeUnit.SECONDS.sleep(5);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
