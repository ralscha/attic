package ch.rasc.e4ds.service;

public class Poll {
	private final long id;

	private final String time;

	private int processCpuLoad;

	private int systemCpuLoad;

	private long freePhysicalMemorySize;

	private long totalPhysicalMemorySize;

	private long usedHeapMemory;

	private long committedHeapMemory;

	private long maxHeapMemory;

	public Poll(long id, String time) {
		this.id = id;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public int getProcessCpuLoad() {
		return processCpuLoad;
	}

	public int getSystemCpuLoad() {
		return systemCpuLoad;
	}

	public long getId() {
		return id;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}

	public void setProcessCpuLoad(int processCpuLoad) {
		this.processCpuLoad = processCpuLoad;
	}

	public void setSystemCpuLoad(int systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}

	public long getUsedHeapMemory() {
		return usedHeapMemory;
	}

	public void setUsedHeapMemory(long usedHeapMemory) {
		this.usedHeapMemory = usedHeapMemory;
	}

	public long getCommittedHeapMemory() {
		return committedHeapMemory;
	}

	public void setCommittedHeapMemory(long committedHeapMemory) {
		this.committedHeapMemory = committedHeapMemory;
	}

	public long getMaxHeapMemory() {
		return maxHeapMemory;
	}

	public void setMaxHeapMemory(long maxHeapMemory) {
		this.maxHeapMemory = maxHeapMemory;
	}

}
