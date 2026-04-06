package ch.rasc.playground.jmx.web;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping("/somedata")
	@ResponseBody
	public String getSomeData() throws IntrospectionException, InstanceNotFoundException,
			ReflectionException, AttributeNotFoundException, MalformedObjectNameException,
			MBeanException, NullPointerException {

		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectInstance> instances = mbeanServer.queryMBeans(null, null);
		for (ObjectInstance oi : instances) {
			MBeanInfo info = mbeanServer.getMBeanInfo(oi.getObjectName());
			System.out.println(info);
		}

		String n = (String) mbeanServer
				.getAttribute(ObjectName.getInstance("bean:name=testBean"), "Name");
		System.out.println("TestBean");
		System.out.println(n);

		List<MemoryPoolMXBean> memPoolBeans = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean mpb : memPoolBeans) {
			System.out.println("Memory Pool: " + mpb.getObjectName());
			System.out.println("Memory Pool Used: " + mpb.getPeakUsage().getUsed());
		}

		return "hi";
	}
}
