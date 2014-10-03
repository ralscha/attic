package ch.rasc.portaldemos.smoothie;

public class CpuData {

	private final long time = System.currentTimeMillis();

	private double[] host1;

	private double[] host2;

	private double[] host3;

	private double[] host4;

	public double[] getHost1() {
		return host1;
	}

	public void setHost1(double[] host1) {
		this.host1 = host1;
	}

	public double[] getHost2() {
		return host2;
	}

	public void setHost2(double[] host2) {
		this.host2 = host2;
	}

	public double[] getHost3() {
		return host3;
	}

	public void setHost3(double[] host3) {
		this.host3 = host3;
	}

	public double[] getHost4() {
		return host4;
	}

	public void setHost4(double[] host4) {
		this.host4 = host4;
	}

	public long getTime() {
		return time;
	}

}
