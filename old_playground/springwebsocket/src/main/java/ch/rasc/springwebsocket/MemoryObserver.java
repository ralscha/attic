package ch.rasc.springwebsocket;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import org.springframework.scheduling.annotation.Scheduled;

public class MemoryObserver {

	private final RegistryHandler registryHandler;

	public MemoryObserver(RegistryHandler registryHandler) {
		this.registryHandler = registryHandler;
	}

	@Scheduled(fixedRate = 10000)
	public void observe() {

		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();

		this.registryHandler.sendToAll("{\"heap\":" + heap.getUsed() + ", \"nonheap\":"
				+ nonHeap.getUsed() + "}");
	}

}
