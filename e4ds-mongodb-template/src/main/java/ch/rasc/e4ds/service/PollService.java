package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.POLL;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
@Lazy
public class PollService {

	private final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

	@ExtDirectMethod(value = POLL, event = "chartdata")
	@PreAuthorize("isAuthenticated()")
	public Poll getPollData() throws MalformedObjectNameException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {

		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName osName = new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
		double processCpuLoad = (Double) mbeanServer.getAttribute(osName, "ProcessCpuLoad");
		double systemCpuLoad = (Double) mbeanServer.getAttribute(osName, "SystemCpuLoad");
		long freePhysicalMemorySize = (Long) mbeanServer.getAttribute(osName, "FreePhysicalMemorySize");
		long totalPhysicalMemorySize = (Long) mbeanServer.getAttribute(osName, "TotalPhysicalMemorySize");

		ObjectName memoryName = new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
		CompositeDataSupport heapMemory = (CompositeDataSupport) mbeanServer
				.getAttribute(memoryName, "HeapMemoryUsage");

		long usedHeapMemory = (Long) heapMemory.get("used");
		long committedHeapMemory = (Long) heapMemory.get("committed");
		long maxHeapMemory = (Long) heapMemory.get("max");

		if (processCpuLoad < 0) {
			processCpuLoad = 0;
		}

		if (systemCpuLoad < 0) {
			systemCpuLoad = 0;
		}

		long now = DateTime.now().getMillis();
		Poll p = new Poll(now, fmt.print(now));

		p.setProcessCpuLoad((int) (processCpuLoad * 100.0));
		p.setSystemCpuLoad((int) (systemCpuLoad * 100.0));
		p.setFreePhysicalMemorySize(freePhysicalMemorySize);
		p.setTotalPhysicalMemorySize(totalPhysicalMemorySize);
		p.setUsedHeapMemory(usedHeapMemory);
		p.setCommittedHeapMemory(committedHeapMemory);
		p.setMaxHeapMemory(maxHeapMemory);

		return p;
	}
}
