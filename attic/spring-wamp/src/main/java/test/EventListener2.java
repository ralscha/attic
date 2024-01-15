package test;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener2 implements ApplicationListener<AppEvent> {

	@Override
	public void onApplicationEvent(AppEvent event) {
		System.out.println(Thread.currentThread());
		System.out.println("2nd: " + new Date() + ": " + event.getMessage());
	}

}
