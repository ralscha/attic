package ch.rasc.java8.array;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class Array {

	private final Random rand = new Random();

	public static void main(String... args) throws RunnerException {
		Options opt = new OptionsBuilder().exclude(Grow.class.getSimpleName())
				.include(Array.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

	int[] testArray;

	@Setup
	public void prepare() {
		this.testArray = new int[30];
		for (int i = 0; i < this.testArray.length; i++) {
			this.testArray[i] = this.rand.nextInt(32);
		}
	}

	@Benchmark
	public void unwrapped(Blackhole bh) {
		int sum = 0;
		sum += this.testArray[0];
		sum += this.testArray[1];
		sum += this.testArray[2];
		sum += this.testArray[3];
		sum += this.testArray[4];
		sum += this.testArray[5];
		sum += this.testArray[6];
		sum += this.testArray[7];
		sum += this.testArray[8];
		sum += this.testArray[9];
		sum += this.testArray[10];
		sum += this.testArray[11];
		sum += this.testArray[12];
		sum += this.testArray[13];
		sum += this.testArray[14];
		sum += this.testArray[15];
		sum += this.testArray[16];
		sum += this.testArray[17];
		sum += this.testArray[18];
		sum += this.testArray[19];
		sum += this.testArray[20];
		sum += this.testArray[21];
		sum += this.testArray[22];
		sum += this.testArray[23];
		sum += this.testArray[24];
		sum += this.testArray[25];
		sum += this.testArray[26];
		sum += this.testArray[27];
		sum += this.testArray[28];
		sum += this.testArray[29];

		bh.consume(sum);
	}

	@Benchmark
	public void loop(Blackhole bh) {
		int sum = 0;
		for (int element : this.testArray) {
			sum += element;
		}
		bh.consume(sum);
	}

	@Benchmark
	public void loopLength(Blackhole bh) {
		int sum = 0;
		int len = this.testArray.length;
		for (int i = 0; i < len; i++) {
			sum += this.testArray[i];
		}
		bh.consume(sum);
	}
}
