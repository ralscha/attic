package ch.rasc.sse;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Service
public class TestJob {

	public final EventBus eventBus;

	@Autowired
	public TestJob(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Scheduled(fixedRate = 1000)
	public void doSomething() {
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();

		this.eventBus.notify("test.topic", Event.wrap("{\"heap\":" + heap.getUsed()
				+ ", \"nonheap\":" + nonHeap.getUsed() + "}"));
	}

}
